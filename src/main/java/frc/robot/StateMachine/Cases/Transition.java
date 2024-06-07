package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.Logic.ArrayForPath;
import frc.robot.Logic.LogicMain;
import frc.robot.Logic.TreeTraverse;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class Transition implements IState {
    private static int indexArray = 0; 
    private Boolean autoMode = true; // true для автономки, false для полуавтономки
    private TreeTraverse auto = RobotContainer.traverse;
    private static int countStep = 0; 

    // private static int index = 0;
    // public static Change change = RobotContainer.change;
    public Transition() {
        
    }

    // @Override
    public boolean execute() {
        if (autoMode) {
                    // StateMachine.currentArray = indexArray;
        // StateMachine.currentIndex = -1;

        String currentStrOut = auto.execute(); 
        Integer currentArray = ArrayForPath.getArrayForPath(currentStrOut);
        
        SmartDashboard.putString("TreeTraverse: ", currentStrOut);
        SmartDashboard.putNumber("TreeArray: ", ArrayForPath.getArrayForPath(currentStrOut));

        // StateMachine.currentArray = Training.indexList.get(Transition.indexArray);
        StateMachine.currentArray = currentArray;
        Transition.indexArray++;
        StateMachine.currentIndex = -1;
        RobotContainer.train.setAxisSpeed(0, 0);

        countStep++;
        SmartDashboard.putNumber("CountStepForTrans: ", countStep);

        return true;

        } else {
            StateMachine.currentArray = Training.indexList.get(Transition.indexArray);
            Transition.indexArray++;
            StateMachine.currentIndex = -1;
            RobotContainer.train.setAxisSpeed(0, 0);
            return true;
        }

    }
}
