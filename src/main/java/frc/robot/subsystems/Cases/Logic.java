package frc.robot.subsystems.Cases;

import frc.robot.RobotContainer;
import frc.robot.Logic.Change;
import frc.robot.subsystems.IState;

public class Logic implements IState {

    private static Change change = RobotContainer.change;

    @Override
    public boolean execute() {
        RobotContainer.logic.init();
        change.changeElements();
        RobotContainer.train.setAxisSpeed(0.0f, 0.0f);
        return true;
    }
}