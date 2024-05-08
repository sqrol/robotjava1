package frc.robot.Logic;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class Main {
    private static LogicCore logic;

    public static void main(String[] args) {
        logic = new LogicCore();
        logic.logicInit();

        for (String elem : logic.arrayWithLogic) {
            System.out.println(elem);
            
        }

    }
}