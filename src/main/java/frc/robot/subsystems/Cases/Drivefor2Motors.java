package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class Drivefor2Motors implements IState {

    private Training train = RobotContainer.train; 

    private boolean exit = false;
    private boolean isFirstX, isFirstZ = true;

    private double x, posX, spX, speedX, startKoef; 
    private double z, speedZ;  
    private double currentRight, currentLeft; 
    private double nowYaw;

    // private double[][] speedXArray = { { 0, 0.3, 2, 40, 150, 300, 700, 1000, 1300 },
    //                                     { 0, 5, 7, 20, 67, 75, 80, 95, 100 } };

    // private double[][] speedXArray = { { 0, 5, 15, 40, 70, 150, 170, 220, 300 },
    //                                     { 0, 6, 12, 16, 30, 35, 40, 60, 90} };

    // private double[][] speedXArray = { { 5, 15, 42, 75, 90, 120, 180, 250, 300 }, 
    //                                     { 11, 18, 21, 28, 38, 41, 54, 80, 95 } }; 

    private double[][] speedXArray = { { 5, 15, 42, 75, 90, 120, 180, 250, 300 }, 
                                        { 30, 35, 45, 50, 55, 60, 65, 80, 95 } }; 

    private double[][] speedZArray = { { 0, 1.2, 2, 3, 7, 50, 65, 79, 90 },
                                       { 0, 7, 10, 18, 25, 40, 63, 70, 80 } };

    private double[][] startMoveForXArray = { {0, 0.5}, 
                                               {0, 1} };

    public Drivefor2Motors(double x, double z) {
        this.x = x; 
        this.z = z;  
        isFirstX = true;
        isFirstZ = true;
    }

    @Override
    public boolean execute() {
        if (x != 0) {
            exit = DriveForX();
        } else if (z != 0) {
            exit = DriveForZ();
        } else {
            return true;
        }
        return exit;
    }

    private boolean DriveForX() {
        if(isFirstX) {
            train.reset2Motors();
            train.resetGyro();
            isFirstX = false;
        }

        startKoef = Function.TransitionFunction(Timer.getFPGATimestamp() - StateMachine.startTime, startMoveForXArray);
        
        SmartDashboard.putNumber("first", 1);
        currentRight = train.getRightEncoder();
        currentLeft = train.getLeftEncoder();

        nowYaw = train.getYaw();

        posX = (currentRight - currentLeft) / 10.05;
        // posX +=  Math.cos(nowYaw) * spX - Math.sin(nowYaw);

        speedX = Function.TransitionFunction(x - posX, speedXArray);
        speedZ = Function.TransitionFunction((0 - nowYaw) * 3.8, speedZArray);

        SmartDashboard.putNumber("spremX", spX);
        SmartDashboard.putNumber("diffX", x - posX);
        SmartDashboard.putNumber("posX", posX);
        SmartDashboard.putNumber("nowYaw", nowYaw);

        train.setAxisSpeed(speedX * startKoef, speedZ);

        return Function.BooleanInRange(x - posX, -5, 5) && Function.BooleanInRange(0 - nowYaw, -0.2, 0.2); 
    }

    private boolean DriveForZ() {
        if(isFirstZ) {
            isFirstZ = false;
            train.resetGyro();
        }

        nowYaw = train.getLongYaw();
        speedZ = Function.TransitionFunction(z - nowYaw, speedZArray);
        train.setAxisSpeed(0, speedZ);

        return Function.BooleanInRange(z - nowYaw, -0.2, 0.2); 
    }
}
