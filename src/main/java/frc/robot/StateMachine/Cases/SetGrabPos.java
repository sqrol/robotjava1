package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.RobotContainer.*;
import frc.robot.StateMachine.*;
import frc.robot.MachineVision.JavaCam;


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
                RobotContainer.train.setGripServoValue(160);
                break;
            case "SMALL APPLE":
                RobotContainer.train.setGripServoValue(177);
                break;
            case "PEAR":
                RobotContainer.train.setGripServoValue(164);
                break;
            case "OPEN":
                RobotContainer.train.setGripServoValue(140);
                break;
        }
        return System.currentTimeMillis() - StateMachine.iterationTime > 2000;
    }

    @Override
    public boolean execute() {
        RobotContainer.train.setAxisSpeed(0, 0);
        return grab(fruit);
    }
}