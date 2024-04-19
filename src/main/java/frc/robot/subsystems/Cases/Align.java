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
    private boolean exit = false;
    private boolean finishX = false;
    private boolean finishY = false;
    private boolean finishZ = false;

    private double X = 0;
    private double Y = 0;
    private double Z = 0;
    private double currSharp = 0;
    private double speedX = 0;
    private double diffX = 0;
    private double speedY = 0;
    private double diffY = 0;
    private double diffZ = 0;
    private double diffSharp = 0;
    private double lastGyro = 0;
    private double speedZ = 0;

    private static double[][] degreeSharpArray = { {0, 0.1, 0.5, 1, 1.5, 2, 5, 10, 15, 20, 30, 45},
                                                  { 0, 5, 10, 20, 25, 30, 35, 40, 45, 50, 55, 56} };

    private static double[][] degreeSonicArray = { {0, 0.1, 0.5, 1, 1.5, 2, 5, 10, 15, 20, 30, 45},
                                                  { 0, 5, 10, 20, 25, 30, 35, 40, 45, 50, 55, 56} };

    private static double[][] sharpXFunc = {{0, 0.9, 1.7, 4, 9, 26, 87, 120, 180, 250, 570 },
                                          { 0f, 4, 10.5, 17, 24, 30, 41, 56, 63, 71, 95 }}; 


    // private static float[][] sharpXArray = { {0f, 0.9f, 1.4f, 3, 7, 16, 50, 117, 180, 240, 390 },
    //                                          { 0f, 4f, 10.5f, 17, 24, 29, 33, 39, 47, 54, 60 } }; 

    private static double[][] sharpXArray = { {0, 0.9, 1.4, 3, 7, 16, 50, 117, 180, 240, 390 },
                                             { 0f, 4, 10.5, 18, 30, 42, 50, 60, 69, 81, 90 } }; 

    // private static float[][] sonicYArray = { { 0f, 0.5f, 4.5f, 21, 31, 47, 76, 93, 150},
    //                                        { 0f, 6.1f, 15.5f, 25, 38, 47, 60, 86, 90} };

    private static double[][] sonicYArray = { { 0.7, 2.6, 5, 12, 35, 41, 52, 64, 71, 80},
                                            { 0, 15, 18, 26, 32, 38, 47, 60, 86, 90} };

    private static double[][] degFunction = {{0.3, 2.4, 10, 25, 38, 47, 60, 70, 80, 90, 100, 100, 120, 130}, 
                                            {4, 9, 15, 26, 30, 37, 45, 51, 57, 70, 76, 85, 93, 100}};


    public Align(String sensors, double X, double Y, double Z){
        this.sensors = sensors;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    @Override
    public boolean execute() {
        switch(sensors){
            case "sharp":
                exit = alignIR(X);
            break;
            case "sonic":
                exit = travelSonic(Y);
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

        double leftSharp = RobotContainer.train.getLeftSharpDistance() + 0.9;  
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
        speedX = Function.TransitionFunction(diffX, sharpXArray);

        RobotContainer.train.setAxisSpeed(speedX, 0);
        if(finishX) {
            RobotContainer.train.setAxisSpeed(speedX,  speedZ);
        }

        finishZ = Function.BooleanInRange(diffSharp, -0.5f, 0.5f);
        finishX = Function.BooleanInRange(diffX, -2f, 2f);

        // return finishX && finishZ;
        if(finishZ && finishX) {
            RobotContainer.train.setAxisSpeed(0,  0);
            isFirstIter = true;
            lastGyro = 0;
            diffZ = 0;
            return true;
        }
        return false;
    }
    
    private boolean travelSonic(double Y) {
        if(isFirstIter) {
            lastGyro = RobotContainer.train.getYaw();
            isFirstIter = false;
        }
        if(Y > 0) {
            diffY = Y - RobotContainer.train.getRightSonicDistance();
            speedY = Function.TransitionFunction(diffY, sonicYArray);
        } else if(Y < 0) {
            diffY = Y + RobotContainer.train.getLeftSonicDistance();
            speedY = Function.TransitionFunction(diffY, sonicYArray);
        }

        diffZ = lastGyro - RobotContainer.train.getYaw();
        speedZ = Function.TransitionFunction(diffZ, degFunction);

        RobotContainer.train.setAxisSpeed(0, speedZ);

        if(finishY) {
            RobotContainer.train.setAxisSpeed(0,  speedZ);
        }
        finishY = Function.BooleanInRange(diffY, -0.5f, 0.5f);
        finishZ = Function.BooleanInRange(diffZ, -2f, 2f);

        if(finishZ && finishY) {
            RobotContainer.train.setAxisSpeed(0,  0);
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
        
        return Function.BooleanInRange(diffZ, -0.3f, 0.3f);       
    }
    
}