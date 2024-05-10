package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;

public class StateMachine {
    public static int currentArray = 0;
    public static int currentIndex = 0;
    public static double startTime = 0;
    public static boolean first = false;

    static void update(){
        first = false;
        if(States.mainStates[currentArray][currentIndex].execute()){
            startTime = Timer.getFPGATimestamp();
            first = true;
            currentIndex++;
            SmartDashboard.putNumber("Index", currentIndex);
            SmartDashboard.putNumber("Array", currentArray);
            SmartDashboard.putBoolean("First", first); 
        }
        SmartDashboard.putString("StateName", States.mainStates[currentArray][currentIndex].getClass().getSimpleName());
    }

    public static void Change(int array, int index){
        currentArray = array;
        currentIndex = index;
    }
}
