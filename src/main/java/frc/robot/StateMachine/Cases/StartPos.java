package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class StartPos implements IState{
    private Training train = RobotContainer.train; 
    private boolean firstCall = true; 

    public StartPos() {
        this.firstCall = true;
    }


    @Override
    public boolean execute() {
        train.resetEncLeft();
        train.resetEncRight();
        train.setIndication("WAITING");
        train.reset2Motors();
        
        // train.OdometryReset(0, 0);
        train.setAxisSpeed(0, 0);
        
        train.resetGyro();

        // train.setAxisSpeed(0.0f, 30.0f);

        if (train.getLimitSwitchLift()) {
            train.liftMotorSpeedThread = 0;
            train.resetLiftEncoder();
        } else {
            train.liftMotorSpeedThread = -50;
        }

        SmartDashboard.putNumber("iter: ", Timer.getFPGATimestamp() - StateMachine.iterationTime);
        
        if(train.successInit) {
            train.resetLiftEncoder();
            train.resetEncRotate();
            
            train.setGripServoValue(15); 
            train.setGripRotateServoValue(93); 
            if(Timer.getFPGATimestamp() - StateMachine.iterationTime > 1){
                train.setIndication("IN PROCESS");
                return true;
            } else {
                return false;
            }
        }
        return false;
    }
}
