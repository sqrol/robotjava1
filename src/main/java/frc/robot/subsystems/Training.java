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

//Vendor imports
import com.kauailabs.navx.frc.AHRS;
import com.studica.frc.Servo;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

public class Training extends SubsystemBase
{
    private TitanQuad rightMotor, leftMotor, backMotor, liftMotor;
    private TitanQuadEncoder rightEnc, leftEnc, backEnc, liftEnc;

    private PID rightPID = new PID(0.35, 0.065, 0.0001, -100, 100); 
    private PID leftPID = new PID(0.35, 0.065, 0.0001, -100, 100);
    private PID backPID = new PID(0.35, 0.065, 0.0001, -100, 100);
    private PID liftPID = new PID(0.35, 0.065, 0.0001, -100, 100);
    private double speedRpid, speedLpid, speedBpid;

    private double rightLast, leftLast, backLast = 0;

    public static double posX;
    public static double posY;

    private MedianFilter sharpRightFilter;
    private MedianFilter sharpLeftFilter;
    private MedianFilter sonicRightFilter;
    private MedianFilter sonicLeftFilter, sonicLeftAdapFilter;

    
    private Ultrasonic sonicRight, sonicLeft;
    private AnalogInput sharpRight, sharpLeft;
    private double confIk = 1.2f;
    private AHRS gyro;

    private Servo glide;
    private Servo grip;
    private Servo gripRotate;
    private Servo mainRotate;
    private Encoder limitSwitch;

    public boolean initLift = false;
    public boolean finish = false;
    
    public int checkAppleResult = 0;

