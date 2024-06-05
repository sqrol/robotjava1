package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class SetGripRotatePos implements IState {

    private String pos;
    private Training train = RobotContainer.train;
    public SetGripRotatePos(String position) {
        this.pos = position;
    }
    @Override
    public boolean execute() {
        
        switch(pos) {
            case "FLOOR":
                train.setGripRotateServoValue(93); // 279
                break;
            case "BRANCH":
                train.setGripRotateServoValue(13);
                break;
            case "ANGLE":
                train.setGripRotateServoValue(70);
                break;
            case "FOR DROP":
                train.setGripRotateServoValue(13);
                break;
            case "SMALL ANGLE":
                train.setGripRotateServoValue(52);
                break;
            
        }
        RobotContainer.train.setAxisSpeed(0, 0);

        return Timer.getFPGATimestamp() - StateMachine.iterationTime > 0.6;
    }
    
}
