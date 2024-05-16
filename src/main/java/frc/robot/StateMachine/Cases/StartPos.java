package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class StartPos implements IState{
    private Training train = RobotContainer.train; 
    private boolean firstCall = true; 

    public StartPos() {
        this.firstCall = true;
    }

    @Override
    public boolean execute() {
        
        if (firstCall) {
            train.resetRotateEncoder(); 
            firstCall = false;
        }

        // train.rotateMotorSpeedThread = 0; 
        train.setGreenLED(true);
        train.setRedLED(true);
        train.reset2Motors();
        train.OdometryReset(0, 0);
        train.resetGyro();
        // train.setAxisSpeed(0.0f, 30.0f);

        // train.finish = false;
        // train.firstInitForGlide = true; 

        // // train.setAxisSpeed(20, 0);

        // if (train.getLimitSwitchLift()) {
        //     train.liftMotorSpeedThread = 0;
        // } else {
        //     train.liftMotorSpeedThread = 50;
        // }
        double currTime = Timer.getFPGATimestamp();
        // double speed = -35; 
        // train.setAxisSpeed(50, 0);

        // train.firstInitForLift = true; 

        // train.firstInitForGlide = true;
        // train.rotateToPos(45);
        // train.setGripRotateServoValue(200);
        // train.glideMotorSpeedThread = -10; 
            // train.setGripServoValue(15);
        // if (train.firstInitForGlideDone) {
        //     if (train.getLimitSwitchGlide() && speed > 0) {
        //         train.glideMotorSpeedThread = 0;
        //         train.resetGlideEncoder(); 
        //     } else {
        //         train.glideMotorSpeedThread = speed;
        //     }
        //     train.glideToMovePos(100); 
        // }
        
        // return train.getLimitSwitchLift() && Timer.getFPGATimestamp() - StateMachine.startTime > 1;
        return true;
        // return Timer.getFPGATimestamp() - StateMachine.startTime > 10;
        // return train.successInit && Timer.getFPGATimestamp() - StateMachine.iterationTime > 5;
        // return Timer.getFPGATimestamp() - StateMachine.startTime > 5;
    }
}
