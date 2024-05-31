package frc.robot.Logic;

import java.util.HashMap;

public class TreeTraverse {

    // Данные и конфигурации
    private static final String[] TREE_ZONE_NAMES = {"LZ", "RZ", "LOZ", "UZR", "TZ"};
    private static final String[] TREE_NAMES = {"THIRD", "SECOND", "FRIST"};

    private static final HashMap<String, String> containersForFruits = new HashMap<String, String>() {
        {
            put("RottenSmallApple", "CON1");
            put("RottenBigApple", "CON1");
            put("RottenPeer", "CON1");
            put("AppleSmallRipe", "CON2");
            put("AppleBigRipe", "CON3");
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

    private static boolean stopLoop = true;
    private static boolean firstCall = true;
    private static boolean fruitFind = false; // Для отладки установлено в true
    private static boolean treeNumberChange = false;
    private static boolean treeNumberChangeForPath = false;
    private static boolean lastDelivery = false;
    private static int currentTreeNumber, currentTreeZoneNumber, currentTreeZoneSteps,
            deliverySteps, endSteps, lastCurrentTreeNumber = 0;

    private static String lastCheckpoint = "";
    private static String treeZone = "";
    private static String findFruitName = "PeerRipe";

    public static void main(String[] args) {
        while (stopLoop) {
            execute();
        }
    }

    public static void execute() {
        String outIndex = "none";

        if (firstCall) { // Инициализация при первом запуске
            outIndex = initializeFirstCall();
            firstCall = false;
        } else {
            outIndex = traverseTree();
        }

        // Проверка на изменение номера дерева
        treeNumberChange = checkChangeTreeNumber();
        lastCurrentTreeNumber = currentTreeNumber;

        if (!outIndex.equals("none")) {
            System.out.println(outIndex);
//            System.out.println("fruitFind: " + fruitFind); // Добавлена отладочная информация
        }
    }

    /**
     * Инициализация первой команды при запуске.
     *
     * @return Возвращает строку команды для первой инициализации.
     */
    private static String initializeFirstCall() {
        String outIndex = "MOV_IN_START_TO_CH1";
        setLastCheckpoint("CH1");
        lastDelivery = false;
        return outIndex;
    }

    /**
     * Переход по дереву, обработка зон и деревьев.
     *
     * @return Возвращает строку команды в зависимости от текущего состояния.
     */
    private static String traverseTree() {
        String outIndex = "none";
        if (currentTreeNumber < TREE_NAMES.length) {
            if (currentTreeZoneNumber < TREE_ZONE_NAMES.length) {
                if (currentTreeZoneSteps < 2) {
                    outIndex = traverseZone();
                } else {
                    outIndex = deliverFruit();
                }
            } else {
                resetZone();
            }
        } else {
            outIndex = processEndTraversal();
        }
        return outIndex;
    }

    /**
     * Обработка перехода внутри текущей зоны.
     *
     * @return Возвращает строку команды для текущей зоны.
     */
    private static String traverseZone() {
        String outIndex;
        if (treeNumberChange) {
            outIndex = createPathInCurrentZone();
            treeNumberChange = false;
            treeNumberChangeForPath = true;
        } else {
            outIndex = createPathForZone(currentTreeNumber, currentTreeZoneNumber, currentTreeZoneSteps, treeNumberChangeForPath);
            treeNumberChangeForPath = false;
            fruitFind = true;
            currentTreeZoneSteps++;
        }
        return outIndex;
    }

    /**
     * Обработка доставки фрукта.
     *
     * @return Возвращает строку команды для доставки фрукта.
     */
    private static String deliverFruit() {
        String outIndex = "none";
        if (fruitFind) {
            outIndex = createPathForDelivery(currentTreeNumber, currentTreeZoneNumber, deliverySteps);
            deliverySteps++;
            if (deliverySteps >= 5) {
                deliverySteps = 0;
                fruitFind = false;
            }
        } else {
            currentTreeZoneSteps = 0;
            currentTreeZoneNumber++;
        }
        return outIndex;
    }

    /**
     * Сброс зоны для перехода к следующему дереву.
     */
    private static void resetZone() {
        currentTreeZoneNumber = 0;
        currentTreeNumber++;
    }

    /**
     * Обработка завершения обхода дерева.
     *
     * @return Возвращает строку команды для завершения обхода дерева.
     */
    private static String processEndTraversal() {
        String outIndex = "none";
        if (endSteps < 2) {
            outIndex = processEnd(endSteps);
            endSteps++;
        } else {
            System.out.println("end");
            stopLoop = false;
        }
        return outIndex;
    }

    /**
     * Проверка на изменение номера текущего дерева.
     *
     * @return Возвращает true, если номер дерева изменился, иначе false.
     */
    private static boolean checkChangeTreeNumber() {
        return currentTreeNumber != lastCurrentTreeNumber;
    }

    /**
     * Обработка завершения пути.
     *
     * @param step Текущий шаг завершения.
     * @return Возвращает строку команды для текущего шага завершения.
     */
    private static String processEnd(Integer step) {
        String outIndex = "none";
        if (step == 0) {
            outIndex = "MOV_IN_" + getLastTreeZone() + "_TO_" + getLastCheckpoint();
        } else if (step == 1) {
            outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + "FINISH";
        }
        return outIndex;
    }

    /**
     * Создание пути внутри текущей зоны.
     *
     * @return Возвращает строку команды для пути внутри текущей зоны.
     */
    private static String createPathInCurrentZone() {
        return "MOV_IN_" + getLastTreeZone() + "_TO_" + getLastCheckpoint();
    }

    /**
     * Создание пути для указанной зоны.
     *
     * @param currentTreeNumber     Номер текущего дерева.
     * @param currentTreeZoneNumber Номер текущей зоны дерева.
     * @param stepForZone           Текущий шаг в зоне.
     * @param treeNumberChange      Флаг изменения номера дерева.
     * @return Возвращает строку команды для указанной зоны.
     */
    private static String createPathForZone(int currentTreeNumber, int currentTreeZoneNumber, int stepForZone, boolean treeNumberChange) {
        String outIndex = "none";
        if (stepForZone == 0) {
            outIndex = processCurrentTreeZone(currentTreeNumber, currentTreeZoneNumber, treeNumberChange);
        } else if (stepForZone == 1) {
            outIndex = getGrabModeInArray(TREE_ZONE_NAMES[currentTreeZoneNumber]);
        }
        return outIndex;
    }

    /**
     * Обработка текущей зоны дерева.
     *
     * @param currentTreeNumber     Номер текущего дерева.
     * @param currentTreeZoneNumber Номер текущей зоны дерева.
     * @param treeNumberChange      Флаг изменения номера дерева.
     * @return Возвращает строку команды для текущей зоны дерева.
     */
    private static String processCurrentTreeZone(int currentTreeNumber, int currentTreeZoneNumber, boolean treeNumberChange) {
        String outIndex = "none";
        if (lastDelivery) {
            outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + getLastTreeZone();
            lastDelivery = false;
        } else if (treeNumberChange) {
            outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber];
        } else {
            outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber];
        }
        setLastTreeZone(TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber]);
        return outIndex;
    }

    private static String createPathForDelivery(int currentTreeNumber, int currentTreeZoneNumber, int stepForZone) {
        String outIndex = "none";
        if (stepForZone == 1) {
            if (currentTreeZoneNumber < 1) {
                outIndex = "MOV_IN_" + getLastTreeZone() + "_TO_" + getLastCheckpoint();
            } else {
                outIndex = "MOV_IN_" + TREE_NAMES[currentTreeNumber] + "_" + TREE_ZONE_NAMES[currentTreeZoneNumber - 1] + "_TO_" + getLastCheckpoint();
            }
        } else if (stepForZone == 2) {
            outIndex = "MOV_IN_" + getLastCheckpoint() + "_TO_" + getConForFruit(findFruitName);
        } else if (stepForZone == 3) {
            outIndex = "RESET_FRUIT";
        } else if (stepForZone == 4) {
            lastDelivery = true;
            outIndex = "MOV_IN_" + getConForFruit(findFruitName) + "_TO_" + getLastCheckpoint();
        }
        return outIndex;
    }

    private static String getGrabModeInArray(String zoneName) {
        return grabTypeForZones.getOrDefault(zoneName, "none");
    }

    private static String getConForFruit(String fruitName) {
        return containersForFruits.getOrDefault(fruitName, "none");
    }

    private static void setLastCheckpoint(String lastCheckpoint) {
        TreeTraverse.lastCheckpoint = lastCheckpoint;
    }

    private static void setLastTreeZone(String treeZone) {
        TreeTraverse.treeZone = treeZone;
    }

    private static String getLastTreeZone() {
        return TreeTraverse.treeZone;
    }

    private static String getLastCheckpoint() {
        return lastCheckpoint;
    }
}

