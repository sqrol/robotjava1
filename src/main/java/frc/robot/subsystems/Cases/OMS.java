package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
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
                train.setGripServoValue(50);
                return true;

            case "FLOOR BIG APPLE ZONE 1":
                int servoAngle = 0;
                boolean mainRotatePosReached = Function.BooleanInRange(servoAngle - train.getMainRotate().getAngle(), -0.1, 0.1);
                train.setMainRotateServoValue(servoAngle);
                if(mainRotatePosReached) {
                    train.setGripServoValue(50);
                    train.setGripRotateServoValue(120);
                }
                liftPosReached = train.liftToMovePos(liftFloorPos);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 3 && liftPosReached) {
                    train.setGripServoValue(27);
                    liftPosReached = false;
                    return true;
                }
                break;

            case "FLOOR SMALL APPLE":
                train.setGripServoValue(50);
                train.setGripRotateServoValue(120);
                liftPosReached = train.liftToMovePos(liftFloorPos);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 3 && liftPosReached) {
                    train.setGripServoValue(0);
                    liftPosReached = false;
                    return true;
                }
                break;
            
            case "FLOOR PEAR":
                train.setGripServoValue(50);
                train.setGripRotateServoValue(120);
                liftPosReached = train.liftToMovePos(liftFloorPos);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 3 && liftPosReached) {
                    train.setGripServoValue(25);
                    liftPosReached = false;
                    return true;
                }
                break;
            
            case "BRANCH OPEN":
                train.setGripRotateServoValue(42);
                train.setGripServoValue(70);
                break;

            case "BRANCH BIG APPLE":
                train.setGripServoValue(70);
                train.setGripRotateServoValue(42);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 1.5) {
                    train.setGripServoValue(19);
                    return true;
                }
                break;

            case "BRANCH SMALL APPLE":
                train.setGripServoValue(70);
                train.setGripRotateServoValue(42);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 1.5) {
                    train.setGripServoValue(0);
                    return true;
                }
                break;

            case "BRANCH PEAR":
                train.setGripServoValue(70);
                train.setGripRotateServoValue(42);
                if(Timer.getFPGATimestamp() - StateMachine.startTime > 1.5) {
                    train.setGripServoValue(25);
                    return true;
                }
                break;
        }
        return false;
    }
    
}
