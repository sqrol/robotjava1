package frc.robot.StateMachine.Cases;

import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class Transition2 implements IState {
    
    private int indexArray = 0;
    private String findFruitName = "";  

    public Transition2() {
        
    }

    @Override
    public boolean execute() {

        StateMachine.currentArray = Training.indexList.get(this.indexArray);
        this.indexArray++;
        StateMachine.currentIndex = -1;
        RobotContainer.train.setAxisSpeed(0, 0);

        return true;
    }
}
