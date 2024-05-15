package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;

import frc.robot.Subsystems.Training;

public class SetGlidePosition implements IState {

    private int pos;
    private Training train = RobotContainer.train; 

    public SetGlidePosition(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean execute() {
        train.servoGlidePosition(this.pos);
        return train.glideExit && Timer.getFPGATimestamp() - StateMachine.startTime > 1;
    }
}
