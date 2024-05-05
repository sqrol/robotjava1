package frc.robot.subsystems.Cases;

import frc.robot.RobotContainer;

import frc.robot.subsystems.IState;
import frc.robot.subsystems.StateMachine;

public class Transition implements IState {
    private int indexArray = 0; 

    // private static int index = 0;
    // public static Change change = RobotContainer.change;
    public Transition(int arrayIndex) {
        this.indexArray = arrayIndex;
    }

    // @Override
    public boolean execute() {
        StateMachine.currentArray = indexArray;
        StateMachine.currentIndex = -1;
    //     RobotContainer.train.setAxisSpeed(0.0f, 0.0f);
    //     StateMachine.currentArray = change.indexList.get(Transition.index);
    //     Transition.index++;
    //     StateMachine.currentIndex = -1;
        return true;
    }
}
