package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class StateMachine {
    public static int currentArray = 0;
    public static int currentIndex = 0;
    public static float startTime = 0;
    public static boolean first = false;

    static void update(){
        if(States.mainStates[currentArray][currentIndex].execute()){
            startTime = (float) Timer.getFPGATimestamp();
            first = true;
            currentIndex++;
            SmartDashboard.putNumber("Index", currentIndex);
            SmartDashboard.putNumber("Array", currentArray);
        }
        SmartDashboard.putString("StateName", States.mainStates[currentArray][currentIndex].getClass().getSimpleName());
    }

    public static void Change(int array, int index){
        currentArray = array;
        currentIndex = index;
    }
}
