package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;
import frc.robot.subsystems.Training;

public class SharpCheck implements IState {

    Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        if(train.getLeftSharpDistance() < 10 || train.getRightSharpDistance() < 10) {
            train.setGreenLED(true);
            train.setRedLED(false);
        } else {
            train.setGreenLED(false);
            train.setRedLED(false);
        }
        
        return false;
    }
    
}
