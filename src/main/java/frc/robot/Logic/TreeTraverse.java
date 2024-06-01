package frc.robot.Logic;

import java.util.HashMap;

public class TreeTraverse {

    // Данные и конфигурации
    private static final String[] TREE_ZONE_NAMES = {"RZ", "TZ", "LZ"};
    private static final String[] TREE_NAMES = {"SECOND"};

    private static final HashMap<String, String> containersForFruits = new HashMap<String, String>() {
        {
            put("RottenSmallApple", "CON1");
            put("RottenBigApple", "CON1");
            put("RottenPeer", "CON1");
            put("AppleSmallRipe", "CON2");
            put("AppleBigRipe", "CON5");
            put("PeerRipe", "CON4");
        }
    };

    private static final HashMap<String, String> grabTypeForZones = new HashMap<String, String>() {
        {
            put("LZ", "AUTO_GRAB_UPPER");
            put("RZ", "AUTO_GRAB_UPPER");
            put("LOZ", "AUTO_GRAB_UPPER");
            put("UZL", "AUTO_GRAB_UPPER");
            put("UZR", "AUTO_GRAB_UPPER");
            put("TZ", "AUTO_GRAB_UPPER");
        }
    };

    // Вот эти переменные необходимо изменить при нахождении фрукта AutoGrab'ом
    private static String findFruitName = "PeerRipe";
    private static boolean fruitFind = true; // Для отладки установлено в true

    private static boolean stopLoop = true;

    private static boolean firstCall = true;
    private static boolean lastAutoGrab, lastStepChange, firstItter, treeNumberChange = true;
    private static int currentTreeNumber, currentTreeZoneNumber, currentTreeZoneSteps, deliverySteps, endSteps, lastCurrentTreeNumber, lastCurrentTreeZoneNumber = 0;
    private static String lastCheckpoint = "";
    private static String treeZone = "";

    public static void execute() {
        String outIndex = "none";

        if (firstCall) {
            outIndex = "MOV_IN_START_TO_" + choosingBestZoneForCheck(TREE_ZONE_NAMES[currentTreeZoneNumber], TREE_NAMES[currentTreeNumber]);
            setLastCheckpoint(choosingBestZoneForCheck(TREE_ZONE_NAMES[currentTreeZoneNumber], TREE_NAMES[currentTreeNumber]));
            firstItter = true;
            firstCall = false;
        } else {
            if (currentTreeNumber < TREE_NAMES.length) {
                if (currentTreeZoneNumber < TREE_ZONE_NAMES.length) {
                    if (currentTreeZoneSteps < 2) {

                        outIndex = processCurrentTreeZone(currentTreeNumber, currentTreeZoneNumber, currentTreeZoneSteps, treeNumberChange);
                        currentTreeZoneSteps++;

                        if (currentTreeZoneSteps + 1 == 3) {
                            currentTreeZoneSteps = 0;
                            currentTreeZoneNumber++;
                        }
                    }
                    if (currentTreeZoneNumber + 1 == TREE_ZONE_NAMES.length + 1) {
                        currentTreeZoneNumber = 0;
                        currentTreeNumber++;
                    }
                }
            } else {
                if (endSteps < 2) {
                    outIndex = processEnd(endSteps);
                    endSteps++;
                } else {
                    stopLoop = false;
                }
            }
        }

        treeNumberChange = checkChangeTreeNumber();
        lastCurrentTreeNumber = currentTreeNumber;

        System.out.println(outIndex);
    }

    private static String processEnd(Integer step) {
        String outIndex = "none";
        if (step == 0) {
            outIndex = "MOV_IN_" + getLastTreeZone() + "_TO_" + getBestWayForCheckForZone(getLastTreeZone());
            setLastCheckpoint(getBestWayForCheckForZone(getLastTreeZone()));
        }
        if (step == 1) {
            outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + "FINISH";
        }

        return outIndex;
    }

