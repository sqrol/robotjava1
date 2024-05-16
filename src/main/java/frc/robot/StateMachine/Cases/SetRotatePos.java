package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;

import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class SetRotatePos implements IState {

    private double angle;
    private Training train = RobotContainer.train;

    public SetRotatePos(double angle) {
        this.angle = angle;
    }

    @Override
    public boolean execute() {
        
        return train.rotateToPos(angle);
    }
    
}
