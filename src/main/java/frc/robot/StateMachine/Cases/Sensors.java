package frc.robot.subsystems.Cases;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.functions.Function;
import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;

public class Sensors implements IState{
    private String sensors;
    private double X, Y, Z, speedX, speedY, speedZ, valueX, degSharp, sharpZ, sonicY, sonicZ = 0;
    private double lastDeg;
    private boolean flagSharp = true, flagSonic = true, exit = false;

    private static double[][] degFunction = {{0.1, 0.3, 0.7, 1.9, 3.2, 4, 5.3, 7.5, 8.5, 20}, {0.2, 1.4, 6, 13, 16, 18, 19, 20, 23, 25}};
    private static double[][] sharpXFunc = {{0, 0.9, 1.7, 4, 9, 26, 87, 120, 180, 250, 570 },{ 0, 4, 10.5, 17, 24, 30, 41, 56, 63, 71, 95 }}; 
    private static double[][] sonicYFunc = { { 0, 0.5, 4.5, 21, 31, 47, 76, 93, 150}, { 0, 6.1, 15.5, 25, 38, 47, 60, 86, 90} };


    public Sensors(String sensors, double X, double Y, double Z){
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

    private boolean levelingIK(double X){
        if(RobotContainer.train.getRightSharpDistance() > 20 && RobotContainer.train.getLeftSharpDistance() > 20){
            if(flagSharp){
                lastDeg = RobotContainer.train.getYaw();
                flagSharp = false;
            }
            valueX = X - RobotContainer.train.getLeftSharpDistance();
            // speedX = Function.TransitionFunction(valueX, sharpXFunc);
            speedX = -40;
            sharpZ = lastDeg - RobotContainer.train.getYaw();
            speedZ = Function.TransitionFunction(sharpZ, degFunction);
        }else{
            valueX = X - RobotContainer.train.getLeftSharpDistance();
            degSharp = RobotContainer.train.getRightSharpDistance() - RobotContainer.train.getLeftSharpDistance();
            speedZ = Function.TransitionFunction(degSharp, degFunction);
            speedX = Function.TransitionFunction(valueX, sharpXFunc);
        }

        RobotContainer.train.setAxisSpeed((float)speedX, (float)speedZ);

        boolean stopX = Function.BooleanInRange(valueX, -0.5f, 0.5f);
        boolean stopZ = Function.BooleanInRange(degSharp, -0.5f, 0.5f);

        SmartDashboard.putBoolean("stopX-sharp", stopX);
        SmartDashboard.putBoolean("stopZ-sharp", stopZ);

        if(stopX && stopZ && ((float)(Timer.getFPGATimestamp() - StateMachine.startTime) > 8)){
            RobotContainer.train.setAxisSpeed((float)0, (float)0);
            StateMachine.startTime = (float) Timer.getFPGATimestamp();
            lastDeg = 0;
            return true;
        }else{
            return false;
        }
    }

    private boolean treveledSonic(double Y){
        if(flagSonic){
            lastDeg = RobotContainer.train.getYaw();
            flagSonic = false;
        }

        if(Y > 0){
            sonicY = Y - RobotContainer.train.getSideSonicDistance();
            speedY = Function.TransitionFunction(-sonicY, sonicYFunc);
        }else if(Y < 0){
            sonicY = RobotContainer.train.getBackSonicDistance() + Y;
            speedY = Function.TransitionFunction(sonicY, sonicYFunc);
        }

        sonicZ = lastDeg - (float)RobotContainer.train.getYaw();
        speedZ = Function.TransitionFunction(sonicZ, degFunction);

        RobotContainer.train.setAxisSpeed((float)0, (float)speedZ);

        boolean stopY = Function.BooleanInRange(sonicY, -0.5f, 0.5f);
        boolean stopZ = Function.BooleanInRange(sonicZ, -0.5f, 0.5f);

        SmartDashboard.putBoolean("stopY-sonic", stopY);
        SmartDashboard.putBoolean("stopZ-sonic", stopZ);
        SmartDashboard.putNumber("sonicY", sonicY);

        if(stopY && stopZ && (float)(Timer.getFPGATimestamp() - StateMachine.startTime) > 0.5){
            RobotContainer.train.setAxisSpeed((float)0, (float)0);
            StateMachine.startTime = (float)Timer.getFPGATimestamp();
            return true;
        }else{
            return false;
        }
    }
}
