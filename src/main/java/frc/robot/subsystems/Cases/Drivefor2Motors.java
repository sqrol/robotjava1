package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class Drivefor2Motors implements IState {

    private Training train = RobotContainer.train; 

    private boolean exit = false;
    private boolean isFirstX, isFirstZ = true;

    private double x, posX, spX, speedX; 
    private double z, speedZ;  
    private double currentRight, currentLeft; 
    private double lastGyro, nowYaw;

    private double[][] speedXArray = { { 0, 0.3, 2, 5, 15, 40, 70, 150, 170, 300 },
                                       { 0, 0.3, 1, 3, 7, 16, 28, 35, 44, 51 } };  

    private double[][] speedZArray = { { 0, 1.2, 2, 3, 7, 20, 32, 50, 65, 79, 90 },
                                       { 0, 1.5, 3, 5, 7, 20, 30, 40, 63, 80, 90 } };  

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
        
        SmartDashboard.putNumber("first", 1);
        currentRight = train.getRightEncoder();
        currentLeft = train.getLeftEncoder();

        nowYaw = train.getYaw();

        posX = (currentRight - currentLeft) / 2.2;
        // posX +=  Math.cos(nowYaw) * spX - Math.sin(nowYaw);

        speedX = Function.TransitionFunction(x - posX, speedXArray);
        speedZ = Function.TransitionFunction((0 - nowYaw) * 3.8, speedZArray);

        SmartDashboard.putNumber("spremX", spX);
        SmartDashboard.putNumber("lastgyro", lastGyro);
        SmartDashboard.putNumber("posX", posX);
        SmartDashboard.putNumber("nowYaw", nowYaw);
        SmartDashboard.putNumber("diffX", x - posX);

        train.setAxisSpeed(speedX, speedZ);

        return Function.BooleanInRange(x - posX, -5, 5) && Function.BooleanInRange(0 - nowYaw, -0.2, 0.2); 
    }

    private boolean DriveForZ() {
        if(isFirstZ) {
            isFirstZ = false;
            train.resetGyro();
        }

        SmartDashboard.putNumber("diffZ", z - nowYaw);
        nowYaw = train.getYaw();
        speedZ = Function.TransitionFunction(z - nowYaw, speedZArray);
        train.setAxisSpeed(0, speedZ);

        return Function.BooleanInRange(z - nowYaw, -0.2, 0.2); 
    }

    
}
