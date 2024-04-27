package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class StartPos implements IState{
    private Training train = RobotContainer.train; 
    private boolean fristCall = true; 
    @Override
    public boolean execute() {

        if (fristCall) {
            train.resetGlideEncoder(); 
            fristCall = false; 
        }
        if (!train.getLimitSwitchGlide()) {
            train.initGlide = true;
        } else {
            train.initGlide = false;
        }

        train.reset2Motors();
        // train.OdometryReset(0, 0);
        train.resetGyro();
        // train.setAxisSpeed(30, 0);
        train.finish = false;
        train.fristInitForGlide = true; 
        // train.setAxisSpeed(20, 0);

        // if (train.getLimitSwitchLift()) {
        //     train.liftMotorSpeedThread = 0;
        // } else {
        //     train.liftMotorSpeedThread = 20;
        // }
        double speed = -35; 

        if (train.fristInitForGlideDone) {
            // if (train.getLimitSwitchGlide() && speed > 0) {
            //     train.glideMotorSpeedThread = 0;
            //     train.resetGlideEncoder(); 
            // } else {
            //     train.glideMotorSpeedThread = speed;
            // }
            // train.glideToMovePos(50); 
        }




        // return train.getLimitSwitch() && Timer.getFPGATimestamp() - StateMachine.startTime > 1;
        return false;
    }
}
