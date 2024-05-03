package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotContainer;

public class EMSThread implements Runnable {

    Training train = RobotContainer.train;

    @Override
    public void run() {
        while(true) {
            try {
                if(train.getEMSButton()) {

                    train.setRightMotorSpeed(0.0, true);
                    train.setLeftMotorSpeed(0.0, true);
                    train.setGlideMotorSpeed(0.0, true);
                    train.setLiftMotorSpeed(0.0, true);

                    train.getGrip().setDisabled();
                    train.getGripRotate().setDisabled();
                    train.getMainRotate().setDisabled();
                }
            } catch (Exception e) {
                e.printStackTrace();
                DriverStation.reportError("EMS THREAD ERROR ", true);
            }
        }

    }
    
}
