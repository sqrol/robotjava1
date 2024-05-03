package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DriverStation;
import frc.robot.RobotContainer;

public class InitThread implements Runnable {

    Training train = RobotContainer.train;

    private boolean firstInitForGlide = false; 
    private boolean firstInitForLift = false; 

    private boolean firstInitForGlideDone = false;
    private boolean firstInitForLiftDone = false;

    private boolean glideReachedPos = false;
    private boolean successInit = false;

    @Override
    public void run() {
        while(true) {
            try {
                if (firstInitForGlide && !firstInitForGlideDone) {
                    firstInitForGlideDone = train.initForGlide();
                    if(firstInitForGlideDone) {
                        train.setGripRotateServoValue(32);
                        train.setGripServoValue(15);
                        train.setMainRotateServoValue(225);
                    }
                    } else {
                        if (firstInitForGlideDone) {
                            if (firstInitForLift && !firstInitForLiftDone) {
                                firstInitForLiftDone = train.initForLift();
                            } else {
                                if(firstInitForGlideDone && firstInitForLiftDone) {
                                    glideReachedPos = true; //glideToMovePos(50)
                                    if(glideReachedPos) {
                                        successInit = true;
                                    }
                                }  
                            }   
                        }
                    }
                Thread.sleep(5);
            } 
            catch (Exception e) {
                e.printStackTrace();
                DriverStation.reportError("INITIALIZATION THREAD ERROR", true);
            }
        }
    } 
}
