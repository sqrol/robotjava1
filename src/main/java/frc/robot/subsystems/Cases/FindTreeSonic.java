package frc.robot.subsystems.Cases;

import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.Training;

public class FindTreeSonic implements IState{

    Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        
        return false;
    }
    
    private boolean StopOnTree() {
        
        double lastSonicDist = train.getRightSonicDistance();
        // double rightSonicDist = train.getRightSonicDistance();
        
        return false;
    }
}
