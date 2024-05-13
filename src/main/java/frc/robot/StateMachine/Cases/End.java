package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;

public class End implements IState{
    @Override
    public boolean execute() {
        RobotContainer.train.setAxisSpeed(0.0, 0.0);
        RobotContainer.train.finish = true;
        return false;
    }
}
