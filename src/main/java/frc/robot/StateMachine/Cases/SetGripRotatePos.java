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
                train.setGripRotateServoValue(279);
                break;
            case "BRANCH":
                train.setGripRotateServoValue(190);
                break;
            case "ANGLE":
                train.setGripRotateServoValue(246);
                break;
            case "FOR DROP":
                train.setGripRotateServoValue(190);
                break;
        }
        RobotContainer.train.setAxisSpeed(0, 0);

        return System.currentTimeMillis() - StateMachine.iterationTime > 2000;
    }
    
}
