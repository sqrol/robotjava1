package frc.robot.subsystems;

//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

import java.util.concurrent.TimeUnit;

//Vendor imports
import com.kauailabs.navx.frc.AHRS;
import com.studica.frc.Servo;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

public class Training extends SubsystemBase
{
    private TitanQuad rightMotor, leftMotor, glideMotor, liftMotor;
    private TitanQuadEncoder rightEnc, leftEnc, glideEnc, liftEnc;

    private PID rightPID = new PID(0.3, 0.094, 0.001, -100, 100); 
    private PID leftPID =  new PID(0.3, 0.094, 0.001, -100, 100);
    // private PID backPID = new PID(0.35, 0.065, 0.0001, -100, 100); // изначальный PID
    private PID liftPID =  new PID(0.3, 0.094, 0.001, -100, 100);
    private PID glidePID = new PID(0.3, 0.094, 0.001, -100, 100);

    private double speedRpid, speedLpid, speedBpid;
    private double rightLast, leftLast = 0;
 
    public static double posX, posY;

    private double liftSpeed, glideSpeed;

    private MedianFilter sharpRightFilter, sharpLeftFilter, sonicRightFilter, sonicLeftFilter;

    private Ultrasonic sonicRight, sonicLeft;
    private AnalogInput sharpRight, sharpLeft;
    // private double confIk = 1.2f;
    private AHRS gyro;

    private Servo glide, grip, gripRotate, mainRotate;

    private Encoder limitSwitchLift, limitSwitchGlide;

    public boolean initLift, liftStop, initGlide, glideStop, finish = false;
    private boolean flag = true;

    public int checkAppleResult = 0;

    private boolean liftInitFirst; 
    public boolean fristInitForGlide = false; 
    private boolean fristCallForOMS = true; 

    public boolean fristInitForGlideDone = false;

    public double leftMotorSpeedThread = 0; 
    public double rightMotorSpeedThread = 0; 
    public double liftMotorSpeedThread = 0; 
    public double glideMotorSpeedThread = 0;

    private double[][] speedForLift = { { 0, 20, 90, 150, 250, 350, 400, 500, 600 },
                                         { 0, 4, 10, 16, 27, 39, 46, 53, 60 } }; 
    
    private double[][] speedForGlide = { { 0, 20, 90, 150, 250, 350, 400, 500, 600 },
                                         { 0, 4, 10, 16, 27, 39, 46, 53, 60 } }; 

    private static final double[][] arrOfPos = {{-1, 0, 100}, {0, 10, 400}};
    private static final double[][] arrForGlide = {{-165, -125, -16, -5, 5, 16, 125, 165}, {-45, -35, -25, -5, 5, 25, 35, 45}};

