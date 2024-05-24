package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Main;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.StateMachine.*;
import frc.robot.MachineVision.JavaCam;

public class ObjectFinder implements IState {

    @Override
    public boolean execute() {
        RobotContainer.train.nowTask = 1; 
        SmartDashboard.putNumber("nowResult", RobotContainer.train.nowResult);
        if(RobotContainer.train.nowResult == 1) {
            RobotContainer.train.setAxisSpeed(0f, 0f);
        } else if(RobotContainer.train.nowResult == 2){ 
            RobotContainer.train.setAxisSpeed(0f, 0f);
        } else if(RobotContainer.train.nowResult == 3){
            RobotContainer.train.setAxisSpeed(0f, 0f);
        } else {
            RobotContainer.train.setAxisSpeed(0f, 0f);
        }
        return false;
    }
}