package frc.robot.StateMachine.Cases;

import org.opencv.core.Point;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotContainer;
import frc.robot.StateMachine.*;
import frc.robot.functions.Function;
import frc.robot.subsystems.Training;

public class AutoGrab implements IState{

    private Training train = RobotContainer.train;
    public int nowStep = 0;

    // Переменные для вращающегося механизма
    private double startKoef, diffSpeed, fruitPosX = 0; 
    private boolean rotateStop, objectFind, objectNotFound = false; 

    private boolean objectDetectionFlag, oneObject = true;

    private double currentTargetDegree = 0; 

    private static final double[][] speedForRotate =  { { 0, 50, 100, 150, 200 }, { 0, 12, 18, 25, 30} };
    private double[][] startKoefSpeedForX = { { 0, 333, 666, 1000 }, { 0, 0.33, 0.66, 1 } };
    private static final double[][] arrForLift = { { 0, 290, 640} , { -60, 0, 60} };

    // Переменные для выдвижного механизма

    private double fruitPosY = 0; 
    private double diffSpeedForGlide = 0; 
    private boolean glideStop = false; 
    private double stopTimer;

    private double localTimer;

    private double lastUpdateTime = 0;
    private static final double STEP = 1.0; 

    private static final double[][] speedForGlideServo = { { 0, 10, 20, 40, 60, 80, 100 } ,
                                                     { 0, 0.15, 0.2, 0.25, 0.3, 0.4, 0.4} };

                                                     
    // Данный метод не тестировал не знаю как себя поведет
    
    public AutoGrab() {
        this.nowStep = 0; 
    }

    public AutoGrab(boolean oneObject) {
        this.oneObject = oneObject; 
        this.nowStep = 0; 
    }


    @Override
    public boolean execute() {

        RobotContainer.train.nowTask = 2; 

        if (objectNotFound) { // Ничего не нашли выходим из данной команды
            return true;
        }

        switch (nowStep)
        {
            case 0: // Выравнивание вращающегося механизма на объекте
                   servoController(130, 270); 
                   if (train.centersForClass.isEmpty()) {
                       fruitPosX = 0; 
                       objectFind = false;
                    } else {
                        if (objectDetectionFlag) {
                            for (Point center : train.centersForClass) {
                                fruitPosX  = center.x; 
                            }
                             if (!oneObject) {
                                 objectDetectionFlag = false;
                            }
                         }
                     }
                     
                     objectFind = true;
                     
                     if (oneObject) {
                         if (objectFind) {
                             this.startKoef = Function.TransitionFunction(System.currentTimeMillis() - StateMachine.iterationTime, startKoefSpeedForX);
                             this.diffSpeed = Function.TransitionFunction(this.fruitPosX - 290, speedForRotate);
                             this.rotateStop = Function.BooleanInRange(this.diffSpeed, -3, 3);
                             train.rotateMotorSpeedThread = diffSpeed * startKoef;
                         } else {
                             train.rotateMotorSpeedThread = 0;
                        }
                    } else {
                        currentTargetDegree = Function.TransitionFunction(fruitPosX, arrForLift);
                        SmartDashboard.putNumber("diffSpeed: ", currentTargetDegree);
                        this.rotateStop = train.rotateToPos(currentTargetDegree); 
                    }
                    
                    if (rotateStop) {
                        train.rotateMotorSpeedThread = 0;
                    if (this.rotateStop && Timer.getFPGATimestamp() - stopTimer > 2) {
                        localTimer = Timer.getFPGATimestamp();
                        nowStep++;
                       }
                    } else {
                        stopTimer = Timer.getFPGATimestamp();
                    } 

                    objectNotFound = Timer.getFPGATimestamp() - StateMachine.startTime > 10; // Ничего не нашли выходим из данной команды
            break;
            case 1: 
                   RobotContainer.train.resizeForGlide = true; // Обрезаем изображение по линии выдвижения
                   if (train.centersForClass.isEmpty()) {
                       fruitPosY = 0; 
                       objectFind = false;
                    } else {
                        for (Point center : train.centersForClass) {
                            fruitPosY  = center.y; 
                            SmartDashboard.putNumber("center.y: ", center.y);
                        }
                        objectFind = true;
                    }
                    
                    double glideServoSpeed = Function.TransitionFunction(230 - fruitPosY, speedForGlideServo);
                    glideStop = Function.BooleanInRange(230 - fruitPosY, -3, 3);

                    if (objectFind) {
                        train.justMoveForGlide(glideServoSpeed);
                    } else {
                        train.justMoveForGlide(0.5);
                    }

                    if (glideStop) {
                        train.justMoveForGlide(0);
                        RobotContainer.train.resizeForGlide = false;
                        if (Timer.getFPGATimestamp() - stopTimer > 1) {
                            localTimer = Timer.getFPGATimestamp();
                            nowStep++;
                        }
                    } else {
                        stopTimer = Timer.getFPGATimestamp();
                    } 
            break;
            case 3: // Опускаем лифт для захвата
                    if (train.liftToMovePos(98) && Timer.getFPGATimestamp() - localTimer > 1) {
                        localTimer = Timer.getFPGATimestamp();
                        nowStep++;
                    }
            break;
            case 4: // Захват
                   if (smoothServoMovement(160.0, 0.01) && Timer.getFPGATimestamp() - localTimer > 1) {
                       localTimer = Timer.getFPGATimestamp();
                       nowStep++;
                    }
            break;
            case 5: // Возврат СМО в исходное сотояние
                  train.servoGlidePosition(0);
                  if (train.liftToMovePos(-1) && train.glideExit && Timer.getFPGATimestamp() - localTimer > 1) {
                      return true;
                  }
            break;
            default:
            nowStep = 0;
            return true;
        }

        train.setAxisSpeed(0, 0);

        return false;
    }

    private boolean smoothServoMovement(double targetPosition, double DELAY) {
        double currentPosition = train.getGripServoValue();
        double step = STEP * (targetPosition > currentPosition ? 1 : -1);

        double currentTime = Timer.getFPGATimestamp();

        if (Math.abs(targetPosition - currentPosition) > Math.abs(step)) {
            if (currentTime - lastUpdateTime >= DELAY) {
                currentPosition += step;
                train.setGripServoValue(currentPosition); // Устанавливаем новое положение серво
                lastUpdateTime = currentTime; // Обновляем время последнего обновления
            }
        }     

        return Function.BooleanInRange(Math.abs(targetPosition - currentPosition), -1, 1); 
    }
    
    private void servoController(double gripValue, double gripRotateValue) {
        train.setGripServoValue(gripValue);
        train.setGripRotateServoValue(gripRotateValue);
    }
}
