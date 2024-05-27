package frc.robot.StateMachine.Cases;

import org.opencv.core.Point;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.functions.Function;
import frc.robot.subsystems.Training;

public class RotateAligToFruit implements IState {
    
    private Training train = RobotContainer.train;

    private double fruitPosX = 0; 

    private double startKoef = 0; 
    private double diffSpeed = 0; 
    private boolean rotateStop = false; 

    private boolean objectFind = false;

    private double stopTimer;

    private static final double[][] speedForRotate =  { { 0, 50, 100, 150, 200 }, { 0, 12, 18, 25, 30} };

    private double[][] startKoefSpeedForX = { { 0, 333, 666, 1000 }, { 0, 0.33, 0.66, 1 } };

    public RotateAligToFruit() {
         
    }

    @Override
    public boolean execute() {
        
        RobotContainer.train.nowTask = 2; 

        train.setGripServoValue(130);
        train.setGripRotateServoValue(260);
        
        if (train.centersForClass.isEmpty()) {

            fruitPosX = 0; 
            objectFind = false;

        } else {

            for (Point center : train.centersForClass) {

                fruitPosX  = center.x; 
    
            }

            objectFind = true;
        }

        this.startKoef = Function.TransitionFunction(System.currentTimeMillis() - StateMachine.iterationTime, startKoefSpeedForX);
        this.diffSpeed = Function.TransitionFunction(this.fruitPosX - 290, speedForRotate);
        this.rotateStop = Function.BooleanInRange(this.diffSpeed, -3, 3);

        SmartDashboard.putNumber("diffSpeed: ", this.fruitPosX - 290);

        if (objectFind) {

            train.rotateMotorSpeedThread = diffSpeed * startKoef;

        } else {

            train.rotateMotorSpeedThread = 0;

        }

        train.setAxisSpeed(0, 0);
        SmartDashboard.putBoolean("RotateAlig.rotateStop", rotateStop);
        if (rotateStop) {

            train.rotateMotorSpeedThread = 0;
            return Timer.getFPGATimestamp() - stopTimer > 2;
        } else {

            stopTimer = Timer.getFPGATimestamp();
            return Timer.getFPGATimestamp() - StateMachine.startTime > 1.5 && this.rotateStop ;
        }      
    }
    
}
