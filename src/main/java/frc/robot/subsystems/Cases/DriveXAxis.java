package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

// Новый Case для езды

public class DriveXAxis implements IState {

    public Training train = RobotContainer.train;

    private double XPosition, ZPosition = 0; 
    private boolean finishX, finishZ = false;
    private double currentAxisX, outSpeedForX, outSpeedForZ  = 0; 

    private boolean isFirst = true;

    private double[][] speedXArray = { { 0, 5, 10, 12, 14, 18, 35, 50, 100}, 
                                       { 0, 12, 15, 25, 35, 50, 75, 85, 95} };

    private double[][] speedZArray = { { 0, 0.5, 1.2, 3, 6, 12, 26, 32, 50 }, 
                                       { 0, 8, 10, 14, 18, 24, 32, 48, 70 } };

    private double[][] speedZArrayJustTurn = { { 0, 0.5, 1.2, 3, 6, 12, 20, 32, 90 }, 
                                       { 0, 3, 8, 10, 15, 24, 32, 48, 70 } };

    private double[][] startKoefSpeedForX = { { 0, 1 }, { 0, 1 } };


    public DriveXAxis(double XPosition, double ZPosition){
        this.XPosition = XPosition;
        this.ZPosition = ZPosition;
        this.isFirst = true;
    }

    @Override
    public boolean execute() {

        if (isFirst) {
            allReset();
            isFirst = false;
        }

        double startKoef = Function.TransitionFunction(Timer.getFPGATimestamp() - StateMachine.startTime, startKoefSpeedForX);
        double gyro = train.getLongYaw(); 

        if (XPosition != 0) {
            
            double currentRight = -train.getEncRightThread();
            double currentLeft = -train.getEncLeftThread();
    
            currentAxisX = ((currentRight + currentLeft) / 2) / 48;
    
            double[] polar = Function.ReImToPolar(currentAxisX, 0);
            double[] decard = Function.PolarToReIm(polar[0], (polar[1] + Math.toRadians(gyro))); 
    
            double diff = XPosition - decard[0];
            
            outSpeedForX = Function.TransitionFunction(diff, speedXArray);
            outSpeedForZ = Function.TransitionFunction(0 - gyro, speedZArray);
    
        } else {
            outSpeedForX = 0;
            outSpeedForZ = Function.TransitionFunction(ZPosition - gyro, speedZArrayJustTurn);
        }

        train.setAxisSpeed(outSpeedForX * startKoef, outSpeedForZ * startKoef);

        finishX = Function.BooleanInRange(outSpeedForX, -1.5, 1.5);
        finishZ = Function.BooleanInRange(outSpeedForZ, -0.2, 0.2); 
        
        return finishX && finishZ;
    }

    private void allReset() {
        train.resetEncRight();
        train.resetEncLeft();
        train.resetGyro();
    }

}