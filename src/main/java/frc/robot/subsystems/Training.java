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
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

public class Training extends SubsystemBase
{
    private TitanQuad rightMotor, leftMotor, backMotor, liftMotor;
    private TitanQuadEncoder rightEnc, leftEnc, backEnc, liftEnc;

    private PID rightPID = new PID(0.35, 0.065, 0.0001, -100, 100); 
    private PID leftPID = new PID(0.35, 0.065, 0.0001, -100, 100);
    private PID backPID = new PID(0.35, 0.065, 0.0001, -100, 100);

    private float speedRpid, speedLpid, speedBpid;

    private float rightLast, leftLast, backLast = 0;

    public static float posX;
    public static float posY;

    private MedianFilter sharpRightFilter;
    private MedianFilter sharpLeftFilter;
    private MedianFilter sonicRightFilter;
    private MedianFilter sonicLeftFilter, sonicLeftAdapFilter;

    private Ultrasonic sonicRight, sonicLeft;
    private AnalogInput sharpRight, sharpLeft;
    private float confIk = 1.2f;
    private AHRS gyro;

    public boolean finish = false;

    public Training()
    {
        rightMotor = new TitanQuad(42, 1);
        leftMotor = new TitanQuad(42, 2);
        backMotor = new TitanQuad(42, 0);
        
        liftMotor = new TitanQuad(42, 3);


        rightEnc = new TitanQuadEncoder(rightMotor, 1, 0.2399827721492203);
        leftEnc = new TitanQuadEncoder(leftMotor, 2, 0.2399827721492203);
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
        

        gyro = new AHRS();
        

        new Thread( () -> {
            while(!Thread.interrupted())
            {
                try 
                {

                    Thread.sleep(5);
                } 
                catch (Exception e) 
                {
                    // e.printStackTrace();
                }
            }
        }).start();

    }

    public float getRightEncoder(){
        return (float)rightEnc.getEncoderDistance();
    }

    public float getLeftEncoder(){
        return (float)leftEnc.getEncoderDistance();
    }

    public float getBackEncoder(){
        return (float)backEnc.getEncoderDistance();
    }

    public float getLiftEncoder() {
        return (float)liftEnc.getEncoderDistance();
    }

    public void travelXYZ(){
        float currentRight = getRightEncoder();
        float currentLeft = getLeftEncoder();
        float currentBack = getBackEncoder();

        float rightSpeed = currentRight - rightLast;
        float leftSpeed = currentLeft - leftLast;
        float backSpeed = currentBack - backLast;

        double nowYaw = Math.toRadians(getYaw());

        float spX = -(((rightSpeed) - (leftSpeed)) / 2) / 0.963f;
        float spY = ((-(rightSpeed / 2) - (leftSpeed / 2) + backSpeed) / 3) / 0.554f;

        float r = (float) Math.sqrt(spX * spX + spY * spY);
        float theta = (float) (Math.atan2(spY, spX) + nowYaw);

        posX += r * Math.cos(theta);
        posY += r * Math.sin(theta);

        rightLast = currentRight;
        leftLast = currentLeft;
        backLast = currentBack;
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

    public void setAxisSpeed(float x, float y, float z) {
        double motorLF =  x - y / 2 + z;
        double motorRF = - (x + y / 2 - z);
        double motorB = (y + z);

        backPID.calculate(-backEnc.getSpeed(), motorB); // revert
        rightPID.calculate(-rightEnc.getSpeed(), motorRF); 
        leftPID.calculate(-leftEnc.getSpeed(), motorLF);

        SmartDashboard.putNumber("rightPID", rightPID.getOutput());
        SmartDashboard.putNumber("leftPID", leftPID.getOutput());
        SmartDashboard.putNumber("backPID", backPID.getOutput());

        backMotor.set(backPID.getOutput()); // revert
        rightMotor.set(rightPID.getOutput()); // revert
        leftMotor.set(leftPID.getOutput()); 
        
    }






    public void OdometryReset(float x, float y){
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
    public float getRightSharpDistance(){
        // return (sharpRightFilter.Filter((float)(Math.pow(sharpRight.getAverageVoltage(), -1.2045) * 27.726)) + confIk)-5;
        return (sharpRightFilter.Filter((float)(Math.pow(sharpRight.getAverageVoltage(), -1.2045) * 27.726)));
    }

    public float getLeftSharpDistance(){
        // return (sharpLeftFilter.Filter((float)(Math.pow(sharpLeft.getAverageVoltage(), -1.2045) * 27.726)))-5;
        return (sharpLeftFilter.Filter((float)(Math.pow(sharpLeft.getAverageVoltage(), -1.2045) * 27.726)));
    }

    public float getRightSonicDistance(){
        try{
            sonicRight.ping();
            Timer.delay(0.005);

            return (float) sonicRightFilter.Filter((float)sonicRight.getRangeMM() / 10);
            // return (float) sonicRightFilter.midFilter((float)sonicRight.getRangeMM() / 10);
            // return (float)sonicRight.getRangeMM()/10;
        }catch (Exception e){
            return 0;
        }
    }

    public float getLeftSonicDistance(){
        try{
            sonicLeft.ping();
            Timer.delay(0.005);

            // return sonicLeftFilter.midFilter((float)sonicLeft.getRangeMM() / 10);
            return sonicLeftFilter.Filter((float)sonicLeft.getRangeMM() / 10);
            // return ((float) sonicLeftAdapFilter.adapFilter((float)sonicLeft.getRangeMM() / 10));

            // return (float)sonicLeft.getRangeMM()/10;
            
        }catch (Exception e){
            return 0;
        }
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

    @Override
    public void periodic()
    {
        // ENCODERS ------------------------------------------------------
        SmartDashboard.putNumber("rightEnc", getRightEncoder());
        SmartDashboard.putNumber("leftEnc", getLeftEncoder());
        SmartDashboard.putNumber("backEnc", (float)backEnc.getEncoderDistance());

        SmartDashboard.putNumber("speedRightMotor", rightEnc.getSpeed());
        SmartDashboard.putNumber("speedLeftMotor", leftEnc.getSpeed());
        SmartDashboard.putNumber("speedBackMotor", backEnc.getSpeed());

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

        // test
        SmartDashboard.putBoolean("end", finish);
    }
}