    public Training()
    {
        rightMotor = new TitanQuad(42, 2);
        leftMotor = new TitanQuad(42, 3); 
        glideMotor = new TitanQuad(42, 0); 
        glideMotor.setInverted(true);
        liftMotor = new TitanQuad(42, 1); 

        rightEnc = new TitanQuadEncoder(rightMotor, 2, 0.2399827721492203);
        leftEnc = new TitanQuadEncoder(leftMotor, 3, 0.2399827721492203);
        glideEnc = new TitanQuadEncoder(glideMotor, 0, 0.2399827721492203);
        glideEnc.setReverseDirection();
        liftEnc = new TitanQuadEncoder(liftMotor, 1, 0.2399827721492203);

        sharpRight = new AnalogInput(3);
        sharpLeft = new AnalogInput(2);

        sonicLeft = new Ultrasonic(10, 11);
        sonicRight = new Ultrasonic(8, 9);

        sharpRightFilter = new MedianFilter(4);
        sharpLeftFilter = new MedianFilter(4);

        sonicRightFilter = new MedianFilter(6);
        sonicLeftFilter = new MedianFilter(6);

        glide = new Servo(0);
        grip = new Servo(8);
        gripRotate = new Servo(9);
        mainRotate = new Servo(7);
        
        limitSwitchLift = new Encoder(2, 3);
        limitSwitchGlide = new Encoder(6, 7);
        gyro = new AHRS();
        
        // // Поток для работы с СМО
        new Thread( () -> {
            while(!Thread.interrupted())
            {
                try
                {
                    if (fristCallForOMS) {
                        glideMotorSpeedThread = 0; 
                        fristCallForOMS = false; 
                    }
                    
                    // if (getLimitSwitchLift()) {
                    //     setLiftMotorSpeed(0, false);
                    // } else { 
                    //     setLiftMotorSpeed(liftMotorSpeedThread, false);
                    // }

                    if (fristInitForGlide && !fristInitForGlideDone) {
                        fristInitForGlideDone = initForGlide();
                    } else {
                        glideToMovePos(50);
                    }

                    if (getLimitSwitchLift()) {
                        setLiftMotorSpeed(0, false);
                    } else { 
                        setLiftMotorSpeed(liftMotorSpeedThread, false);
                    }
                    setLiftMotorSpeed(liftMotorSpeedThread, true);
                    setGlideMotorSpeed(glideMotorSpeedThread, true);
 

                    SmartDashboard.putNumber("liftMotorSpeedThread", liftMotorSpeedThread);
                    SmartDashboard.putBoolean("getLimitSwitchLift", getLimitSwitchLift());

                    SmartDashboard.putNumber("glideMotorSpeedThread", glideMotorSpeedThread);
                    SmartDashboard.putBoolean("getLimitSwitchGlide", getLimitSwitchGlide());

                    // -574 Макс


                    Thread.sleep(5);
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private boolean initForGlide() {
        if (getLimitSwitchGlide()) {
            resetGlideEncoder();
            glideMotorSpeedThread = 0;
            return true;
        } else {
            glideMotorSpeedThread = 35;
        }
        return false;
    }

    public boolean glideToMovePos(double pos) {

        double nowPos = -getGlideEncoder();
        double encPos = Function.TransitionFunction(pos, arrOfPos); 
        double speed = Function.TransitionFunction(nowPos - encPos, arrForGlide); 
        boolean glideStop = Function.BooleanInRange(nowPos - encPos, -5, 5); 

        if (getLimitSwitchGlide() && speed > 0) {
            speed = 0;
            resetGlideEncoder();
            return true; 
        } else if (speed < 0 && glideEnc.getEncoderDistance() < -550) {
            glideMotorSpeedThread = 0;
            return true; 
        } else if (glideStop && !getLimitSwitchGlide()) {
            glideMotorSpeedThread = 0;
            return true; 
        } else {
            glideMotorSpeedThread = speed;
            return false;
        }  
    }


    public double getRightEncoder(){
        return rightEnc.getEncoderDistance();
    }

    public double getLeftEncoder(){
        return leftEnc.getEncoderDistance();
    }

    public double getGlideEncoder(){
        return glideEnc.getEncoderDistance();
    }

    public double getLiftEncoder() {
        return liftEnc.getEncoderDistance();
    }

    public void resetGlideEncoder(){
        glideEnc.reset();;
    }

    public boolean getLimitSwitchLift(){
        return limitSwitchLift.getDistance() == -1 || limitSwitchLift.getDistance() == 2;
    }

    public boolean getLimitSwitchGlide(){
        return limitSwitchGlide.getDistance() == -1 || limitSwitchGlide.getDistance() == 2;
    }


    // Не забудь сюда добовиль опрос кнопки когда она появиться!
    private boolean getEMSButton() {
        return false;
    }



    public void setLiftMotorSpeed(double speed, boolean withPID) {
        if (speed == 0) {
            liftPID.reset();
            liftMotor.set(0);
        }
        if (withPID) { 
            liftPID.calculate(-liftEnc.getSpeed(), speed);
            liftMotor.set(liftPID.getOutput());
        } else {
            liftMotor.set(speed / 100);
        }
        SmartDashboard.putNumber("setLiftMotorSpeed", speed); 
    }

    public void setGlideMotorSpeed(double speed, boolean withPID) {
        if (speed == 0) {
            glidePID.reset();
            glideMotor.set(0);
        }
        if (withPID) { 
            glidePID.calculate(glideEnc.getSpeed(), speed);
            glideMotor.set(glidePID.getOutput());
        } else {
            glideMotor.set(speed / 100);
        }
        SmartDashboard.putNumber("setGlideMotorSpeed", speed); 
    }




    public void travelXYZ(){
        double currentRight = getRightEncoder();
        double currentLeft = getLeftEncoder();

        double rightSpeed = currentRight - rightLast;
        double leftSpeed = currentLeft - leftLast;

        double nowYaw = Math.toRadians(getYaw());

        double spX = (-rightSpeed + leftSpeed) / 0.963f;

        posX +=  Math.cos(nowYaw) * spX - Math.sin(nowYaw);
        

        SmartDashboard.putNumber("spremX", spX);

        rightLast = currentRight;
        leftLast = currentLeft;
    }
    
    // public void setAxisSpeed(float x, float y, float z){
    //     float right = - (x + y / 2 - z);
    //     float left =  x - y / 2 + z;
    //     float back = (y + z);

    //     float maxValue = Math.max(Math.max(Math.abs(right), Math.abs(left)), Math.abs(back));
    //     float cof = 1;

    //     if (95 < maxValue) {
    //       cof = 95 / maxValue;
    //     } else {
    //       cof = 1;
    //     }

    //     float X = right * cof;
    //     float Y = left * cof;
    //     float Z = back * cof;

    //     if(X == 0.0){
    //         rightPID.reset();
    //         rightMotor.set(0);
    //     }else{
    //         rightPID.setpoint = X;
    //         speedRpid = rightPID.calculate((float) rightEnc.getSpeed() );
    //         SmartDashboard.putNumber("rgSpeed", rightEnc.getSpeed() / 10f); 
    //         SmartDashboard.putNumber("rgMotorSpeed", speedRpid); 
    //         rightMotor.set(speedRpid/100);
    //     }

    //     if(Y == 0.0){
    //         leftPID.reset();
    //         leftMotor.set(0);
    //     }else{
    //         leftPID.setpoint = Y;
    //         speedLpid = leftPID.calculate((float)leftEnc.getSpeed() / 10f);
    //         leftMotor.set(speedLpid / 100);
    //     }

    //     if(Z == 0.0){
    //         backPID.reset();
    //         backMotor.set(0);
    //     }else{
    //         backPID.setpoint = Z;
    //         speedBpid = backPID.calculate((float)backEnc.getSpeed() / 10f);
    //         backMotor.set(speedBpid / 100);
    //     }
    // }

    // public void setAxisSpeed(float x, float y, float z) {
    //     y = -y;
    //     double motorLF =  x - y / 2 + z;
    //     double motorRF = - (x + y / 2 - z);
    //     double motorB = (y + z);

    //     backPID.calculate(-backEnc.getSpeed(), motorB); // revert
    //     rightPID.calculate(-rightEnc.getSpeed(), motorRF); 
    //     leftPID.calculate(-leftEnc.getSpeed(), motorLF);

    //     SmartDashboard.putNumber("rightPID", rightPID.getOutput());
    //     SmartDashboard.putNumber("leftPID", leftPID.getOutput());
    //     SmartDashboard.putNumber("backPID", backPID.getOutput());

    //     backMotor.set(backPID.getOutput()); // revert
    //     rightMotor.set(rightPID.getOutput()); // revert
    //     leftMotor.set(leftPID.getOutput()); 
    // }

    // public void setAxisSpeed(double x, double z) {
    //     if(x == 0 && z == 0) {
    //         leftPID.reset();
    //         rightPID.reset();

    //         leftMotor.set(0);
    //         rightMotor.set(0);

    //         rightEnc.reset();
    //         leftEnc.reset();
    //     }
    //     // double motorL = x + z + x / 2;
    //     // double motorR = -x - z;

    //     double motorL = -x - z ; 
    //     double motorR = x - z;

    //     SmartDashboard.putNumber("leftPIDIn", motorL);
    //     SmartDashboard.putNumber("rightPIDIn", motorR);
    
    //     leftPID.calculate(-leftEnc.getSpeed(), motorL);
    //     rightPID.calculate(-rightEnc.getSpeed(), motorR);
    
    //     SmartDashboard.putNumber("leftPIDOut", leftPID.getOutput());
    //     SmartDashboard.putNumber("rightPIDOut", rightPID.getOutput());
    
    //     // leftMotor.set(leftPID.getOutput());
    //     // rightMotor.set(rightPID.getOutput());

    //     leftMotor.set(leftPID.getOutput());
    //     rightMotor.set(rightPID.getOutput());
    // }

    public void setAxisSpeed(double x, double z) {
        if(x == 0 && z == 0) {
            leftPID.reset();
            rightPID.reset();

            leftMotor.set(0);
            rightMotor.set(0);

            rightEnc.reset();
            leftEnc.reset();
        }
        // double motorL = x + z + x / 2;
        // double motorR = -x - z;

        double motorL = -x - z ; 
        double motorR = x - z;

        SmartDashboard.putNumber("leftPIDIn", motorL);
        SmartDashboard.putNumber("rightPIDIn", motorR);
    
        leftPID.calculate(-leftEnc.getSpeed(), motorL);
        rightPID.calculate(-rightEnc.getSpeed(), motorR);
    
        SmartDashboard.putNumber("leftPIDOut", leftPID.getOutput());
        SmartDashboard.putNumber("rightPIDOut", rightPID.getOutput());
    
        // leftMotor.set(leftPID.getOutput());
        // rightMotor.set(rightPID.getOutput());

        leftMotor.set(leftPID.getOutput());
        rightMotor.set(rightPID.getOutput());
    }

    public void OdometryReset(double x, double y){
        rightLast = 0;
        leftLast = 0;

        rightEnc.reset();
        leftEnc.reset();

        posX = x;
        posY = y;
    }

    public void reset2Motors() {
        rightEnc.reset();
        leftEnc.reset();
    }

    // public void checkMotors(String name, double speed){
    //     switch(name){
    //         case "right":
    //             rightMotor.set(speed);
    //             break;

    //         case "left":
    //             leftMotor.set(speed);
    //             break;

    //         case "back":
    //             glideMotor.set(speed);
    //             break;
    //     }
    // }


    // SENSORS ---------------------------------------------------------------------------------------------
    public double getRightSharpDistance(){
        // return (sharpRightFilter.Filter((float)(Math.pow(sharpRight.getAverageVoltage(), -1.2045) * 27.726)) + confIk)-5;
        return (sharpRightFilter.Filter((Math.pow(sharpRight.getAverageVoltage(), -1.2045) * 27.726)));
    }

    public double getLeftSharpDistance(){
        // return (sharpLeftFilter.Filter((float)(Math.pow(sharpLeft.getAverageVoltage(), -1.2045) * 27.726)))-5;
        return (sharpLeftFilter.Filter((Math.pow(sharpLeft.getAverageVoltage(), -1.2045) * 27.726)));
    }

    public double getRightSonicDistance(){
        try{
            sonicRight.ping();
            Timer.delay(0.005);

            return sonicRightFilter.Filter(sonicRight.getRangeMM() / 10);
            // return (float) sonicRightFilter.midFilter((float)sonicRight.getRangeMM() / 10);
            // return (float)sonicRight.getRangeMM()/10;
        }catch (Exception e){
            return 0;
        }
    }

    public double getLeftSonicDistance(){
        try{
            sonicLeft.ping();
            Timer.delay(0.005);

            // return sonicLeftFilter.midFilter((float)sonicLeft.getRangeMM() / 10);
            return sonicLeftFilter.Filter(sonicLeft.getRangeMM() / 10);
            // return ((float) sonicLeftAdapFilter.adapFilter((float)sonicLeft.getRangeMM() / 10));

            // return (float)sonicLeft.getRangeMM()/10;
            
        }catch (Exception e){
            return 0;
        }
    }

    public void setGripServoValue(int value) {
        grip.setAngle(value);
    }

    public void setGripRotateServoValue(int value) {
        gripRotate.setAngle(value);
    }

    public void setMainRotateServoValue(int value) {
        mainRotate.setAngle(value);
    }

    public void setGlideServoValue(int value) {
        glide.setAngle(value);
    }

    public double getYaw()
    {
        return gyro.getYaw();
    }

    public double getLongYaw()
    {
        return gyro.getAngle();
    }

    // -----------------------------------------------------------------------------------------------------

    // BUTTONS ---------------------------------------------------------------------------------------------
    
    // -----------------------------------------------------------------------------------------------------
    public void resetGyro()
    {
        gyro.zeroYaw();
    }

    // public void setLiftPositions() {
    //     SmartDashboard.putBoolean("InitLift", initLift);
    //     SmartDashboard.putBoolean("Swith", getLimitSwitch());
    //     SmartDashboard.putNumber("Hello World", 0);
    //     SmartDashboard.putNumber("positions", positions);
        
    //     if (initLift) {
    //         liftSpeed = -20;
    //         liftStop = getLimitSwitch();
    //     } else {
    //         liftSpeed = Function.TransitionFunction(positions - liftEnc.getEncoderDistance(), speedForLift);
    //         liftStop = Function.BooleanInRange(positions - liftEnc.getEncoderDistance(), -5, 5);
    //         SmartDashboard.putNumber("razniza", positions - liftEnc.getEncoderDistance());
    //     }
    //     SmartDashboard.putNumber("liftSpeed", liftSpeed);

    //     if (liftStop && !getLimitSwitch()) {
    //         SmartDashboard.putNumber("Hello World", 1);
    //         liftPID.reset(); 
    //         liftMotor.set(0);
    //     } else if (liftSpeed < 0 && getLimitSwitch()){
    //         liftPID.reset();
    //         liftMotor.set(0);
    //         liftEnc.reset();
    //         SmartDashboard.putNumber("Hello World", 2);
    //     } else if (liftSpeed < 0 && liftEnc.getEncoderDistance() > 400 && !initLift) {
    //         liftPID.reset();
    //         liftMotor.set(0);
    //         SmartDashboard.putNumber("Hello World", 3);
    //     } else 
    //     {
    //         SmartDashboard.putNumber("Hello World", 4);
            
        
    //         liftPID.calculate(liftEnc.getSpeed(), liftSpeed);
    //         liftMotor.set(liftPID.getOutput());
    //     }
    // }

    // public void setLiftPositions(int positions) {

    //     SmartDashboard.putBoolean("InitLift", initLift);
        
    //     // liftSpeed движение вверх
    //     // -liftSpeed движение вниз

    //     if (initLift) {
    //         liftSpeed = 17;
    //         liftStop = getLimitSwitchLift();
    //         SmartDashboard.putNumber("Hello World", 0);
    //         if (liftStop) {
    //             liftPID.reset();
    //             liftMotor.set(0);
    //         }
    //     } else {
    //         liftSpeed = -Function.TransitionFunction(positions - liftEnc.getEncoderDistance(), speedForLift);
    //         liftStop = Function.BooleanInRange(positions - liftEnc.getEncoderDistance(), -10, 10);
    //     }

    //     if (liftStop && !getLimitSwitchLift()) {
    //         SmartDashboard.putNumber("Hello World", 1);
    //         liftPID.reset(); 
    //         liftMotor.set(0);
    //     } else if (liftSpeed > 0 && getLimitSwitchLift()){
    //         liftPID.reset();
    //         liftMotor.set(0);
    //         liftEnc.reset();
    //         SmartDashboard.putNumber("Hello World", 2);
    //     } else if (liftSpeed < 0 && liftEnc.getEncoderDistance() < -300 && !initLift) {
    //         liftPID.reset();
    //         liftMotor.set(0);
    //         SmartDashboard.putNumber("Hello World", 3);
    //     } else {
    //         SmartDashboard.putNumber("Hello World", 4);
    //         liftPID.calculate(liftEnc.getSpeed(), liftSpeed);
    //         liftMotor.set(liftPID.getOutput());
    //     }

    //     SmartDashboard.putBoolean("Swith", getLimitSwitchLift());
    //     SmartDashboard.putNumber("positions", positions);
    //     SmartDashboard.putNumber("razniza", liftEnc.getEncoderDistance() - positions);
    //     SmartDashboard.putNumber("liftSpeed", liftSpeed);
    //     SmartDashboard.putBoolean("liftStop", liftStop);

    // }

    public boolean moveToZero() {
        double speed = 10; 
        if (getLimitSwitchLift()) {
            liftMotorSpeedThread = 0; 
        } else {
            liftMotorSpeedThread = 10; 
        }

        return getLimitSwitchLift(); 
    }

    // public void setGlidePositions(int position) {

    //     if(initGlide) {
    //         glideSpeed = 20;
    //         glideStop = getLimitSwitchGlide();
    //         if(glideStop) {
    //             glidePID.reset();
    //             glideEnc.reset();
    //         }
    //     }
    //     else {
    //         glideSpeed = Function.TransitionFunction(position - glideEnc.getEncoderDistance(), speedForGlide);
    //         glideStop = Function.BooleanInRange(position - glideEnc.getEncoderDistance(), -5, 5);
    //     }

    //     if(glideStop && !getLimitSwitchGlide()) {
    //         glideSpeed = 0;
    //         glidePID.reset(); 
    //         glideMotor.set(glideSpeed);
    //     } else if(glideSpeed > 0 && getLimitSwitchGlide()) {
    //         glideSpeed = 0;
    //         glidePID.reset(); 
    //         glideEnc.reset();
    //         glideMotor.set(glideSpeed);
    //     } else if(glideSpeed < 0 && !initGlide && glideEnc.getEncoderDistance() > -500) {
    //         glideSpeed = 0;
    //         glidePID.reset();
    //         glideMotor.set(glideSpeed);
    //     } else {
    //         glidePID.calculate(glideEnc.getEncoderDistance(), glideSpeed);
    //         glideMotor.set(glidePID.getOutput());
    //     }
    //     SmartDashboard.putBoolean("glideStop", glideStop);
    //     SmartDashboard.putBoolean("initGlide", initGlide);
    // }

    @Override
    public void periodic()
    {
        // ENCODERS ------------------------------------------------------
        SmartDashboard.putNumber("rightEnc", getRightEncoder());
        SmartDashboard.putNumber("leftEnc", getLeftEncoder());
        SmartDashboard.putNumber("glideEnc", getGlideEncoder());
        SmartDashboard.putNumber("liftEnc", getLiftEncoder());

        SmartDashboard.putNumber("speedRightMotor", rightEnc.getSpeed());
        SmartDashboard.putNumber("speedLeftMotor", leftEnc.getSpeed());
        SmartDashboard.putNumber("speedLiftotor", liftEnc.getSpeed());

        SmartDashboard.putNumber("posZ", getYaw());

        // SENSORS -------------------------------------------------------
        SmartDashboard.putNumber("sharpRight", getRightSharpDistance());
        SmartDashboard.putNumber("sharpLeft", getLeftSharpDistance());
        SmartDashboard.putNumber("sonicRight", getRightSonicDistance());
        SmartDashboard.putNumber("sonicLeft", getLeftSonicDistance());
        SmartDashboard.putNumber("sonicLeftNumber", getLeftSonicDistance());
        SmartDashboard.putNumber("yaw", getYaw());

        // BUTTONS -------------------------------------------------------
 
        // SmartDashboard.putNumber("EMS_number", ems.getDistance());
        SmartDashboard.putBoolean("initlift", initLift);
        SmartDashboard.putBoolean("limitLift", getLimitSwitchLift());
        SmartDashboard.putBoolean("limitGlide", getLimitSwitchGlide());
        // test
        SmartDashboard.putBoolean("end", finish);
    }
}