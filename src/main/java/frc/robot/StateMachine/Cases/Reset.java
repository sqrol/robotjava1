package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class Reset implements IState{
    private Training train = RobotContainer.train;

    public Reset(){
        
    }

    @Override
    public boolean execute(){
        
        train.resetEncLeft();
        train.resetEncRight();
        
        train.setAxisSpeed(0, 0);

        return Timer.getFPGATimestamp() - StateMachine.iterationTime > 3;
    }
}
