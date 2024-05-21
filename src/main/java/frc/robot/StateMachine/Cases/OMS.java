package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class OMS implements IState {

    private String pos;

    private boolean liftPosReached = false;

    private double liftFloorPos = 66;

    Training train = RobotContainer.train;

    public OMS(String pos) {
        this.pos = pos;
    }
    @Override
    public boolean execute() {
        switch(pos) {
            case "FLOOR OPEN":
                train.setGripRotateServoValue(120);
                train.setGripServoValue(50.0);
                return true;

            case "FLOOR BIG APPLE ZONE 1":
                int servoAngle = 0;
                train.setGripServoValue(50.0);
                train.setGripRotateServoValue(120);
                liftPosReached = train.liftToMovePos(liftFloorPos);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 3 && liftPosReached) {
                    train.setGripServoValue(27.0);
                    liftPosReached = false;
                    return true;
                }
                break;

            case "FLOOR SMALL APPLE":
                train.setGripServoValue(50.0);
                train.setGripRotateServoValue(120);
                // liftPosReached = train.liftToMovePos(liftFloorPos);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 3 && true) {
                    train.setGripServoValue(0.0);
                    liftPosReached = false;
                    return true;
                }
                break;
            
            case "FLOOR PEAR":
                train.setGripServoValue(50.0);
                train.setGripRotateServoValue(120);
                liftPosReached = train.liftToMovePos(liftFloorPos);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 3 && liftPosReached) {
                    train.setGripServoValue(25.0);
                    liftPosReached = false;
                    return true;
                }
                break;
            
            case "BRANCH OPEN":
                train.setGripRotateServoValue(42);
                train.setGripServoValue(70.0);
                break;

            case "BRANCH BIG APPLE":
                train.setGripServoValue(70.0);
                train.setGripRotateServoValue(42);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 1.5) {
                    train.setGripServoValue(19.0);
                    return true;
                }
                break;

            case "BRANCH SMALL APPLE":
                train.setGripServoValue(70.0);
                train.setGripRotateServoValue(42);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 1.5) {
                    train.setGripServoValue(0.0);
                    return true;
                }
                break;

            case "BRANCH PEAR":
                train.setGripServoValue(70.0);
                train.setGripRotateServoValue(42);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 1.5) {
                    train.setGripServoValue(25.0);
                    return true;
                }
                break;
        }
        return false;
    }
    
}
