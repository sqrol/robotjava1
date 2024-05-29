package frc.robot.StateMachine;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Training;

public class StateMachine extends CommandBase {
    
    private static final Training train = RobotContainer.train;
    public static int currentArray = 0;
    public static int currentIndex = 0;
    public static double startTime, iterationTime = 0;
    public static boolean first = false;

    public StateMachine(){
        addRequirements(train);
    }

    @Override
    public void initialize(){
    }

    @Override
    public void execute(){
        update();
    }

    @Override
    public void end(boolean interrupted){
    }

    static void update(){
        first = false;
        
        if(States.mainStates[currentArray][currentIndex].execute()){
            startTime = Timer.getFPGATimestamp();
            first = true;
            currentIndex++;
            SmartDashboard.putNumber("Index", currentIndex);
            SmartDashboard.putNumber("Array", currentArray);
            SmartDashboard.putBoolean("First", first); 
            iterationTime = Timer.getFPGATimestamp() - startTime;
        }
        SmartDashboard.putString("StateName", States.mainStates[currentArray][currentIndex].getClass().getSimpleName());
    }

    public static void Change(final int array, final int index){
        currentArray = array;
        currentIndex = index;
    }
}
