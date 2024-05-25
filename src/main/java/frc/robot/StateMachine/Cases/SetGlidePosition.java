package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;

import frc.robot.subsystems.Training;

public class SetGlidePosition implements IState {

    private int pos;
    private Training train = RobotContainer.train; 

    public SetGlidePosition(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean execute() {
        
        train.servoGlidePosition(this.pos);
        train.setAxisSpeed(0, 0);
        return train.glideExit && System.currentTimeMillis() - StateMachine.startTime > 5000;
    }
}
