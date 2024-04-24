package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class SetLiftPosition implements IState {

    private int pos;
    private Training train = RobotContainer.train; 

    public SetLiftPosition(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean execute() {
        train.setLiftPositions(pos);
        return train.liftStop || Timer.getFPGATimestamp() - StateMachine.startTime > 10; 
        // return false;
        // return Timer.getFPGATimestamp() - StateMachine.startTime > 2;
    }
}
