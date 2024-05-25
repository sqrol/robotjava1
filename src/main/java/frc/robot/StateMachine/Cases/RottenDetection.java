package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;
import frc.robot.subsystems.Training;

public class RottenDetection implements IState {
    private Training train = RobotContainer.train;
    @Override
    public boolean execute() {
        train.nowTask = 1; 
        SmartDashboard.putNumber("nowResult", RobotContainer.train.nowResult);
        train.setGripRotateServoValue(260);
        if(train.nowResult == 5) {
            train.setAxisSpeed(0, 0);
            train.setIndication("IN PROCESS");
        } else {
            train.setAxisSpeed(0, 0);
            train.setIndication("WAITING");
            train.nowResult = 0;
        }
        return false;
    }
    
}
