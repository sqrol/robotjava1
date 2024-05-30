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

    private boolean objectDetectionFlag = true;

    private double currentTargetDegree = 0; 

    private double stopTimer;

    private boolean oneObject = true;

    private static final double[][] speedForRotate =  { { 0, 50, 100, 150, 200 }, { 0, 12, 18, 25, 30} };

    private double[][] startKoefSpeedForX = { { 0, 333, 666, 1000 }, { 0, 0.33, 0.66, 1 } };

    private static final double[][] arrForLift = { { 0, 290, 640} , { -60, 0, 60} };

    public RotateAligToFruit() {
         
    }

    public RotateAligToFruit(boolean oneObject) {
         this.oneObject = oneObject; 
    }

    @Override
    public boolean execute() {
        
        RobotContainer.train.nowTask = 2; 

        train.setGripServoValue(130);
        train.setGripRotateServoValue(270);
        
        if (train.centersForClass.isEmpty()) {

            fruitPosX = 0; 
            objectFind = false;

        } else {
            if (objectDetectionFlag) {
                for (Point center : train.centersForClass) {
                    fruitPosX  = center.x; 
                }
                if (!oneObject) {
                    objectDetectionFlag = false;
                }
            }
            objectFind = true;
        }

        if (oneObject) {
            if (objectFind) {
                this.startKoef = Function.TransitionFunction(System.currentTimeMillis() - StateMachine.iterationTime, startKoefSpeedForX);
                this.diffSpeed = Function.TransitionFunction(this.fruitPosX - 290, speedForRotate);
                this.rotateStop = Function.BooleanInRange(this.diffSpeed, -3, 3);
                train.rotateMotorSpeedThread = diffSpeed * startKoef;
            } else {
                train.rotateMotorSpeedThread = 0;
            }
        } else {
            currentTargetDegree = Function.TransitionFunction(fruitPosX, arrForLift);
            SmartDashboard.putNumber("diffSpeed: ", currentTargetDegree);
            this.rotateStop = train.rotateToPos(currentTargetDegree); 
        }

        train.setAxisSpeed(0, 0);

        if (rotateStop) {
            train.rotateMotorSpeedThread = 0;
            
            return this.rotateStop && Timer.getFPGATimestamp() - StateMachine.startTime > 1.5; 
        } else {
            stopTimer = Timer.getFPGATimestamp();
        }

        return Timer.getFPGATimestamp() - StateMachine.startTime > 10;
    }
}
