package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.Logic.TreeTraverse;
import frc.robot.StateMachine.IState;

public class InitTraverse implements IState{
    private TreeTraverse traverse = RobotContainer.traverse;
    @Override
    public boolean execute() {
        TreeTraverse.execute();
        return true;
    }
    
}
