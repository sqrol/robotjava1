package frc.robot.Logic;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;

public class Change {
    
    public ArrayList<Integer> indexList = new ArrayList<>();
    public LogicInit log = RobotContainer.logic;

    public void changeElements() {
        String tasks = "";

        System.out.println(" ");
        System.out.println("========================");
        System.out.println("======= ACTIONS ========");
        for (int i = 0; i < LogicInit.actionElements.size(); i++) {
            System.out.println(LogicInit.actionElements.get(i));
            tasks += LogicInit.actionElements.get(i) + "\n";
        }
        System.out.println(" ");
        System.out.println("========================");
        System.out.println("========================");
        System.out.println(" ");
    

        try {
            // BufferedWriter writer = new BufferedWriter(new FileWriter("taskLog.txt"));
            // System.out.println("!!!!!!!!!!!!!!! Writing started !!!!!!!!!!!!!!!");
            // writer.write(tasks);
            // System.out.println("!!!!!!!!!!!!!!! Writing completed !!!!!!!!!!!!!!!");
            // writer.close();
            // System.out.println("!!!!!!!!!!!!!!! Writer closed !!!!!!!!!!!!!!!");
        } catch (Exception e) {
            e.printStackTrace();
        }

        for(String action : LogicInit.actionElements) {
            SmartDashboard.putString("Action", action);
            switch(action) {
                case "Labyrinth": 
                    indexList.add(1);
                    break;

                case "GreenPear":
                    indexList.add(2);
                    break;

                case "YellowPear":
                    indexList.add(3);
                    break;

            }
        }
    }
}