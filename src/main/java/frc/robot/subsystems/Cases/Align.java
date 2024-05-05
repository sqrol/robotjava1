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
    private double coefForTime = 0;

    Training train = RobotContainer.train;
 
    private static double[][] XArray = { { 0, 0.1, 0.5, 0.8, 1.5, 2.5, 5, 10, 15, 25, 30 },
                                          { 0, 5, 10, 12, 15, 25, 30, 60, 70, 80, 90 } };

    private static double[][] sonicArray = { { 0.7, 4, 7, 15, 30 },
                                             { 12, 30, 40, 70, 95 } };

    private static double[][] degFunction = { { 0.1, 0.5, 1.5, 2, 5, 15, 20, 25, 35}, 
                                             { 25, 30, 35, 40, 45, 50, 55, 60, 65 } };


    private static double[][] arrayForTime = { { 0, 1},
                                             { 0, 1} };


    // private static double[][] degFunction = {{ 0.5, 4.4, 10, 25, 38, 47, 60, 70, 80, 90, 100, 100, 120, 130 }, 
    //                                          { 4.1, 9.5, 15, 26, 30, 37, 45, 51, 57, 70, 76, 85, 93, 97 } };

    // private static double[][] degFunction = {{ 0.4, 1.5, 2.5, 5, 10, 15, 20, 25, 35}, 
    //                                          { 8, 10, 12, 18, 20, 25, 30, 35, 45} };

    // private static double[][] degFunction = {{ 0.1, 0.5, 1.5, 2, 5, 15, 20, 25, 35}, 
    //                                          { 15, 18, 20, 25, 30, 35, 40, 45, 50} };


                              

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

        coefForTime = Function.TransitionFunction(Timer.getFPGATimestamp() - StateMachine.startTime, arrayForTime); // Начало движения по времени

        double leftSharp = RobotContainer.train.getLeftSharpDistance();   
        double rightSharp = RobotContainer.train.getRightSharpDistance();

        diffX = X - Math.min(leftSharp, rightSharp);
        speedX = Function.TransitionFunction(diffX, XArray);

        if (Math.min(leftSharp, rightSharp) < 20) {
            diffSharp = leftSharp - rightSharp;
            speedZ = -Function.TransitionFunction(diffSharp, degFunction);
        } else {
            speedZ = (float)(lastGyro - RobotContainer.train.getYaw());
        }

        RobotContainer.train.setAxisSpeed(-speedX * coefForTime, speedZ);

        SmartDashboard.putNumber("OutSpeed", speedX);

        finishZ = Function.BooleanInRange(speedZ, -0.2, 0.2);
        finishX = Function.BooleanInRange(speedX, -0.5, 0.5);

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