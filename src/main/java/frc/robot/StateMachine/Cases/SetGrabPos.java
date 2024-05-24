package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.Training;
import frc.robot.RobotContainer.*;
import frc.robot.StateMachine.*;
import frc.robot.MachineVision.JavaCam;


public class SetGrabPos implements IState {
    private Training train = RobotContainer.train; 
    private String fruit;
    private boolean endMovement = false;
    private boolean smooth;

    private double lastUpdateTime = 0;

    private static final double STEP = 1.0; // Increment step for each iteration

    public SetGrabPos(String fruit, boolean smooth) {
        this.fruit = fruit;
        this.smooth = smooth;
        this.lastUpdateTime = Timer.getFPGATimestamp();
    }

    // private boolean operate(int value) {
    // switch(servo) {
    // case "mainRotate":
    // RobotContainer.train.setMainRotateServoValue(value);
    // break;
    // case "glide":
    // RobotContainer.train.setGlideServoValue(value);
    // break;
    // case "grip":
    // RobotContainer.train.setGripServoValue(value);
    // break;
    // case "gripRotate":
    // RobotContainer.train.setGripRotateServoValue(value);
    // break;
    // }
    // return Timer.getFPGATimestamp() - StateMachine.startTime > 2;
    // }

    // private boolean grab(String fruit) {
    // switch(fruit) {
    // case "BIG APPLE":
    // // train.setGripServoValue(160.0);

    // endMovement = smoothServoMovement(160.0);
    // break;
    // case "SMALL APPLE":
    // // train.setGripServoValue(177.0);
    // endMovement = smoothServoMovement(177.0);
    // break;
    // case "PEAR":
    // // train.setGripServoValue(164.0);
    // endMovement = smoothServoMovement(164.0);
    // break;
    // case "OPEN":
    // // train.setGripServoValue(144.0);
    // endMovement = smoothServoMovement(144.0);
    // break;
    // }
    // return System.currentTimeMillis() - StateMachine.iterationTime > 1000 &&
    // endMovement;
    // }

    private boolean grab(String fruit) {

        if (fruit.equals("BIG APPLE")) {
            if (smooth) {
                endMovement = smoothServoMovement(165.0, 0.05);
            } else {
                endMovement = smoothServoMovement(160.0, 0.01);
            }
        }
        if (fruit.equals("SMALL APPLE")) {
            if (smooth) {
                endMovement = smoothServoMovement(177.0, 0.05);
            } else {
                endMovement = smoothServoMovement(177.0, 0.01);
            }
        }
        if (fruit.equals("PEAR")) {
            if (smooth) {
                endMovement = smoothServoMovement(164.0, 0.05);
            } else {
                endMovement = smoothServoMovement(164.0, 0.01);
            }
        }
        if (fruit.equals("OPEN")) {
            if (smooth) {
                endMovement = smoothServoMovement(144.0, 0.05);
            } else {
                endMovement = smoothServoMovement(144.0, 0.01);
            }
        }

        return System.currentTimeMillis() - StateMachine.iterationTime > 1000 && endMovement;
    }

    private boolean smoothServoMovement(double targetPosition, double DELAY) {
        double currentPosition = train.getGripServoValue();
        double step = STEP * (targetPosition > currentPosition ? 1 : -1);

        double currentTime = Timer.getFPGATimestamp();

        if (Math.abs(targetPosition - currentPosition) > Math.abs(step)) {
            if (currentTime - lastUpdateTime >= DELAY) {
                currentPosition += step;
                train.setGripServoValue(currentPosition); // Устанавливаем новое положение серво
                lastUpdateTime = currentTime; // Обновляем время последнего обновления
            }
        }     

        return Function.BooleanInRange(Math.abs(targetPosition - currentPosition), -1, 1); 
    }

    @Override
    public boolean execute() {
        RobotContainer.train.setAxisSpeed(0, 0);
        return grab(fruit);
    }
}