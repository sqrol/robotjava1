package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;
import frc.robot.subsystems.Training;

public class SonicCheck implements IState {

    Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        if(train.getBackSonicDistance() < 20) {
            train.setGreenLED(true);
            train.setRedLED(false);
        } else {
            train.setGreenLED(false);
            train.setRedLED(false);
        }
        return false;
    }
    
}
