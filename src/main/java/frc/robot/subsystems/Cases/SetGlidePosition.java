package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class SetGlidePosition implements IState {

    private int pos;
    private Training train = RobotContainer.train; 

    public SetGlidePosition(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean execute() {
        // return train.glideStop || Timer.getFPGATimestamp() - StateMachine.startTime > 5;
        return train.glideToMovePos(pos);
    }
}
