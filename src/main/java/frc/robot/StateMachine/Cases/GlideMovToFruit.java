package frc.robot.StateMachine.Cases;

import org.opencv.core.Point;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.functions.Function;
import frc.robot.subsystems.Training;

public class GlideMovToFruit implements IState {
    
    private Training train = RobotContainer.train;

    private double fruitPosY = 0; 

    private double startKoef = 0; 
    private double diffSpeed = 0; 
    private boolean rotateStop = false; 

    private boolean objectFind = false;

    private double stopTimer;

    private static final double[][] speedForGlideServo = { { -10, -8, -6, -4, -2, -1, 0, 1, 2, 4, 6, 8, 10 } ,
                                                 { -0.4, -0.4, -0.3, -0.25, -0.2, -0.15, 0, 0.15, 0.2, 0.25, 0.3, 0.4, 0.4} };

    public GlideMovToFruit() {
         
    }

    @Override
    public boolean execute() {
        RobotContainer.train.nowTask = 2; 

        if (train.centersForClass.isEmpty()) {

            fruitPosY = 0; 
            objectFind = false;

        } else {

            for (Point center : train.centersForClass) {

                fruitPosY  = center.y; 
                SmartDashboard.putNumber("center.y: ", center.y);
    
            }

            objectFind = true;
        }

        // if (Function.BooleanInRange(this.fruitPosY, 120, 150)) {
        //     train.justMoveForGlide(false);
        // } else {
        //     train.justMoveForGlide(true);
        // }

        double glideServoSpeed = Function.TransitionFunction(fruitPosY - 144, speedForGlideServo);

        if (objectFind) {
            
            train.justMoveForGlide(glideServoSpeed);

        } else {

            train.justMoveForGlide(0.4);

        }

        // train.setAxisSpeed(0, 0);

        // if (rotateStop) {

        //     train.rotateMotorSpeedThread = 0;
        //     return Timer.getFPGATimestamp() - stopTimer > 3;
        // } else {
            
        //     stopTimer = Timer.getFPGATimestamp();
        //     return Timer.getFPGATimestamp() - StateMachine.startTime > 1.5 && this.rotateStop ;
        // }   
        return false;   
    }
    
}
