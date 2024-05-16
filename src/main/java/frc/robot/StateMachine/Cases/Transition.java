package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.Logic.LogicMain;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class Transition implements IState {
    private static int indexArray = 0; 

    // private static int index = 0;
    // public static Change change = RobotContainer.change;
    public Transition() {
        
    }

    // @Override
    public boolean execute() {
        // StateMachine.currentArray = indexArray;
        // StateMachine.currentIndex = -1;
        StateMachine.currentArray = Training.indexList.get(Transition.indexArray);
        Transition.indexArray++;
        StateMachine.currentIndex = -1;
        return true;
    }
}
