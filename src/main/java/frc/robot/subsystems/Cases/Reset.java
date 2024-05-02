package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.RobotContainer;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;

public class Reset implements IState{
    private String resetName;
    private float X, Y, Z;

    public Reset(String resetName, float X, float Y, float Z){
        this.resetName = resetName;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    @Override
    public boolean execute(){
        switch (this.resetName){
            case "Gyro":
            RobotContainer.train.setAxisSpeed(0.0f, 0.0f);
            RobotContainer.train.resetGyro();
            break;

            default:
            this.X = 0;
            this.Y = 0;
            this.Z = 0;

            RobotContainer.train.resetGyro();
            RobotContainer.train.OdometryReset(this.X, this.Y);
            break;
        }
        return Timer.getFPGATimestamp() - StateMachine.startTime > 0.25f;
    }
}
