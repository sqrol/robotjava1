package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class Odometry implements IState {
    private String odo;
    private double X, Y, Z, initX, initY, initZ, method_X, method_Y, method_Z;
    private boolean exit = false;
    private boolean firstStart = true;
    private int pos = 0;

    private double[][] speedTime = { { 0, 0.15, 0.3, 0.9 },  
                                     { 0, 0.2, 0.5, 1 } };

    private double[][] speedXandY = { { 0, 5, 15, 40, 70, 150, 170, 220 },
                                     { 0, 6, 12, 16, 28, 41, 68, 90 } };

    private double[][] speedZFunc = { { 0, 1.2, 3, 6f, 12, 26, 32, 50 },
                                       { 0, 4, 10, 18, 24, 32, 48, 70 } };

    // private float[][] speedZFunc = { { 0f, 1.2f, 3f, 6f, 12f, 26f, 32f, 50f, 61, 74, 88 },
    //                                  { 0, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89 } }; 

    // private float[][] speedZFunc = { { 0.1f, 1.64f, 2.1f, 3.2f, 4f, 5.3f, 7.5f, 12f, 30, 70, 90 },
    //                              { 3.1f, 3.9f, 7f, 10f, 13f, 19, 25f, 34f, 40, 70, 90 } };

    public Odometry(String odo, double X, double Z) { 
        this.odo = odo;
        this.X = X; // был -
        this.Z = Z;
    }

    @Override
    public boolean execute() {
        switch (this.odo) {
        case "absolute":
            exit = absOdometry(X, Z);
            break;

        case "relative":
            if (firstStart) {
                initX = Training.posX;
                initY = Training.posY;
                initZ = RobotContainer.train.getYaw();

                double r = Math.sqrt(X * X + Y * Y);
                double theta = Math.atan2(Y, X) + Math.toRadians(RobotContainer.train.getYaw());

                double x = r * Math.cos(theta);
                double y =r * Math.sin(theta);

                method_X = x + initX;
                method_Y = y + initY;
                method_Z = Z + initZ;
                firstStart = false;
            }
            exit = absOdometry(method_X, method_Z);
            break;

        case "check":
            exit = checkMotors(X, Y);
            break;
        }

        return exit;
    }

    // private boolean absOdometry(float x, float y, float z){
    // float time = Function.TransitionFunction( (float)(Timer.getFPGATimestamp() - StateMachine.startTime), speedTime );
    // float newX = x - Training.posX;
    // float newY = y - Training.posY;
    // float newZ = z - (float) RobotContainer.train.getYaw();

    // float setX = newX;
    // float setY = newY;
    
    // if(StateMachine.first){
    //     if(!Function.BooleanInRange(newZ, -30, 30)){
    //         pos = Function.axis(X, Y, Training.posX, Training.posY);
    //     }else{
    //         pos = 0;
    //     }
    //     StateMachine.first = false;
    // }

    // if(pos == 1){
    //     setX *= 5f;
    // }else if(pos == 2){
    //     setY *= -5f;
    // }

    // float rads = (float)Math.toRadians(RobotContainer.train.getYaw());
    // float newXX = Function.TransitionFunction(setX, speedXandY);
    // float newYY = Function.TransitionFunction(setY, speedXandY);

    // float speedX = (float)(Math.cos(rads) * newXX - Math.sin(rads) * newYY) * time;
    // float speedY = (float)(Math.cos(rads) * newYY - Math.sin(rads) * newXX) * time; 

    // float speedZ = Function.TransitionFunction(newZ, speedZFunc) * time;

    // SmartDashboard.putNumber("animeYaw", rads);

    // boolean stopX = Function.BooleanInRange(newX, -5f, 5f);
    // boolean stopY = Function.BooleanInRange(newY, -5f, 5f);
    // boolean stopZ = Function.BooleanInRange(newZ, -0.9f, 0.9f);

    // boolean end = stopX && stopY && stopZ;

    // if(end){
    //     RobotContainer.train.setAxisSpeed(0, 0);
    // }else{
    //     RobotContainer.train.setAxisSpeed(speedX, speedZ);
    // }

    // SmartDashboard.putNumber("newX", newX);
    // SmartDashboard.putNumber("newY", newY);

    // SmartDashboard.putBoolean("StopX", stopX);
    // SmartDashboard.putBoolean("StopY", stopY);
    // SmartDashboard.putBoolean("StopZ", stopZ);

    // return end;
    // }

    private boolean absOdometry(double x, double z) {
        double time = Function.TransitionFunction(Timer.getFPGATimestamp() - StateMachine.startTime, speedTime);
        double newX = x - Training.posX;
        double newZ = z - RobotContainer.train.getYaw();
    
        double setX = newX;
    
        if(StateMachine.first) {
            if(!Function.BooleanInRange(newZ, -30, 30)) {
                pos = Function.axis(X, Y, Training.posX, Training.posY);
            } else {
                pos = 0;
            }
            StateMachine.first = false;
        }
    
        if(pos == 1) {
            setX *= 5f;
        }
    
        float rads = (float)Math.toRadians(RobotContainer.train.getYaw());
        double newXX = Function.TransitionFunction(setX, speedXandY);
    
        double speedX = newXX * time;
        double speedZ = Function.TransitionFunction(newZ, speedZFunc) * time;
    
        SmartDashboard.putNumber("animeYaw", rads);
    
        boolean stopX = Function.BooleanInRange(newX, -5f, 5f);
        boolean stopZ = Function.BooleanInRange(newZ, -0.9f, 0.9f);
    
        boolean end = stopX && stopZ;
    
        if(end) {
            RobotContainer.train.setAxisSpeed(0, 0);
        } else {
            RobotContainer.train.setAxisSpeed(speedX, speedZ);
        }
    
        SmartDashboard.putNumber("newX", newX);
        SmartDashboard.putBoolean("StopX", stopX);
        SmartDashboard.putBoolean("StopZ", stopZ);
    
        return end;
    }
    
    // private boolean absOdometry(float x, float y, float z) {
    //     float time = Function.TransitionFunction((float) (Timer.getFPGATimestamp() - StateMachine.startTime), speedTime);
    //     float newX = x - Training.posX;
    //     float newY = y - Training.posY;
    //     float newZ = z - (float) RobotContainer.train.getYaw();
    
    //     float setX = newX;
    //     float setY = newY;
    
    //     if (StateMachine.first) {
    //         if (!Function.BooleanInRange(newZ, -30, 30)) {
    //             pos = Function.axis(X, Y, Training.posX, Training.posY);
    //         } else {
    //             pos = 0;
    //         }
    //         StateMachine.first = false;
    //     }
    
    //     if (pos == 1) {
    //         setX *= 5f;
    //     } else if (pos == 2) {
    //         setY *= 5f;
    //     }
    
    //     // Вычисляем угол движения робота
    //     float theta = (float) Math.atan2(setY, setX);
    
    //     // Поворачиваем вектор движения на угол поворота
    //     float cosThetaZ = (float) Math.cos(Math.toRadians(newZ));
    //     float sinThetaZ = (float) Math.sin(Math.toRadians(newZ));
    //     float newXRotation = setX * cosThetaZ - setY * sinThetaZ;
    //     float newYRotation = setX * sinThetaZ + setY * cosThetaZ;
    
    //     // Новые значения для движения
    //     float newSetX = newXRotation;
    //     float newSetY = newYRotation;
    
    //     // Вычисление скорости движения по новым направлениям
    //     float newR = Function.TransitionFunction((float) Math.sqrt(newSetX * newSetX + newSetY * newSetY), speedXandY);
    //     float speedX = newR * (float) Math.cos(theta) * time;
    //     float speedY = newR * (float) Math.sin(theta) * time;
    //     float speedZ = Function.TransitionFunction(newZ, speedZFunc) * time;
    
    //     boolean stopX = Function.BooleanInRange(newX, -5f, 5f);
    //     boolean stopY = Function.BooleanInRange(newY, -5f, 5f);
    //     boolean stopZ = Function.BooleanInRange(newZ, -0.9f, 0.9f);
    
    //     boolean end = stopX && stopY && stopZ;
    
    //     if (end) {
    //         RobotContainer.train.setAxisSpeed(0, 0, 0);
    //     } else {
    //         RobotContainer.train.setAxisSpeed(speedX, speedY, speedZ);
    //     }
    
    //     SmartDashboard.putNumber("newX", newX);
    //     SmartDashboard.putNumber("newY", newY);
    
    //     SmartDashboard.putBoolean("StopX", stopX);
    //     SmartDashboard.putBoolean("StopY", stopY);
    //     SmartDashboard.putBoolean("StopZ", stopZ);
    
    //     return end;
    // }
    
    
    

    private boolean checkMotors(double nameMotor, double speed){
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
