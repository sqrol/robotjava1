package frc.robot.StateMachine;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Training;

public class Drive extends CommandBase {
    private static final Training train = RobotContainer.train;

    public Drive(){
        addRequirements(train);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        // train.travelXYZ();
        StateMachine.update();
        
    }

    @Override
    public void end(boolean interrupted){
        train.setRightMotorSpeed(0.0, true);
        train.setLeftMotorSpeed(0.0, true);

    }

    @Override
    public boolean isFinished(){
        return false;
    }
}
