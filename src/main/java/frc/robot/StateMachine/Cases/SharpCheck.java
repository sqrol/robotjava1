package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;
import frc.robot.subsystems.Training;

public class SharpCheck implements IState {

    Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        
        if(train.getLeftSharpDistance() < 10 || train.getRightSharpDistance() < 10) {
            
            train.setIndication("IN PROCESS");
            SmartDashboard.putBoolean("SHARP CHECK", true);
            if(train.getStartButton()) {
                return true;
            }
        } else {
            SmartDashboard.putBoolean("SHARP CHECK", false);
            
            
            train.setIndication("WAITING");
        }
        
        return false;
    }
    
}
