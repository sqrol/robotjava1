package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;
import frc.robot.StateMachine.StateMachine;
import frc.robot.subsystems.Training;

public class RottenDetection implements IState {
    private Training train = RobotContainer.train;
    
    @Override
    public boolean execute() {

        train.nowTask = 4; 
        SmartDashboard.putNumber("nowResult", train.nowResult);

        train.setGripRotateServoValue(95);

        if(train.nowResult == 5) {
            train.setAxisSpeed(0, 0);
            train.setIndication("IN PROCESS");
            SmartDashboard.putString("Identification", "Rotten fruit");
            if(train.getStartButton() && Timer.getFPGATimestamp() - StateMachine.iterationTime > 2) {
                return true;
            }
        } else {
            SmartDashboard.putString("Identification", "none");
            train.setAxisSpeed(0, 0);
            train.setIndication("WAITING");
            train.nowResult = 0;
            
        }
        return false;
    }
    
}
