package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;

public class Sensors implements IState{
    private String sensors;
    private float X, Y, Z, speedX, speedY, speedZ, valueX, degSharp, sharpZ, sonicY, sonicZ = 0;
    private float lastDeg;
    private boolean flagSharp = true, flagSonic = true, exit = false;

    private static float[][] degFunction = {{0.1f, 0.3f, 0.7f, 1.9f, 3.2f, 4f, 5.3f, 7.5f, 8.5f, 20}, {0.2f, 1.4f, 6f, 13f, 16f, 18f, 19f, 20f, 23f, 25f}};
    private static float[][] sharpXFunc = {{0f, 0.9f, 1.7f, 4, 9, 26, 87, 120, 180, 250, 570 },{ 0f, 4f, 10.5f, 17, 24, 30, 41, 56, 63, 71, 95 }}; 
    private static float[][] sonicYFunc = { { 0f, 0.5f, 4.5f, 21, 31, 47, 76, 93, 150}, { 0f, 6.1f, 15.5f, 25, 38, 47, 60, 86, 90} };


    public Sensors(String sensors, float X, float Y, float Z){
        this.sensors = sensors;
        this.X = X;
        this.Y = Y;
        this.Z = Z;
    }

    @Override
    public boolean execute(){
        switch(sensors){
            case "sharp":
                exit = levelingIK(X);
            break;
            case "sonic":
                exit = treveledSonic(Y);
            break;
        }
        return exit;
    }

    private boolean levelingIK(float X){
        if(RobotContainer.train.getRightSharpDistance() > 20 && RobotContainer.train.getLeftSharpDistance() > 20){
            if(flagSharp){
                lastDeg = (float) RobotContainer.train.getYaw();
                flagSharp = false;
            }
            valueX = X - RobotContainer.train.getLeftSharpDistance();
            // speedX = Function.TransitionFunction(valueX, sharpXFunc);
            speedX = -40;
            sharpZ = lastDeg - (float)RobotContainer.train.getYaw();
            speedZ = Function.TransitionFunction(sharpZ, degFunction);
        }else{
            valueX = X - RobotContainer.train.getLeftSharpDistance();
            degSharp = RobotContainer.train.getRightSharpDistance() - RobotContainer.train.getLeftSharpDistance();
            speedZ = Function.TransitionFunction(degSharp, degFunction);
            speedX = Function.TransitionFunction(valueX, sharpXFunc);
        }

        RobotContainer.train.setAxisSpeed(speedX, 0, speedZ);

        boolean stopX = Function.BooleanInRange(valueX, -0.5f, 0.5f);
        boolean stopZ = Function.BooleanInRange(degSharp, -0.5f, 0.5f);

        SmartDashboard.putBoolean("stopX-sharp", stopX);
        SmartDashboard.putBoolean("stopZ-sharp", stopZ);

        if(stopX && stopZ && ((float)(Timer.getFPGATimestamp() - StateMachine.startTime) > 8)){
            RobotContainer.train.setAxisSpeed(0, 0, 0);
            StateMachine.startTime = (float) Timer.getFPGATimestamp();
            lastDeg = 0;
            return true;
        }else{
            return false;
        }
    }

    private boolean treveledSonic(float Y){
        if(flagSonic){
            lastDeg = (float) RobotContainer.train.getYaw();
            flagSonic = false;
        }

        if(Y > 0){
            sonicY = Y - RobotContainer.train.getRightSonicDistance();
            speedY = Function.TransitionFunction(-sonicY, sonicYFunc);
        }else if(Y < 0){
            sonicY = RobotContainer.train.getLeftSonicDistance() + Y;
            speedY = Function.TransitionFunction(sonicY, sonicYFunc);
        }

        sonicZ = lastDeg - (float)RobotContainer.train.getYaw();
        speedZ = Function.TransitionFunction(sonicZ, degFunction);

        RobotContainer.train.setAxisSpeed(0, speedY, speedZ);

        boolean stopY = Function.BooleanInRange(sonicY, -0.5f, 0.5f);
        boolean stopZ = Function.BooleanInRange(sonicZ, -0.5f, 0.5f);

        SmartDashboard.putBoolean("stopY-sonic", stopY);
        SmartDashboard.putBoolean("stopZ-sonic", stopZ);
        SmartDashboard.putNumber("sonicY", sonicY);

        if(stopY && stopZ && (float)(Timer.getFPGATimestamp() - StateMachine.startTime) > 0.5){
            RobotContainer.train.setAxisSpeed(0, 0, 0);
            StateMachine.startTime = (float)Timer.getFPGATimestamp();
            return true;
        }else{
            return false;
        }
    }
}
