package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class OMS implements IState {

    private String pos;

    private boolean liftPosReached = false;

    private double liftFloorPos = 66;

    Training train = RobotContainer.train;

    public OMS(String pos) {
        this.pos = pos;
    }
    @Override
    public boolean execute() {
        
        switch(pos) {
            case "check":
                train.setGripServoValue(49);
                break;
        }
        return false;
    }
    
}
