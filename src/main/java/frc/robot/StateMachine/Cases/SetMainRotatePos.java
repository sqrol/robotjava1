package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;

public class SetMainRotatePos implements IState{

    private int pos = 0;
    public SetMainRotatePos(int pos) {
        this.pos = pos;
    }
    @Override
    public boolean execute() {
        RobotContainer.train.setMainRotateServoValue(pos);
        return Timer.getFPGATimestamp() - StateMachine.startTime > 1.2;
    }
    
}
