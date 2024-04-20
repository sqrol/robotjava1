package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class SetGlidePos implements IState {

    private int pos = 0;
    private Training train = RobotContainer.train; 
    
    public SetGlidePos(int pos) {
        this.pos = pos;
    }
    @Override
    public boolean execute() {
        train.setGlideServoValue(pos);
        return Timer.getFPGATimestamp() - StateMachine.startTime > 3;
    }
    
}
