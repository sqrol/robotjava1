package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.Subsystems.Training;

public class FindTreeSonic implements IState{

    Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        
        return false;
    }
    
    private boolean StopOnTree() {
        
        double lastSonicDist = train.getSideSonicDistance();
        // double rightSonicDist = train.getRightSonicDistance();
        
        return false;
    }
}
