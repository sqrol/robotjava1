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
        train.setIndication("WAITING");
        train.reset2Motors();
        train.OdometryReset(0, 0);
        train.setAxisSpeed(0, 0);
        train.resetGyro();
        // train.setAxisSpeed(0.0f, 30.0f);

        if (train.getLimitSwitchLift()) {
            train.liftMotorSpeedThread = 0;
            train.resetLiftEncoder();
        } else {
            train.liftMotorSpeedThread = 50;
        }
        
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
        // train.rotateMotorSpeedThread = 10;

        if(train.successInit) {
            train.resetLiftEncoder();
            train.resetEncRotate();
            // train.setGripServoValue(130.0); // 177
            train.setGripServoValue(123); // 177
            train.setGripRotateServoValue(279);
            if(Timer.getFPGATimestamp() - StateMachine.iterationTime > 8 && train.getStartButton()){
                train.setIndication("IN PROCESS");
                return true;
            }
        }
        return false;
    }
}