    private static String processCurrentTreeZone(Integer currentTreeNumber, Integer currentTreeZoneNumber, Integer currentTreeZoneSteps, Boolean ChangeTreeNumber) {
        String outIndex = "none";

            if (firstItter) {

                outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber];
                setLastTreeZone(TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber]);
                TreeTraverse.currentTreeZoneSteps = 0;
                firstItter = false;

            } else {
                if (fruitFind && lastAutoGrab) {

                    if (deliverySteps < 5) {

                        if (deliverySteps == 0) {
                            outIndex = "MOV_IN_" + getLastTreeZone() + "_TO_" + getBestWayForCheckForZone(getLastTreeZone());
                            setLastCheckpoint(getBestWayForCheckForZone(getLastTreeZone()));

                            TreeTraverse.currentTreeZoneNumber =lastCurrentTreeZoneNumber;
                            TreeTraverse.currentTreeZoneSteps = -1;
                        }

                        if (deliverySteps == 1) {
                            outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + getConForFruit(findFruitName);

                            TreeTraverse.currentTreeZoneNumber =lastCurrentTreeZoneNumber;
                            TreeTraverse.currentTreeZoneSteps = -1;
                        }

                        if (deliverySteps == 2) {
                            outIndex = "RESET_FRUIT";

                            TreeTraverse.currentTreeZoneNumber =lastCurrentTreeZoneNumber;
                            TreeTraverse.currentTreeZoneSteps = -1;
                        }

                        if (deliverySteps == 3) {
                            outIndex = "MOV_IN_" + getConForFruit(findFruitName) + "_TO_" + getLastCheckpoint();

                            TreeTraverse.currentTreeZoneNumber =lastCurrentTreeZoneNumber;
                            TreeTraverse.currentTreeZoneSteps = -1;
                        }

                        if (deliverySteps == 4) {
                            outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + getLastTreeZone();

                            TreeTraverse.currentTreeZoneNumber =lastCurrentTreeZoneNumber;
                            TreeTraverse.currentTreeZoneSteps = 0;
                        }

                        deliverySteps++;

                        if (deliverySteps == 5) {

                            fruitFind = false;
                            lastAutoGrab = false;
                        }
                    }

                } else {
                    if (ChangeTreeNumber) {
                        outIndex = "MOV_IN_" + getLastTreeZone() + "_TO_" + getBestWayForCheckForZone(getLastTreeZone());
                        setLastCheckpoint(getBestWayForCheckForZone(getLastTreeZone()));
                        lastStepChange = true;
                        TreeTraverse.currentTreeZoneSteps = -1;
                    } else {
                        outIndex = createPathForZone(currentTreeNumber, currentTreeZoneNumber, currentTreeZoneSteps);
                    }
                }
            }

        return outIndex;
    }

    private static String createPathForZone(Integer currentTreeNumber, Integer currentTreeZoneNumber, Integer stepForZone) {
        String outIndex = "none";

        if (currentTreeZoneSteps == 0) {
            if (lastStepChange) {
                outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber];
                lastStepChange = false;
            } else {
                outIndex = "MOV_IN_" + getLastTreeZone() + "_TO_" + TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber];
                setLastTreeZone(TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber]);
                lastAutoGrab = false;
            }
        }

        if (currentTreeZoneSteps == 1) {
            outIndex = getGrabModeInArray(TREE_ZONE_NAMES[currentTreeZoneNumber]);
            setLastTreeZone(TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber]);
            lastCurrentTreeZoneNumber = TreeTraverse.currentTreeZoneNumber;
            deliverySteps = 0;
            lastAutoGrab = true;
        }

        return outIndex;

    }

    /**
     * Узнаем в какой контейнер нужно вести найденный фрукт
     */
    private static String getConForFruit(String fruitName) {
        return containersForFruits.getOrDefault(fruitName, "none");
    }

    private static String getBestWayForCheckForZone(String getLastTreeZone) {
        String[] replaceZone = getLastTreeZone.split("_");
        return choosingBestZoneForCheck(replaceZone[1], replaceZone[0]);
    }

    private static boolean checkChangeTreeNumber() {
        return currentTreeNumber != lastCurrentTreeNumber;
    }

    /**
     * Узнаем какой тип распознавания нужно использовать для заданной зоны
     */
    private static String getGrabModeInArray(String zoneName) {
        return grabTypeForZones.getOrDefault(zoneName, "none");
    }

    /**
     * Установка последней Checkpoint
     */
    private static void setLastCheckpoint(String lastCheckpoint) {
        TreeTraverse.lastCheckpoint = lastCheckpoint;
    }

    /**
     * Установка последней зоны в который мы были у дерева
     */
    private static void setLastTreeZone(String treeZone) {
        TreeTraverse.treeZone = treeZone;
    }

    /**
     * Получение последней зоны в который мы были у дерева
     */
    private static String getLastTreeZone() {
        return TreeTraverse.treeZone;
    }

    /**
     * Получение последней точки робота
     */
    private static String getLastCheckpoint() {
        return lastCheckpoint;
    }

    private static String choosingBestZoneForCheck(String currentZoneName, String zoneName) {
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

}