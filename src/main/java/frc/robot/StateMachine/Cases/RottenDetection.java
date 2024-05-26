package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;
import frc.robot.subsystems.Training;

public class RottenDetection implements IState {
    private Training train = RobotContainer.train;
    @Override
    public boolean execute() {
        train.nowTask = 228; 
        SmartDashboard.putNumber("nowResult", train.nowResult);
        // train.setGripRotateServoValue(260);
        if(train.nowResult == 5) {
            train.setAxisSpeed(0, 0);
            train.setIndication("IN PROCESS");
            if(train.getStartButton()) {
                return true;
            }
        } else {
            train.setAxisSpeed(0, 0);
            train.setIndication("WAITING");
            train.nowResult = 0;
        }
        return false;
    }
    
}
