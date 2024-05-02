package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class Align implements IState {
    private String sensors;

    private boolean isFirstIter = true;
    private boolean finishX, finishZ, exit = false;

    private double X, Z = 0;
    private double speedX, diffX = 0;
    private double speedZ, diffZ = 0;
    private double diffSharp, diffSonic, lastGyro = 0;

    Training train = RobotContainer.train;

    private static double[][] degreeSharpArray = { {0, 0.1, 0.5, 1, 1.5, 2, 5, 10, 15, 20, 30, 45},
                                                  { 0, 5, 10, 20, 25, 30, 35, 40, 45, 50, 55, 56} };

    private static double[][] degreeSonicArray = { {0, 0.1, 0.5, 1, 1.5, 2, 5, 10, 15, 20, 30, 45},
                                                  { 0, 5, 10, 20, 25, 30, 35, 40, 45, 50, 55, 56} };

    private static double[][] sharpXFunc = { {0, 0.9, 1.7, 4, 9, 26, 87, 120, 180, 250, 570 },
                                          { 0f, 4, 10.5, 17, 24, 30, 41, 56, 63, 71, 95 } }; 
 

    // private static double[][] XArray = { { 0, 0.9, 1.4, 3, 7, 9, 11, 14, 16, 18, 21 },
    //                                      { 0, 4, 10.5, 20, 35, 42, 50, 60, 69, 81, 90 } }; 

    // private static double[][] XArray = { { 0, 0.5, 1.5, 2.5, 5, 10, 15, 20, 25, 30, 35 },
    //                                      { 0, 5, 10.5, 15, 20, 30, 40, 50, 60, 70, 80 } };
    private static double[][] XArray = { { 0, 0.5, 1.5, 2.5, 30, 35 },
                                          { 0, 5, 10.5, 15, 70, 90 } };
    private static double[][] sonicArray = { { 0.7, 4, 7, 15, 30 },
                                              { 0, 5, 18, 38, 90 } };


    // private static double[][] degFunction = {{ 0.5, 4.4, 10, 25, 38, 47, 60, 70, 80, 90, 100, 100, 120, 130 }, 
    //                                          { 4.1, 9.5, 15, 26, 30, 37, 45, 51, 57, 70, 76, 85, 93, 97 } };

    // private static double[][] degFunction = {{ 0.4, 1.5, 2.5, 5, 10, 15, 20, 25, 35}, 
    //                                          { 8, 10, 12, 18, 20, 25, 30, 35, 45} };

    // private static double[][] degFunction = {{ 0.1, 0.5, 1.5, 2, 5, 15, 20, 25, 35}, 
    //                                          { 15, 18, 20, 25, 30, 35, 40, 45, 50} };

    private static double[][] degFunction = {{ 0.1, 0.5, 1.5, 2, 5, 15, 20, 25, 35}, 
                                             { 25, 30, 35, 40, 45, 50, 55, 60, 65} };


    private static double[][] degFunctionSonic = {{ 0.3, 2.4, 10, 25, 38, 47, 60, 70, 80, 90, 100, 100, 120, 130 }, 
                                                  { 4.1, 9.5, 15, 26, 30, 37, 45, 51, 57, 70, 76, 85, 93, 100 } };                                

    public Align(String sensors, double X, double Z){
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
        double rightSharp = RobotContainer.train.getRightSharpDistance() + 0.57;

        // if (Math.min(leftSharp, rightSharp) < 15) 

        diffX = X - Math.min(leftSharp, rightSharp);
        SmartDashboard.putNumber("diffX", diffX);
        speedX = Function.TransitionFunction(diffX, XArray);
        

        if (Math.min(leftSharp, rightSharp) < 17) {
            diffSharp = leftSharp - rightSharp;
            SmartDashboard.putNumber("DiffForIr", diffSharp); 
            speedZ = -Function.TransitionFunction(diffSharp, degFunction);
            SmartDashboard.putNumber("Hello World", 1); 
        } else {
            SmartDashboard.putNumber("Hello World", 2); 
            speedZ = (float)(lastGyro - RobotContainer.train.getYaw());
        }

        RobotContainer.train.setAxisSpeed(-speedX, speedZ);
    
        // diffSharp = leftSharp - rightSharp;
        // SmartDashboard.putNumber("DiffSharp", diffSharp); 
        // speedZ = -Function.TransitionFunction(diffSharp, degFunction);
        // SmartDashboard.putNumber("sharp1", 1); 

        // else {
        //     speedZ = (float)(lastGyro - RobotContainer.train.getYaw()); 
        //     SmartDashboard.putNumber("sharp1", 2); 
        // }

        
        // if(finishX) {
        //     RobotContainer.train.setAxisSpeed(speedX,  speedZ);
        // }

        finishZ = Function.BooleanInRange(diffSharp, -0.5, 0.5);
        finishX = Function.BooleanInRange(diffX, -2, 2);

        // 
        if(finishZ && finishX) {
            RobotContainer.train.setAxisSpeed(0, 0);
            isFirstIter = true;
            lastGyro = 0;
            diffZ = 0;
            return true;
        }
        return finishX && finishZ;
    }
    
    private boolean travelSonic(double X) {

        if(isFirstIter) {
            lastGyro = train.getLongYaw();
            isFirstIter = false;
        }
        
        double backSonicDist = train.getBackSonicDistance();
        diffX = X - backSonicDist;
        diffZ = lastGyro - train.getLongYaw();

        double speedX = Function.TransitionFunction(diffX, sonicArray);
        double speedZ = Function.TransitionFunction(diffZ, degFunction);

        train.setAxisSpeed(speedX, speedZ);

        finishX = Function.BooleanInRange(X - backSonicDist, -1, 1);
        finishZ = Function.BooleanInRange(lastGyro - train.getLongYaw(), -0.5, 0.5);
        SmartDashboard.putNumber("diffXSonic", diffX);

        if(finishX && finishZ) {
            train.setAxisSpeed(0, 0);
            isFirstIter = true;
            lastGyro = 0;
            diffX = 0;
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
        RobotContainer.train.setAxisSpeed((float)0, (float)-speedZ);
        
        return Function.BooleanInRange(diffZ, -0.3, 0.3);       
    }
    
}