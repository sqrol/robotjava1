package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.JavaCam;

public class ObjectFinder implements IState {

    @Override
    public boolean execute() {
        if(RobotContainer.train.checkAppleResult == 1) {
            RobotContainer.train.setAxisSpeed(50f, 0f);
        } else if(RobotContainer.train.checkAppleResult == 2){ 
            RobotContainer.train.setAxisSpeed(0f, 0f);
        } else if(RobotContainer.train.checkAppleResult == 3){
            RobotContainer.train.setAxisSpeed(0f, 0f);
        } else {
            RobotContainer.train.setAxisSpeed(0f, 0f);
        }
        return false;
    }
}