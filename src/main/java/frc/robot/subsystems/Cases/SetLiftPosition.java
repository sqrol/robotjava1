package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;
import frc.robot.subsystems.Training;

public class SetLiftPosition implements IState {

    private int pos;
    private Training train = RobotContainer.train; 

    public SetLiftPosition(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean execute() {

        if (!train.getLimitSwitchLift()) {
            train.initLift = true;
        } else {
            train.initLift = false;
        }

        // train.setLiftPositions(pos);
        SmartDashboard.putBoolean("liftStop", train.liftStop);
        return train.liftStop;
    }
}
