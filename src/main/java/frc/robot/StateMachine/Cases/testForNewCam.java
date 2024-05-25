package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.Logic.LogicMain;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class testForNewCam implements IState {

    private Training train = RobotContainer.train; 

    public testForNewCam() {
        
    }


    @Override
    public boolean execute() {
        
        RobotContainer.train.nowTask = 2; 
        train.setGripServoValue(130);
        
        return false;
    }


}
