package frc.robot.StateMachine.Cases;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.Logic.LogicMain;
import frc.robot.Logic.TreeTraverse;
import frc.robot.StateMachine.*;
import frc.robot.subsystems.Training;

public class Transition implements IState {
    private static int indexArray = 0; 
    private TreeTraverse auto = RobotContainer.traverse;

    // private static int index = 0;
    // public static Change change = RobotContainer.change;
    public Transition() {
        
    }

    // @Override
    public boolean execute() {
        // StateMachine.currentArray = indexArray;
        // StateMachine.currentIndex = -1;
        SmartDashboard.putString("TreeTraverse: ", auto.execute());
        StateMachine.currentArray = Training.indexList.get(Transition.indexArray);
        Transition.indexArray++;
        StateMachine.currentIndex = -1;
        RobotContainer.train.setAxisSpeed(0, 0);
        return true;
    }
}
