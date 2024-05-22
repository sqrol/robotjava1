package frc.robot.StateMachine.Cases;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;

public class Grab implements IState {
    
    private double pos = 0;
    public Grab(int pos) {
            this.pos = pos;
    }
    
    @Override
    public boolean execute() {
        RobotContainer.train.setGripServoValue(pos);
        return Timer.getFPGATimestamp() - StateMachine.startTime > 1.5;
    }
    
}
