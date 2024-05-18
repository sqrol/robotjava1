package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.StateMachine.IState;
import frc.robot.StateMachine.StateMachine;
import frc.robot.subsystems.Training;
/**
 * Данный класс используется для выравнивания по инфракрасным и ультразвуковым датчикам, 
 * а так же для поворота робота вокруг своей оси.
 */
public class Align implements IState {
    private String sensors;

    private boolean isFirstIter = true;
    private boolean finishX, finishZ, exit = false;

    private double X, Z, distToWall = 0;
    private double speedX, diffX = 0;
    private double speedZ, diffZ = 0;
    private double diffSharp, lastGyro = 0;
    private double coefForTime = 0;
    private double elapsedTime = 0;

    Training train = RobotContainer.train;
 
    private static double[][] XArray = { { 0, 0.1, 0.8, 1.5, 2.5, 5, 10, 15, 25, 30 },
                                          { 0, 0.4, 2, 6, 13, 24, 30, 60, 85, 95 } };

    private double[][] sonicArray = { { 0, 1, 2.5, 5, 10, 12, 14, 18, 35, 50, 100}, 
                                          { 0, 3, 10, 12, 18, 25, 35, 50, 75, 85, 95} };

    // private double[][] sonicArray = { { 0, 1, 2.5, 5, 10, 12, 50, 100}, 
    //                                       { 0, 3, 10, 12, 18, 25, 40, 50} };

    private static double[][] degFunction = { { 0.1, 0.5, 1.5, 2, 5, 15, 20, 25, 35 }, 
                                               { 6, 11, 17, 22, 28, 33, 38, 43, 50 } };

    private static double[][] arrayForTime = { { 0, 1 },
                                               { 0, 1 } };

    public Align(String sensors, double X, double Z, double distToWall) {
        this.sensors = sensors;
        this.X = X;
        this.Z = Z;
        this.distToWall = distToWall;
        isFirstIter = true;
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
            
            case "sharpSonic":
                exit = travelSharpSonic(X, distToWall);
                break;
            }
        return exit;
    }
    /**
     * Метод для подъезда к стене и выравнивания по ИК датчикам.
     * @param X - расстояние до стены в см, на которое робот подъедет и выровняется.
     * @return true, если робот стоит ровно.
     */
    private boolean alignIR(double X) {
        if(isFirstIter) {
            lastGyro = RobotContainer.train.getLongYaw();
            train.resetEncLeft();
            train.resetEncRight();
            isFirstIter = false;
        }

        coefForTime = Function.TransitionFunction(Timer.getFPGATimestamp() - StateMachine.startTime, arrayForTime); // Начало движения по времени

        double leftSharp = RobotContainer.train.getLeftSharpDistance();   
        double rightSharp = RobotContainer.train.getRightSharpDistance();

        diffX = X - Math.min(leftSharp, rightSharp);
        speedX = Function.TransitionFunction(diffX, XArray);

        if (Math.min(leftSharp, rightSharp) < 20) {
            diffSharp = leftSharp - rightSharp;
            speedZ = Function.TransitionFunction(diffSharp, degFunction);
        } else {
            speedZ = lastGyro - RobotContainer.train.getLongYaw();
        }

        RobotContainer.train.setAxisSpeed(-speedX * coefForTime, speedZ);

        finishZ = Function.BooleanInRange(speedZ, -0.2, 0.2);
        finishX = Function.BooleanInRange(speedX, -0.5, 0.5);

        if(finishZ && finishX) {
            train.setAxisSpeed(0, 0);
            isFirstIter = true;
            lastGyro = 0;
            diffZ = 0;
            train.resetEncLeft();
            train.resetEncRight();
            train.resetGyro();
            return true;
        }
        return finishX && finishZ;
    }
    /**
     * Метод для подъезда к стене по ультразвуковому датчику, установленному сзади.
     * @param X - расстояние до стены в см, на которое робот подъедет.
     * @return true, если робот подъехал.
     */
    private boolean travelSonic(double X) {

        if(isFirstIter) {
            lastGyro = train.getLongYaw();
            isFirstIter = false;
        }

        double time = System.currentTimeMillis();

        double backSonicDist = train.getBackSonicDistance();

        diffX = X - backSonicDist;
        diffZ = lastGyro - train.getLongYaw();

        double speedX = Function.TransitionFunction(diffX, sonicArray);
        double speedZ = Function.TransitionFunction(diffZ, degFunction);

        train.setAxisSpeed(speedX, speedZ);
        // if(Math.abs(diffX) < backSonicDist) {
        //     elapsedTime = System.currentTimeMillis() - time;
        //     if(elapsedTime < 2000) {
        //         train.setAxisSpeed(0, speedZ);
        //     }
        // }
        finishX = Function.BooleanInRange(X - backSonicDist, -0.5, 0.5);
        finishZ = Function.BooleanInRange(lastGyro - train.getLongYaw(), -0.5, 0.5);

        if(finishX && finishZ) {
            train.setAxisSpeed(0, 0);
            isFirstIter = true;
            lastGyro = 0;
            diffZ = 0;
            train.resetEncLeft();
            train.resetEncRight();
            train.resetGyro();
            return true;
            
        }
        return false;
    }

    /**
     * Метод для одновременного движения по инфракрасным и ультразвуковым датчикам.
     * @param X - расстояние до передней стены в см.
     * @param distToWall - расстояние до правой боковой стены в см, которое робот должен удерживать.
     * @return true, если робот доехал и выровнялся по ИК и выровнялся по ультразвуковому датчику.
     */
    private boolean travelSharpSonic(double X, double distToWall) {

        if(isFirstIter) {
            lastGyro = train.getLongYaw();
            isFirstIter = false;
        }

        double leftSharp = train.getLeftSharpDistance();
        double rightSharp = train.getRightSharpDistance();

        diffX = X - Math.min(rightSharp, leftSharp);
        speedX = Function.TransitionFunction(diffX, XArray);

        double sideSonicDist = train.getSideSonicDistance();

        double wallDiff = distToWall - sideSonicDist;

        return false;
    }
}