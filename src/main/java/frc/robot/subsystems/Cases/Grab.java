package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;

public class Grab implements IState {
    
    private int pos = 0;
    public Grab(int pos) {
            this.pos = pos;
    }
    @Override
    public boolean execute() {
        RobotContainer.train.setMainRotateServoValue(pos);
        return Timer.getFPGATimestamp() - StateMachine.startTime > 1.5;
    }
    
}
