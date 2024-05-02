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
    private boolean liftStop = false;  

    public SetLiftPosition(int pos) {
        this.pos = pos;
    }

    @Override
    public boolean execute() {

        // if (!train.getLimitSwitchLift()) {
        //     train.initLift = true;
        // } else {
        //     train.initLift = false;
        // }

        liftStop = train.liftToMovePos(pos);
        SmartDashboard.putBoolean("liftStop",  liftStop);
        return false;
        // return liftStop && Timer.getFPGATimestamp() - StateMachine.startTime > 1.5;
    }
}