    public boolean liftStop = false;
    private double liftSpeed = 0;
    private double[][] speedForLift = {{0, 20, 90, 150, 250, 350, 400, 500, 600}, {0, 0.4, 0.10, 0.16, 0.27, 0.39, 0.46, 0.53, 0.60}}; 
    public int positions = 0; 
    private boolean flag = true;
    public Training()
    {
        rightMotor = new TitanQuad(42, 0);
        leftMotor = new TitanQuad(42, 1);
        backMotor = new TitanQuad(42, 2);
        
        liftMotor = new TitanQuad(42, 3);

        rightEnc = new TitanQuadEncoder(rightMotor, 0, 0.2399827721492203);
        leftEnc = new TitanQuadEncoder(leftMotor, 1, 0.2399827721492203);
        backEnc = new TitanQuadEncoder(backMotor, 0, 0.2399827721492203);

        liftEnc = new TitanQuadEncoder(liftMotor, 3, 0.2399827721492203);

        sharpRight = new AnalogInput(0);
        sharpLeft = new AnalogInput(1);
        sonicLeft = new Ultrasonic(11, 10);
        sonicRight = new Ultrasonic(9, 8);

        sharpRightFilter = new MedianFilter(4);
        sharpLeftFilter = new MedianFilter(4);

        sonicRightFilter = new MedianFilter(6);
        sonicLeftFilter = new MedianFilter(6);
        sonicLeftAdapFilter = new MedianFilter(3);

        glide = new Servo(0);
        grip = new Servo(1);
        gripRotate = new Servo(2);
        mainRotate = new Servo(9);
        
        limitSwitch = new Encoder(6, 7);
        
        gyro = new AHRS();
        
        
        new Thread( () -> {
            while(!Thread.interrupted())
            {
                try 
                {
                    if(getLimitSwitch()){
                        initLift = false;
                        liftEnc.reset();
                    }
                    setLiftPositions();
                    if(flag) {
                        setMainRotateServoValue(0);
                        setGlideServoValue(0);
                        flag = false;
                    }
                    
                    Thread.sleep(5);
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    public double getRightEncoder(){
        return rightEnc.getEncoderDistance();
    }

    public double getLeftEncoder(){
        return leftEnc.getEncoderDistance();
    }

    public double getBackEncoder(){
        return backEnc.getEncoderDistance();
    }

    public double getLiftEncoder() {
        return liftEnc.getEncoderDistance();
    }

    public boolean getLimitSwitch(){
        return limitSwitch.getDistance() == -1 || limitSwitch.getDistance() == 2;
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

    public void setAxisSpeed(double x, double z) {

        // double motorL = x + z + x / 2;
        // double motorR = -x - z;

        double motorL = x + z + x / 2; 
        double motorR = -x - z;
    
        leftPID.calculate(-leftEnc.getSpeed(), motorL);
        rightPID.calculate(-rightEnc.getSpeed(), motorR);
    
        SmartDashboard.putNumber("leftPID", leftPID.getOutput());
        SmartDashboard.putNumber("rightPID", rightPID.getOutput());
    
        leftMotor.set(leftPID.getOutput());
        rightMotor.set(rightPID.getOutput());
    }

    public void OdometryReset(double x, double y){
        rightLast = 0;
        leftLast = 0;
        backLast = 0;

        rightEnc.reset();
        leftEnc.reset();
        backEnc.reset();

        posX = x;
        posY = y;
    }

    public void checkMotors(String name, double speed){
        switch(name){
            case "right":
                rightMotor.set(speed);
                break;

            case "left":
                leftMotor.set(speed);
                break;

            case "back":
                backMotor.set(speed);
                break;
        }
    }


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

    public void setLiftPositions() {
        SmartDashboard.putBoolean("InitLift", initLift);
        

        // -liftSpeed Движение вверх
        // liftSpeed Движение вниз

        if (initLift) {
            liftSpeed = -0.5;
            liftStop = getLimitSwitch();
            SmartDashboard.putNumber("Hello World", 0);
            if (liftStop) {
                liftPID.reset(); 
                
            }
        } else {
            liftSpeed = Function.TransitionFunction(positions - liftEnc.getEncoderDistance(), speedForLift);
            liftStop = Function.BooleanInRange(positions - liftEnc.getEncoderDistance(), -10, 10);
        }

        if (liftStop && !getLimitSwitch()) {
            SmartDashboard.putNumber("Hello World", 1);
            liftPID.reset(); 
            liftMotor.set(0);
        } else if (liftSpeed < 0 && getLimitSwitch()){
            liftPID.reset();
            liftMotor.set(0);
            liftEnc.reset();
            SmartDashboard.putNumber("Hello World", 2);
        } else if (liftSpeed > 0 && liftEnc.getEncoderDistance() > 500 && !initLift) {
            liftPID.reset();
            liftMotor.set(0);
            SmartDashboard.putNumber("Hello World", 3);
        } else 
        {
            SmartDashboard.putNumber("Hello World", 4);
            liftPID.calculate(liftEnc.getSpeed(), liftSpeed);
            liftMotor.set(liftPID.getOutput());
        }

        SmartDashboard.putBoolean("Swith", getLimitSwitch());
        SmartDashboard.putNumber("positions", positions);
        SmartDashboard.putNumber("razniza", liftEnc.getEncoderDistance() - positions);
        SmartDashboard.putNumber("liftSpeed", liftSpeed);
        SmartDashboard.putBoolean("liftStop", liftStop);

    }

    @Override
    public void periodic()
    {
        // ENCODERS ------------------------------------------------------
        SmartDashboard.putNumber("rightEnc", getRightEncoder());
        SmartDashboard.putNumber("leftEnc", getLeftEncoder());
        SmartDashboard.putNumber("liftEnc", getLiftEncoder());

        SmartDashboard.putNumber("speedRightMotor", rightEnc.getSpeed());
        SmartDashboard.putNumber("speedLeftMotor", leftEnc.getSpeed());
        SmartDashboard.putNumber("speedLiftotor", liftEnc.getSpeed());

        SmartDashboard.putNumber("posX", posX);
        SmartDashboard.putNumber("posY", posY);
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

        // test
        SmartDashboard.putBoolean("end", finish);
    }
}