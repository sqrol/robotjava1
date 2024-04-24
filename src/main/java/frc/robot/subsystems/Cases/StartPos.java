package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class StartPos implements IState{
    private Training train = RobotContainer.train; 
    @Override
    public boolean execute() {
        if (!train.getLimitSwitch()) {
            train.initLift = true;
        } else {
            train.initLift = false;
        }
        train.reset2Motors();
        // train.OdometryReset(0, 0);
        train.resetGyro();
        // train.setAxisSpeed(30, 0);
        train.finish = false;
        // train.setAxisSpeed(20, 0);

        // return train.getLimitSwitch() && Timer.getFPGATimestamp() - StateMachine.startTime > 1;
        return true;
    }
}
