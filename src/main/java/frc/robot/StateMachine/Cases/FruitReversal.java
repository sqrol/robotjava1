package frc.robot.StateMachine.Cases;

import org.opencv.core.Point;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class FruitReversal implements IState{

    Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        RobotContainer.train.nowTask = 2; 

        for (Point center : train.centersForClass) {
            SmartDashboard.putNumber("CenterX: ", center.x);
            SmartDashboard.putNumber("CenterY: ", center.y);
        }
        
        return false;
    }

    private void RotateSpeed() {
        
    }
    
}
