package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;

public class End implements IState{
    @Override
    public boolean execute() {
        RobotContainer.train.setIndication("FINISHED");
        RobotContainer.train.setAxisSpeed(0.0, 0.0);
        RobotContainer.train.getGlide().setDisabled();
        RobotContainer.train.finish = true;
        return false;
    }
}
