package frc.robot.subsystems.Cases;

import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;

public class StartPos implements IState{
    @Override
    public boolean execute() {
        RobotContainer.train.OdometryReset(0, 0);
        RobotContainer.train.resetGyro();
        RobotContainer.train.finish = false;
        return true;
    }
}
