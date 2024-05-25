package frc.robot.Logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LogicCore {

    // GRAB_POS_(ROT-DEGREE, GLIDEPOS, SUBGRABPOS)
    // Пример: GRAB_POS_0_0_LOWER

    // ROT-DEGREE = 0, 45, -45
    // GLIDEPOS = 0, 1, 2
    // SUBGRABPOS = LOWER, MIDDLE, UPPER

    private boolean fristCall = true;
    private boolean fristCallForInit = true;

    // Выходной массив с командами
    public final ArrayList<String> arrayWithLogic = new ArrayList<String>();

    private String lastCheckpoint = "";

    private String lastCurrentZoneArea = "";

    private boolean fristCallForSubPath = false;

    // Для сдачи модулей B отключает построение пути назад 

    private boolean autonomusMode = false;

    // Зона 1
    private static final String[] firstTree = { "null", "null", "null"};
    private static final String[][] firstTreeZone =
            {
                    //  1  | 2  |                      | 3  |  4
                    { "1", "2", "null", "null", "null", "3", "4" },
                    //   5  |   6   |   7   |   8   |   9   |   10  |   11
                    { "5", "6", "7", "8", "9", "10", "11" },
                    //  12  |   13   |   14   |   15   |   16   |   17  |   18
                    { "12", "13", "14", "15", "16", "17", "18" },
                    //  19  |   20   |   21   |   22   |   23   |   24  |   25
                    { "19", "20", "21", "22", "23", "24", "25" } };

    // Зона 2
    private static final String[] secondTree = { "null", "null", "null"};
    private static final String[][] secondTreeZone =
            {
                    //  1  | 2  |                      | 3  |  4
                    { "1", "2", "null", "null", "null", "3", "1" },
                    //   5  |   6   |   7   |   8   |   9   |   10  |   11
                    {   "5", "6", "7", "8", "9", "10", "11" },
                    //  12  |   13   |   14   |   15   |   16   |   17  |   18
                    { "12", "13", "14", "15", "16", "17", "18" },
                    //  19  |   20   |   21   |   22   |   23   |   24  |   25
                    { "19", "20", "21", "22", "23", "24", "25" } };

    // Зона 3
    private static final String[] thitdTree = { "null", "null", "null"};
    private static final String[][] thitdTreeZone =
            {
                    //  1  | 2  |                      | 3  |  4
                    { "1", "2", "null", "null", "null", "3", "4" },
                    //   5  |   6   |   7   |   8   |   9   |   10  |   11
                    { "RottenPeer", "6", "7", "8", "9", "10", "11" },
                    //  12  |   13   |   14   |   15   |   16   |   17  |   18
                    { "1", "13", "14", "15", "16", "17", "18" },
                    //  19  |   20   |   21   |   22   |   23   |   24  |   25
                    { "19", "20", "21", "22", "23", "24", "25" } };


    // Шаблон зоны с номерами
    private static final String[][] zoneWithNumber = {{ "1", "2", "null", "null", "null", "3", "4" }, { "5", "6", "7", "8", "9", "10", "11" },
            { "12", "13", "14", "15", "16", "17", "18" }, { "19", "20", "21", "22", "23", "24", "25" } };

    // Назначаем фрукты, которые возим
    private static final String[] allFullFruitsName = { "AppleBigRipe", "AppleSmallRipe", "PeerRipe", "RottenBigApple", "RottenSmallApple", "RottenPeer"};

    // Назначаем контейнеры для определенного типа фруктов
    HashMap<String, String> containersForFruits = new HashMap<String, String>() {
        {
            put("RottenSmallApple", "CON1");
            put("RottenBigApple", "CON1");
            put("RottenPeer", "CON1");
            put("AppleSmallRipe", "CON2");
            put("AppleBigRipe", "CON3");
            put("PeerRipe", "CON4");
        }
    };

    public void logicInit() {
        if (fristCallForInit) {
            for (int zoneNum = 1; zoneNum <= 3; zoneNum++) {
                arrayWithLogic.addAll(getFruitsForExport(zoneNum));
            }
            if (!arrayWithLogic.isEmpty() && autonomusMode) {
                arrayWithLogic.add("MOVE_IN_" + getLastCheckpoint() + "_TO_FINISH");
            } else {
                arrayWithLogic.add("END");
            }
            fristCallForInit = false;
        }
    }

    /**
     * Получение текущего массива зон для дерева
     */
    private String[][] getZoneArray(Integer zoneNum) {
        if (zoneNum == 1) {
            return firstTreeZone;
        }
        if (zoneNum == 2) {
            return secondTreeZone;
        }
        if (zoneNum == 3) {
            return thitdTreeZone;
        }
        return new String[0][0];
    }

    /**
     * Основная функция где мы формируем пути ко всем зонам
     */
    private ArrayList<String> getFruitsForExport(Integer zoneNum)
    {
        ArrayList<String> outArray = new ArrayList<String>();
        String[][] currentZone = getZoneArray(zoneNum);
        String[] currentTree = getTreeArray(zoneNum);
        String zoneName = getZoneName(zoneNum);

        outArray.addAll(grabFromLowerZone(currentZone, zoneName));
        outArray.addAll(grabFromLeftZone(currentZone, zoneName));
        outArray.addAll(grabFromRightZone(currentZone, zoneName));
        outArray.addAll(grabFromTreeZone(currentTree, zoneName));
        outArray.addAll(grabFromUpperZoneLeft(currentZone, zoneName));
        outArray.addAll(grabFromUpperZoneRight(currentZone, zoneName));

        return outArray;
    }

    // Пример: GRAB_POS_0_0_LOWER
    // GRAB_POS_(GLIDEPOS, ROT-DEGREE, SUBGRABPOS)
    private String grabPosGenerate(String inGrabPos) {
        Map<String, String> grabPosMap = new HashMap<>();

        // For UZL
        grabPosMap.put("GRAB_POS_1", "GRAB_POS_0_-45_LOWER_UZ");
        grabPosMap.put("GRAB_POS_2", "GRAB_POS_0_20_LOWER");

        // For UZR
        grabPosMap.put("GRAB_POS_3", "GRAB_POS_0_-45_LOWER_3");
        grabPosMap.put("GRAB_POS_4", "GRAB_POS_0_0_LOWER");

        // For LOZ 
        grabPosMap.put("GRAB_POS_7", "GRAB_POS_2_-45_MIDDLE");
        grabPosMap.put("GRAB_POS_8", "UNDER_TREE_8");
        grabPosMap.put("GRAB_POS_9", "GRAB_POS_2_45_LOWER");
        grabPosMap.put("GRAB_POS_14", "GRAB_POS_1_-45_LOWER_14");
        grabPosMap.put("GRAB_POS_15", "UNDER_TREE_15");
        grabPosMap.put("GRAB_POS_16", "GRAB_POS_1_45_LOWER");
        grabPosMap.put("GRAB_POS_21", "GRAB_POS_0_-45_LOWER");
        grabPosMap.put("GRAB_POS_22", "GRAB_POS_0_0_LOWER");
        grabPosMap.put("GRAB_POS_23", "GRAB_POS_0_45_LOWER");

        // For RZ
        grabPosMap.put("GRAB_POS_25", "GRAB_POS_0_45_LOWER");
        grabPosMap.put("GRAB_POS_24", "GRAB_POS_0_0_LOWER");
        grabPosMap.put("GRAB_POS_18", "GRAB_POS_1_45_LOWER");
        grabPosMap.put("GRAB_POS_17", "GRAB_POS_1_0_LOWER");
        grabPosMap.put("GRAB_POS_11", "GRAB_POS_2_45_MIDDLE");
        grabPosMap.put("GRAB_POS_10", "GRAB_POS_2_0_MIDDLE");

        // For LZ
        grabPosMap.put("GRAB_POS_20", "GRAB_POS_0_0_LOWER");
        grabPosMap.put("GRAB_POS_19", "GRAB_POS_0_-45_LOWER");
        grabPosMap.put("GRAB_POS_13", "GRAB_POS_1_0_LOWER");
        grabPosMap.put("GRAB_POS_12", "GRAB_POS_1_-45_LOWER");
        grabPosMap.put("GRAB_POS_6", "GRAB_POS_2_0_MIDDLE");
        grabPosMap.put("GRAB_POS_5", "GRAB_POS_2_-45_MIDDLE");

        return grabPosMap.getOrDefault(inGrabPos, "none");
    }

    /**
     * Захват фруктов с дерева
     */
    private ArrayList<String> grabFromTreeZone(String[] currentTree, String zoneName) {
        ArrayList<String> allFindFruits = new ArrayList<String>();
        String currentZoneName = "TZ";

        for (int i = 0; i < currentTree.length; i++) { // Собираем все фрукты из дерева
            String elemInArray = currentTree[i];
            if (weNeedThisFruit(elemInArray)) { // Смотрим фрукт на ветке тот который нам нужен
                allFindFruits.add(getPosForTree(i) + "/" + elemInArray);
            }
        }

        return subPathForDelivery(allFindFruits, currentZoneName, zoneName);
    }

    private String getPosForTree(int numIndex) {
        if (numIndex == 0) {
            return "GRAB_POS_DOWN";
        }
        if (numIndex == 1) {
            return "GRAB_POS_MID";
        }
        if (numIndex == 2) {
            return "GRAB_POS_UP";
        }
        return "null";
    }

    /**
     * Захват фруктов с позиций 1, 2 для указанного дерева
     */
    private ArrayList<String> grabFromUpperZoneLeft(String[][] currentZone, String zoneName) {
        ArrayList<String> allFindFruits = new ArrayList<String>();
        String currentZoneName = "UZL";
        int[][] indexes = { {0, 0}, {0, 1} }; // Тут указываем индексы для 1, 2

        // Проход по каждому индексу в массиве и вывод соответствующего значения
        for (int i = 0; i < indexes.length; i++) { // Собираем все фрукты в зоне если они есть
            String elemInArray = currentZone[indexes[i][0]][indexes[i][1]];
            if (weNeedThisFruit(elemInArray)) { // Смотрим фрукт в зоне тот который нам нужен
                allFindFruits.add(grabPosGenerate("GRAB_POS_"+ zoneWithNumber[indexes[i][0]][indexes[i][1]]) + "/" + elemInArray);
            }
        }
        return subPathForDelivery(allFindFruits, currentZoneName, zoneName);
    }

    /**
     * Захват фруктов с позиций 3, 4 для указанного дерева
     */
    private ArrayList<String> grabFromUpperZoneRight(String[][] currentZone, String zoneName) {
        ArrayList<String> allFindFruits = new ArrayList<String>();
        String currentZoneName = "UZR";
        int[][] indexes = { {0, 5}, {0, 6} }; // Тут указываем индексы для 3, 4

        // Проход по каждому индексу в массиве и вывод соответствующего значения
        for (int i = 0; i < indexes.length; i++) { // Собираем все фрукты в зоне если они есть
            String elemInArray = currentZone[indexes[i][0]][indexes[i][1]];
            if (weNeedThisFruit(elemInArray)) { // Смотрим фрукт в зоне тот который нам нужен
                allFindFruits.add(grabPosGenerate("GRAB_POS_"+ zoneWithNumber[indexes[i][0]][indexes[i][1]]) + "/" + elemInArray);
            }
        }
        return subPathForDelivery(allFindFruits, currentZoneName, zoneName);
    }

    /**
     * Захват фруктов с позиций 19, 20, 12, 13, 5, 6 для указанного дерева
     */
    private ArrayList<String> grabFromLeftZone(String[][] currentZone, String zoneName) {
        ArrayList<String> allFindFruits = new ArrayList<String>();
        String currentZoneName = "LZ";
        int[][] indexes = { {3, 0}, {3, 1}, {2, 0}, {2, 1}, {1, 0}, {1, 1} }; // Тут указываем индексы для 19, 20, 12, 13, 5, 6

        // Проход по каждому индексу в массиве и вывод соответствующего значения
        for (int i = 0; i < indexes.length; i++) { // Собираем все фрукты в зоне если они есть
            String elemInArray = currentZone[indexes[i][0]][indexes[i][1]];
            if (weNeedThisFruit(elemInArray)) { // Смотрим фрукт в зоне тот который нам нужен
                allFindFruits.add(grabPosGenerate("GRAB_POS_"+ zoneWithNumber[indexes[i][0]][indexes[i][1]]) + "/" + elemInArray);
            }
        }
        return subPathForDelivery(allFindFruits, currentZoneName, zoneName);
    }

    /**
     * Захват фруктов с позиций 24, 25, 17, 18, 10, 11 для указанного дерева
     */
    private ArrayList<String> grabFromRightZone(String[][] currentZone, String zoneName) {
        ArrayList<String> allFindFruits = new ArrayList<String>();
        String currentZoneName = "RZ";
        int[][] indexes = { {3, 5}, {3, 6}, {2, 5}, {2, 6}, {1, 5}, {1, 6} }; // Тут указываем индексы для 24, 25, 17, 18, 10, 11

        // Проход по каждому индексу в массиве и вывод соответствующего значения
        for (int i = 0; i < indexes.length; i++) { // Собираем все фрукты в зоне если они есть
            String elemInArray = currentZone[indexes[i][0]][indexes[i][1]];
            if (weNeedThisFruit(elemInArray)) { // Смотрим фрукт в зоне тот который нам нужен
                allFindFruits.add(grabPosGenerate("GRAB_POS_"+ zoneWithNumber[indexes[i][0]][indexes[i][1]]) + "/" + elemInArray);
            }
        }
        return subPathForDelivery(allFindFruits, currentZoneName, zoneName);
    }

    /**
     * Захват фруктов с позиций 21, 22, 23, 14, 15, 16, 7, 8, 9 для указанного дерева
     */
    private ArrayList<String> grabFromLowerZone(String[][] currentZone, String zoneName) {
        ArrayList<String> allFindFruits = new ArrayList<String>();
        String currentZoneName = "LOZ";
        int[][] indexes = { {3, 2}, {3, 3}, {3, 4}, {2, 2}, {2, 3}, {2, 4}, {1, 2}, {1, 3}, {1, 4}, }; // Тут указываем индексы для 21, 22, 23, 14, 15, 16, 7, 8, 9

        // Проход по каждому индексу в массиве и вывод соответствующего значения
        for (int i = 0; i < indexes.length; i++) { // Собираем все фрукты в зоне если они есть
            String elemInArray = currentZone[indexes[i][0]][indexes[i][1]];
            if (weNeedThisFruit(elemInArray)) { // Смотрим фрукт в зоне тот который нам нужен
                allFindFruits.add(grabPosGenerate("GRAB_POS_"+ zoneWithNumber[indexes[i][0]][indexes[i][1]]) + "/" + elemInArray);
            }
        }
        return subPathForDelivery(allFindFruits, currentZoneName, zoneName);
    }

    /**
     * Собираем путь из зоны до контейнера
     */
    private ArrayList<String> subPathForDelivery(ArrayList<String> allFindFruits, String currentZoneName, String zoneName) {
        ArrayList<String> outSubPathForDelivery = new ArrayList<String>();
        if (!allFindFruits.isEmpty()) {
            String currentZoneArea = zoneName + "_" + currentZoneName;

            for (int i = 0; i < allFindFruits.size(); i++) {
                String currentGrabPos = allFindFruits.get(i).split("/")[0]; // Получение позиции GRAB_POS
                String currentFruit = allFindFruits.get(i).split("/")[1]; // Получение фрукта который в GRAB_POS
                String bestWayForCheck = choosingBestZoneForCheck(currentZoneName, zoneName);

                if (fristCall) { // Первый запуск перемещение с зоны страта в превую назначенную зону
                    outSubPathForDelivery.add("MOV_IN_START_TO_" + choosingBestZoneForCheck("START", zoneName));
                    outSubPathForDelivery.add("MOV_IN_" + choosingBestZoneForCheck("START", zoneName) +"_TO_" + currentZoneArea);
                    fristCall = false;
                }

                if (!currentZoneArea.equals(lastCurrentZoneArea)) {
                    if (fristCallForSubPath) {
                        outSubPathForDelivery.add("MOV_IN_" + getLastCheckpoint() + "_TO_" + currentZoneArea);
                    }
                }

                outSubPathForDelivery.add(currentGrabPos);
                outSubPathForDelivery.add("MOV_IN_" + currentZoneArea + "_TO_" + bestWayForCheck);
                outSubPathForDelivery.add("MOV_IN_" + bestWayForCheck + "_TO_" + containersForFruits.get(currentFruit));
                outSubPathForDelivery.add("RESET_FRUIT");

                if (CheckingLastElement(allFindFruits, i) && autonomusMode) { // Смотрим это последний фрукт для этой зоны или нет
                    outSubPathForDelivery.add("MOV_IN_" + containersForFruits.get(currentFruit) + "_TO_" + bestWayForCheck);
                    setLastCheckpoint(bestWayForCheck);
                }

            }
            fristCallForSubPath = true;
            lastCurrentZoneArea = currentZoneArea;
        }

        return outSubPathForDelivery;
    }

    /**
     * Выбираем где лучше выровниться для каждого из зон
     */
    private String choosingBestZoneForCheck(String currentZoneName, String zoneName) {
        String out = "";
        if (zoneName.equals("FRIST")) {
            switch (currentZoneName) {
                case "LZ":
                    out = "CH3";
                    break;
                case "RZ":
                    out = "CH3";
                    break;
                case "LOZ":
                    out = "CH3";
                    break;
                case "UZL":
                    out = "CH3";
                    break;
                case "UZR":
                    out = "CH3";
                    break;
                case "TZ":
                    out = "CH3";
                    break;
                case "START":
                    out = "CH3";
                    break;
                default:
                    out = "null";
                    break;
            }
        }
        if (zoneName.equals("SECOND")) {
            switch (currentZoneName) {
                case "LZ":
                    out = "CH2";
                    break;
                case "RZ":
                    out = "CH2";
                    break;
                case "LOZ":
                    out = "CH2";
                    break;
                case "UZL":
                    out = "CH2";
                    break;
                case "UZR":
                    out = "CH2";
                    break;
                case "TZ":
                    out = "CH2";
                    break;
                case "START":
                    out = "CH2";
                    break;
                default:
                    out = "null";
                    break;
            }
        }
        if (zoneName.equals("THIRD")) {
            switch (currentZoneName) {
                case "LZ":
                    out = "CH1";
                    break;
                case "RZ":
                    out = "CH1";
                    break;
                case "LOZ":
                    out = "CH1";
                    break;
                case "UZL":
                    out = "CH1";
                    break;
                case "UZR":
                    out = "CH1";
                    break;
                case "TZ":
                    out = "CH1";
                    break;
                case "START":
                    out = "CH1";
                    break;
                default:
                    out = "null";
                    break;
            }
        }
        return out;
    }

    /**
     * Проверяем является ли элемент последним в массиве
     */
    private boolean CheckingLastElement(ArrayList<String> allFindFruits, int currentIndex) {
        int lastIndex = allFindFruits.size() - 1;
        return currentIndex == lastIndex;
    }

    /**
     * Узнаем есть ли такой фрукт который на пришел через name
     */
    private boolean weNeedThisFruit(String name) {
        for (String elem : allFullFruitsName) {
            if (elem.equals(name)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Получение название зоны по номеру
     */
    private String getZoneName(Integer zoneNum) {
        return zoneNum == 1 ? "FRIST" : zoneNum == 2 ? "SECOND" : zoneNum== 3 ? "THIRD" : "none";
    }

    /**
     * Получение текущего массива элементов на дереве
     */
    private String[] getTreeArray(Integer zoneNum) {
        if (zoneNum == 1) {
            return firstTree;
        }
        if (zoneNum == 2) {
            return secondTree;
        }
        if (zoneNum == 3) {
            return thitdTree;
        }
        return new String[0];
    }

    /**
     * Получение последней точки робота
     */
    private String getLastCheckpoint() {
        return lastCheckpoint;
    }

    /**
     * Установка последней точки робота
     */
    private void setLastCheckpoint(String lastCheckpoint) {
        this.lastCheckpoint = lastCheckpoint;
    }
}