package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.RobotContainer.*;
import frc.robot.functions.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.JavaCam;

public class SetGrabPos implements IState {
    private String fruit;

    public SetGrabPos(String fruit) {
        this.fruit = fruit;
    }

    // private boolean operate(int value) {
    //     switch(servo) {
    //         case "mainRotate":
    //             RobotContainer.train.setMainRotateServoValue(value);
    //             break;
    //         case "glide":
    //             RobotContainer.train.setGlideServoValue(value);
    //             break;
    //         case "grip":
    //             RobotContainer.train.setGripServoValue(value);
    //             break;
    //         case "gripRotate":
    //             RobotContainer.train.setGripRotateServoValue(value);
    //             break;
    //     }
    //     return Timer.getFPGATimestamp() - StateMachine.startTime > 2;
    // }

    private boolean grab(String fruit) {
        switch(fruit) {
            case "BIG APPLE":
                RobotContainer.train.setGripServoValue(19);
                break;
            case "SMALL APPLE":
                RobotContainer.train.setGripServoValue(0);
                break;
            case "PEAR":
                RobotContainer.train.setGripServoValue(25);
                break;
            case "OPEN":
                RobotContainer.train.setGripServoValue(70);
                break;
        }
        return Timer.getFPGATimestamp() - StateMachine.startTime > 0.6;
    }

    @Override
    public boolean execute() {
        return grab(fruit);
    }
}