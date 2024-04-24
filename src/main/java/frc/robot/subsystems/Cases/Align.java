package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;

public class Align implements IState {
    private String sensors;

    private boolean isFirstIter = true;
    private boolean finishX, finishZ, exit = false;

    private double X, Z = 0;

    private double speedX, diffX = 0;
    private double speedZ, diffZ = 0;
    private double diffSharp, diffSonic, lastGyro = 0;


    private static double[][] degreeSharpArray = { {0, 0.1, 0.5, 1, 1.5, 2, 5, 10, 15, 20, 30, 45},
                                                  { 0, 5, 10, 20, 25, 30, 35, 40, 45, 50, 55, 56} };

    private static double[][] degreeSonicArray = { {0, 0.1, 0.5, 1, 1.5, 2, 5, 10, 15, 20, 30, 45},
                                                  { 0, 5, 10, 20, 25, 30, 35, 40, 45, 50, 55, 56} };

    private static double[][] sharpXFunc = { {0, 0.9, 1.7, 4, 9, 26, 87, 120, 180, 250, 570 },
                                          { 0f, 4, 10.5, 17, 24, 30, 41, 56, 63, 71, 95 } }; 
 

    private static double[][] XArray = { {0, 0.9, 1.4, 3, 7, 16, 50, 117, 180, 240, 390 },
                                             { 0, 4, 10.5, 20, 35, 42, 50, 60, 69, 81, 90 } }; 

    private static double[][] sonicArray = { { 0.7, 2.6, 5, 12, 35, 41, 52, 64, 71, 80 },
                                               { 0, 15, 18, 26, 32, 38, 47, 60, 86, 90 } };


    private static double[][] degFunction = {{ 0.3, 2.4, 10, 25, 38, 47, 60, 70, 80, 90, 100, 100, 120, 130 }, 
                                             { 4.1, 9.5, 15, 26, 30, 37, 45, 51, 57, 70, 76, 85, 93, 100 } };

    private static double[][] degFunctionSonic = {{ 0.3, 2.4, 10, 25, 38, 47, 60, 70, 80, 90, 100, 100, 120, 130 }, 
                                                  { 4.1, 9.5, 15, 26, 30, 37, 45, 51, 57, 70, 76, 85, 93, 100 } };                                

    public  Align(String sensors, double X, double Z){
        this.sensors = sensors;
        this.X = X;
        this.Z = Z;
    }

    @Override
    public boolean execute() {
        switch(sensors){
            case "sharp":
                exit = alignIR(X);
            break;
            case "sonic":
                exit = travelSonic(X);
            break;
            case "rotate":
                exit = rotateZ(Z);
            break;
        }
        return exit;
    }

    private boolean alignIR(double X) {
        if(isFirstIter) {
            lastGyro = RobotContainer.train.getYaw();
            isFirstIter = false;
        }

        double leftSharp = RobotContainer.train.getLeftSharpDistance();  
        double rightSharp = RobotContainer.train.getRightSharpDistance();

        // if (Math.min(leftSharp, rightSharp) < 15) {
    
        diffSharp = leftSharp - rightSharp;
        SmartDashboard.putNumber("DiffSharp", diffSharp); 
        speedZ = -Function.TransitionFunction(diffSharp, degFunction);
        SmartDashboard.putNumber("sharp1", 1); 
        // else {
        //     speedZ = (float)(lastGyro - RobotContainer.train.getYaw()); 
        //     SmartDashboard.putNumber("sharp1", 2); 
        // }

        diffX = X - leftSharp;
        SmartDashboard.putNumber("diffX", diffX);
        speedX = Function.TransitionFunction(diffX, XArray);

        RobotContainer.train.setAxisSpeed(speedX, speedZ);
        // if(finishX) {
        //     RobotContainer.train.setAxisSpeed(speedX,  speedZ);
        // }

        finishZ = Function.BooleanInRange(diffSharp, -0.5, 0.5);
        finishX = Function.BooleanInRange(diffX, -2, 2);

        // return finishX && finishZ;
        if(finishZ && finishX) {
            RobotContainer.train.setAxisSpeed(0, 0);
            isFirstIter = true;
            lastGyro = 0;
            diffZ = 0;
            return true;
        }
        return false;
    }
    
    private boolean travelSonic(double X) {
        if(isFirstIter) {
            lastGyro = RobotContainer.train.getYaw();
            isFirstIter = false;
        }

        double leftSonic = RobotContainer.train.getLeftSonicDistance();  
        double rightSonic = RobotContainer.train.getRightSonicDistance();

        // if (Math.min(leftSharp, rightSharp) < 15) {
    
        diffSonic = leftSonic - rightSonic;
        SmartDashboard.putNumber("DiffSonic", diffSonic); 
        speedZ = Function.TransitionFunction(diffSonic, degFunctionSonic);
        // SmartDashboard.putNumber("sharp1", 1); 
        // else {
        //     speedZ = (float)(lastGyro - RobotContainer.train.getYaw()); 
        //     SmartDashboard.putNumber("sharp1", 2); 
        // }

        diffX = X - rightSonic;
        SmartDashboard.putNumber("diffX", diffX);
        speedX = Function.TransitionFunction(diffX, sonicArray);

        RobotContainer.train.setAxisSpeed(-speedX, speedZ);

        // if(finishX) {
        //     RobotContainer.train.setAxisSpeed(speedX,  speedZ);
        // }

        finishZ = Function.BooleanInRange(diffSonic, -0.5, 0.5);
        finishX = Function.BooleanInRange(diffX, -5, 5);

        if(finishZ && finishX) {
            RobotContainer.train.setAxisSpeed(0, 0);
            isFirstIter = true;
            lastGyro = 0;
            diffZ = 0;
            return true;
        }
        return false;
    }

    private boolean rotateZ(double Z) {
        if(isFirstIter) {
            lastGyro = RobotContainer.train.getYaw();
            isFirstIter = false;
        }
        double currGyro = RobotContainer.train.getYaw();
        diffZ = currGyro - Z;
        SmartDashboard.putNumber("diffZ", diffZ); 
        speedZ = Function.TransitionFunction(diffZ, degFunction);
        RobotContainer.train.setAxisSpeed(0, -speedZ);
        
        return Function.BooleanInRange(diffZ, -0.3, 0.3);       
    }
    
}