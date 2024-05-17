package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;

import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class SetRotatePos implements IState {

    private double angle;
    private Training train = RobotContainer.train;

    public SetRotatePos(double angle) {
        this.angle = angle;
        RobotContainer.train.setAxisSpeed(0, 0);
    }

    @Override
    public boolean execute() {
        return train.rotateToPos(angle) && Timer.getFPGATimestamp() - StateMachine.startTime > 5;
    }
    
}
