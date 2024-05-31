package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;
import frc.robot.subsystems.Training;

public class CheckGrippedFruit implements IState{

    private Training train = RobotContainer.train;
    @Override
    public boolean execute() {
        train.nowTask = 3;
        train.setGripServoValue(177);
        
        return false;
    }
    
}
