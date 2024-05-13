package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;

public class SetGripRotatePos implements IState {

    private String pos;

    public SetGripRotatePos(String position) {
        this.pos = position;
    }
    @Override
    public boolean execute() {
        switch(pos) {
            case "FLOOR":
                RobotContainer.train.setGripRotateServoValue(0);
                break;
            case "BRANCH":
                RobotContainer.train.setGripRotateServoValue(42);
        }


        return Timer.getFPGATimestamp() - StateMachine.startTime > 1;
    }
    
}
