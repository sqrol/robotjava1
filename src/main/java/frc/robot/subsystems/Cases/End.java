package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;

public class End implements IState{
    @Override
    public boolean execute() {
        RobotContainer.train.setAxisSpeed(0.0f, 0.0f);
        RobotContainer.train.finish = true;
        return false;
    }
}
