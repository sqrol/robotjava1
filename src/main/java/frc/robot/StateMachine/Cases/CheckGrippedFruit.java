package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;

public class CheckGrippedFruit implements IState{

    @Override
    public boolean execute() {
        RobotContainer.train.nowTask = 3;
        RobotContainer.train.setGripServoValue(177);
        return false;
    }
    
}
