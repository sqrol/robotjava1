package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Main;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.Training;
import frc.robot.StateMachine.*;
import frc.robot.MachineVision.JavaCam;

public class ObjectFinder implements IState {

    private Training train = RobotContainer.train;

    @Override
    public boolean execute() {
        train.setAxisSpeed(0, 0);
        train.nowTask = 1; 
        SmartDashboard.putNumber("nowResult", RobotContainer.train.nowResult);
        train.setGripRotateServoValue(269);
        
        // if(train.nowResult == 1) {
        //     train.setAxisSpeed(0, 0);
        //     train.setIndication("IN PROCESS");
        //     if(train.getStartButton()) {
        //         return true;
        //     }

        // } else if(train.nowResult == 2){ 
        //     train.setAxisSpeed(0, 0);
        //     train.setIndication("IN PROCESS");
        //     if(train.getStartButton()) {
        //         return true;
        //     }

        // } else if(train.nowResult == 3){
        //     train.setAxisSpeed(0, 0);
        //     train.setIndication("IN PROCESS");
        //     if(train.getStartButton()) {
        //         return true;
        //     }

        // } else if(train.nowResult == 4){
        //     train.setAxisSpeed(0, 0);
        //     train.setIndication("IN PROCESS");
        //     if(train.getStartButton()) {
        //         return true;
        //     }

        // } else if(train.nowResult == 5) {
        //     train.setAxisSpeed(0, 0);  
        //     train.setIndication("IN PROCESS");
        //     if(train.getStartButton()) {
        //         return true;
        //     }

        // } else if(train.nowResult == 6) {
        //     train.setIndication("IN PROCESS");
        //     train.setAxisSpeed(0, 0);
        //     if(train.getStartButton()) {
        //         return true;
        //     }

        // } else if(train.nowResult == 7) {
        //     train.setIndication("IN PROCESS");
        //     train.setAxisSpeed(0, 0);
        //     if(train.getStartButton()) {
        //         return true;
        //     }

        // } else {
        //     train.setIndication("WAITING");
            
        //     train.nowResult = 0;
        // }

        return false;
    }
}