package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class Odometry implements IState{
    private String odo;
    private float X, Y, Z, initX, initY, initZ, method_X, method_Y, method_Z;
    private boolean exit = false;
    private boolean firstStart = true;
    private int pos = 0;

    private float[][] speedTime = {{ 0, 0.15f, 0.3f, 0.9f },{ 0, 0.2f, 0.5f, 1 }};
    private float[][] speedXandY = { { 0f, 5.1f, 9.6f, 17, 42, 75, 90, 120, 180, 250, 570 }, { 0f, 8.7f, 18.5f, 22, 26, 30, 38, 41, 54, 63, 95 } };
    private float[][] speedZFunc = {{0.1f, 1.64f, 2.1f, 3.2f, 4f, 5.3f, 7.5f, 12f, 30, 70, 90}, { 3.1f, 3.9f, 7f, 10f, 13f, 19, 25f, 34f, 40, 70, 90}};

    public Odometry(String odo, float X, float Y, float Z){ 
        this.odo = odo;
        this.X = X; // был -
        this.Y = Y;
        this.Z = Z;
    }

    @Override
    public boolean execute(){
        switch (this.odo){
            case "absolute":
                exit = absOdometry(X, Y, Z);
                break;

            case "relative":
                if(firstStart){
                    initX = Training.posX;
                    initY = Training.posY;
                    initZ = (float) RobotContainer.train.getYaw();

                    float r = (float) (Math.sqrt(X * X + Y * Y));
                    float theta = (float)(Math.atan2(Y, X) + Math.toRadians((float) RobotContainer.train.getYaw()));

                    float x = (float) (r * Math.cos(theta));
                    float y = (float) (r * Math.sin(theta));

                    method_X = x + initX;
                    method_Y = y + initY;
                    method_Z = Z + initZ;
                    firstStart = false;
                }
                exit = absOdometry(method_X, method_Y, method_Z);
                break;

            case "check":
                exit = checkMotors(X, Y);
                break;
        }

        return exit;
    }
    
    private boolean absOdometry(float x, float y, float z){
        float time = Function.TransitionFunction( (float)(Timer.getFPGATimestamp() - StateMachine.startTime), speedTime );
        float newX = x - Training.posX;
        float newY = y - Training.posY;
        float newZ = z - (float) RobotContainer.train.getYaw();

        float setX = newX;
        float setY = newY;

        if(StateMachine.first){
            if(!Function.BooleanInRange(newZ, -30, 30)){
                pos = Function.axis(X, Y, Training.posX, Training.posY);
            }else{
                pos = 0;
            }
            StateMachine.first = false;
        }

        if(pos == 1){
            setX *= 5f;
        }else if(pos == 2){
            setY *= 5f;
        }

        float thetaR = (float) (Math.atan2(setY, setX) - Math.toRadians(RobotContainer.train.getYaw()));
        float newR = Function.TransitionFunction((float) Math.sqrt(setX * setX + setY * setY), speedXandY);

        float speedX = (float)(newR * Math.cos(thetaR)) * time;
        float speedY = (float)(newR * Math.sin(thetaR)) * time;
        float speedZ = Function.TransitionFunction(newZ, speedZFunc) * time;

        boolean stopX = Function.BooleanInRange(newX, -5f, 5f);
        boolean stopY = Function.BooleanInRange(newY, -5f, 5f);
        boolean stopZ = Function.BooleanInRange(newZ, -0.9f, 0.9f);

        boolean end = stopX && stopY && stopZ;

        if(end){
            RobotContainer.train.setAxisSpeed(0, 0, 0);
        }else{
            RobotContainer.train.setAxisSpeed(speedX, speedY, speedZ);
        }

        SmartDashboard.putNumber("newX", newX);
        SmartDashboard.putNumber("newY", newY);

        SmartDashboard.putBoolean("StopX", stopX);
        SmartDashboard.putBoolean("StopY", stopY);
        SmartDashboard.putBoolean("StopZ", stopZ);

        return end;
    }

    private boolean checkMotors(float nameMotor, double speed){
        switch((int)nameMotor){
            case 1:
                RobotContainer.train.checkMotors("left", speed);
                break;

            case 2:
                RobotContainer.train.checkMotors("right", speed);
                break;

            case 3:
                RobotContainer.train.checkMotors("back", speed);
                break;
        }
        
        return (Timer.getFPGATimestamp() - StateMachine.startTime) > 5;
    }
}
