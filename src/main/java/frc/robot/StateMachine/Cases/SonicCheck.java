package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.IState;
import frc.robot.subsystems.Training;

public class SonicCheck implements IState {

    Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        
        if(train.getBackSonicDistance() < 20 || train.getSideSonicDistance() < 20) {
            SmartDashboard.putBoolean("SONIC CHECK", true);
            train.setIndication("IN PROCESS");
            if(train.getStartButton()) {
                return true;
            }
        } else {
            train.setIndication("WAITING");
            SmartDashboard.putBoolean("SONIC CHECK", false);
            
            // train.setIndication("WAITING");
        }
        return false;
    }
    
}
