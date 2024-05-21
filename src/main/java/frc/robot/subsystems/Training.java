package frc.robot.subsystems;

//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.functions.Function;
import frc.robot.functions.MeanFilter;
import frc.robot.functions.MedianFilter;
import frc.robot.functions.PID;
import frc.robot.StateMachine.StateMachine;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

import java.util.ArrayList;

//Vendor imports
import com.kauailabs.navx.frc.AHRS;
import com.studica.frc.Servo;
import com.studica.frc.ServoContinuous;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

public class Training extends SubsystemBase
{
    private TitanQuad rightMotor, leftMotor, rotateMotor, liftMotor;
    private TitanQuadEncoder rightEnc, leftEnc, rotateEnc, liftEnc;

    private double rightEncResetValue, leftEncResetValue;
    private double speedRpid, speedLpid, speedBpid;
    private double rightLast, leftLast = 0;

    public static double posX, posY;

    private double liftSpeed, glideSpeed;

    private MedianFilter sharpRightFilter, sharpLeftFilter, sonicRightFilter, sonicBackFilter;
    private MeanFilter rightMotorMeanFilter, leftMotorMeanFilter, glideMotorMeanFilter, liftMotorMeanFilter;

    private Ultrasonic sonicRight, sonicBack;
    private AnalogInput sharpRight, sharpLeft, cobraGlide;

    private DigitalOutput redLED, greenLED;
    private DigitalInput limitSwitchLift, startButton, EMS;
    
    private AHRS gyro;
    double currentRotatePos;
    // SERVO
    private Servo servoGrab, servoTurnGrab;
    private ServoContinuous servoGlide; 
    
    private Encoder limitSwitchGlide;

    public boolean initLift, initGlide, glideReachedPos, glideStop, finish = false;
    private boolean flag, isFirstRotateCall = true;
    
    public static ArrayList<Integer> indexList = new ArrayList<>();

    // Подключение ПИДов
    private boolean usePIDForMotors = true; 

    private final PID rightPID = new PID(0.215, 0.095, 0.0001, -100, 100); // Настройка ПИДа правого мотора 
    private final PID leftPID = new PID(0.215, 0.095, 0.0001, -100, 100); // Настройка ПИДа левого мотора
    private final PID liftPID = new PID(0.35, 0.065, 0.0001, -100, 100); // Настройка ПИДа лифта
    private final PID rotatePID = new PID(0.215, 0.095, 0.0001, -100, 100); // Настройка ПИДа 
                                         // 0.15, 0.095, 0.0001, -100, 100
                                         
    // private final PID rightPID = new PID(0.15, 0.095, 0.0001, -100, 100); // Настройка ПИДа правого мотора 
    // private final PID leftPID = new PID(0.15, 0.095, 0.0001, -100, 100); // Настройка ПИДа левого мотора

    public int nowResult = 0;
    public int nowTask = 0; 

    public boolean firstInitForGlide = false; 
    public boolean firstInitForLift = false; 

    public boolean firstInitForGlideDone = false;
    public boolean firstInitForLiftDone = false;

    private boolean firstCallForOMS = true;
    public boolean successInit = false;

    public double leftMotorSpeedThread = 0; 
    public double rightMotorSpeedThread = 0; 
    public double liftMotorSpeedThread = 0; 
    public double rotateMotorSpeedThread = 0;

    // Glide
    private boolean blackLineFlag = false; 
    private boolean direction = false; 
    private int currentGlidePosition = 0;
    public boolean glideExit = false;

    private double encRightResetValue = 0;
    private double encLeftResetValue = 0;
    private double encRotateResetValue = 0; 

    public float newGyroThread = 0;
    public boolean resetGyroThread = false;
    public boolean resetGyroThreadOnce = false;
    public float resetGyroValue = 0;
    private float lastYaw = 0;

    public boolean plus360once = false;
    public boolean minus360once = false;
 
    private static final double[][] speedForGlideServo = { { -10, -8, -6, -4, -2, 2, 4, 6, 8, 10 } ,
                                                 { -0.4, -0.4, -0.3, -0.25, -0.2, 0.2, 0.25, 0.3, 0.4, 0.4} };

    private static final double[][] arrOfPosForLift = { { -1, 0, 15, 30, 40, 55, 70, 80, 90, 100 }, 
                                                         { 0, 600, 900, 1200, 1500, 1800, 2100, 2400, 2900, 3200 } };

    private static final double[][] arrOfPosForRotate = { { 0, 500, 1500 },
                                                             { 0, 45, 90 } };
    
    // private static final double[][] arrOfPosForRotate = 
    //             { { -1000, -500, -400, -350, -300, -200, -100, 0, 100, 200, 300, 350, 400, 500, 1000 },
    //             { -90, -45, -40, -35, -30, -20, -10, 0, 10, 20, 30, 35, 40, 45, 90 } };

    private static final double[][] speedForRotate =  { { 0, 5, 18, 36, 54 },
                                                        { 0, 5, 10, 25, 35 } };

    // private static final double[][] speedForRotate =  { { -90, -72, -54, -36, -18, -5, 0, 5, 18, 36, 54, 72, 90 },
    //                                                     { -15, -15, -15, -15, -15, -15, 0, 15, 15, 15, 15, 15, 15 } };

    // private static final double[][] speedForRotate =  { { 0, 5, 18, 36, 54, 72, 90 },
    //                                                     { 0, 4, 13, 20, 30, 47, 60 } };

    private static final double[][] arrForLift = { { -350, -200, -100, -20, -8, 0, 8, 100, 200, 350 } ,
                                                    { -72, -48, -24, -12, -8, 0, 13, 20, 45, 60 } };

    public Training()       
    {                                   // почему не константы
        rightMotor = new TitanQuad(42, 1); 
        leftMotor = new TitanQuad(42, 3); 
        rotateMotor = new TitanQuad(42, 0); 
        liftMotor = new TitanQuad(42, 2); 

        rightEnc = new TitanQuadEncoder(rightMotor, 1, 1);
        rightEnc.setReverseDirection();
        leftEnc = new TitanQuadEncoder(leftMotor, 3, 1);
        rotateEnc = new TitanQuadEncoder(rotateMotor, 0, 1);
        rotateEnc.setReverseDirection();
        liftEnc = new TitanQuadEncoder(liftMotor, 2, 1);
        liftEnc.setReverseDirection();

        sharpRight = new AnalogInput(0);
        sharpLeft = new AnalogInput(1);

        // Датчик черной линии для подсчета линий на Glide
        cobraGlide = new AnalogInput(2);

        sonicBack = new Ultrasonic(9, 8);
        sonicRight = new Ultrasonic(11, 10);

        sharpRightFilter = new MedianFilter(5);
        sharpLeftFilter = new MedianFilter(5);

        sonicRightFilter = new MedianFilter(6);
        sonicBackFilter = new MedianFilter(10);

        redLED = new DigitalOutput(20);
        greenLED = new DigitalOutput(21);

        // Инициализация концевого выключателя 
        limitSwitchLift = new DigitalInput(0);

        // Инициализация сервоприводов
        servoGrab = new Servo(0);
        // 177 закрыть 
        // 110 открыть
        // для фруктов:
        // большое яблоко - 160
        // маленькое яблоко - 177
        // груша - 164
        servoTurnGrab = new Servo(2);
        // 190 смотрит вперед
        // 279 смотрит вниз
        // 222 смотрит вперед и чуть ниже
        servoGlide = new ServoContinuous(1);
        
        // 
        // 
        // 
        // 

        startButton = new DigitalInput(2);
        EMS = new DigitalInput(3);

        gyro = new AHRS();
        
    //     // Поток для anal
        new Thread( () -> {
            while(!Thread.interrupted())
            {
                try {
                // // Инициализируем СМО
                // if (firstInitForGlide && !firstInitForGlideDone) {
                //     firstInitForGlideDone = initForGlide();
                //     if(firstInitForGlideDone) {
                //         // setGripRotateServoValue(32); 
                //         // setGripServoValue(60);
                //         // setMainRotateServoValue(230);
                //     }
                // } else {
                //     if (firstInitForGlideDone) {
                //         if (firstInitForLift && !firstInitForLiftDone) {
                //             firstInitForLiftDone = initForLift();
                //         } else {
                //             if(firstInitForGlideDone && firstInitForLiftDone) {
                //                 glideReachedPos = true; //glideToMovePos(50)
                //                 if(glideReachedPos) {
                //                     successInit = true;
                //                 }
                //             }  
                //         }   
                //     }
                // }
                    successInit = getLimitSwitchLift();

                    float yaw = (float)getLongYaw();
                    float dYaw = yaw - lastYaw;
                    float outYaw = 0;
                    
                    if (!resetGyroThread && !resetGyroThreadOnce)
                    {
                        outYaw = dYaw + newGyroThread;
                    }
                    if (resetGyroThread)
                    {
                        outYaw = resetGyroValue;
                    }
                    if (resetGyroThreadOnce)
                    {
                        outYaw = resetGyroValue;
                        resetGyroThreadOnce = false;
                    }
                    if (plus360once)
                    {
                        outYaw += 360;
                        plus360once = false;
                    }
                    if (minus360once)
                    {
                        outYaw -= 360;
                        minus360once = false;
                    }
                    newGyroThread = outYaw;
                    lastYaw = yaw;

                    Thread.sleep(5);
                }
                catch (Exception e) 
                {
                    e.printStackTrace();
                    DriverStation.reportError("INITIALIZATION THREAD ERROR", true);
                }
            }
        }).start();

        // Поток для работы с моторами
        new Thread( () -> {
            while(!Thread.interrupted())
            {
                try
                 {
                    // if (getEMSButton()) {
                    if (false) {
                        setLeftMotorSpeed(0.0, true);
                        setRightMotorSpeed(0.0, true);
                        setLiftMotorSpeed(0.0, true);
                        setRotateMotorSpeed(0.0, true);
                        try {
                            // mainRotate.setDisabled();
                            // gripRotate.setDisabled();
                            // grip.setDisabled();
                        } catch (Exception e) {
                            System.out.println("Pizdec Servakam");
                        }

                    } else {
                        setLeftMotorSpeed(leftMotorSpeedThread, usePIDForMotors);
                        setRightMotorSpeed(rightMotorSpeedThread, usePIDForMotors);
                        setLiftMotorSpeed(liftMotorSpeedThread, usePIDForMotors);
                        setRotateMotorSpeed(rotateMotorSpeedThread, usePIDForMotors);
                        // servoGrab.setDisabled();
                    }
                    
                    Thread.sleep(5);
                } 
                catch (Exception e) 
                {
                    e.printStackTrace();
                    DriverStation.reportError("EMS THREAD ERROR", true);
                }
            }
        }).start();
    }

    // /**
    //  * Метод для инициализации выдвижного механизма
    //  */
    // public boolean initForGlide() {
    //     try {
    //         if (getLimitSwitchGlide()) {
    //             resetGlideEncoder();
    //             glideMotorSpeedThread = 0;
    //             return true;
    //         } else {
    //             glideMotorSpeedThread = 60;
    //         }
    //         return false;
    //     } catch (Exception e) {
    //         e.printStackTrace();
    //         return true;
    //     }
        
    // }
    

    /**
     * Метод для инициализации лифта
     */
    public boolean initForLift() {
        try {
            if (getLimitSwitchLift()) {
                resetLiftEncoder();
                liftMotorSpeedThread = 0;
                return true;
            } else {
                liftMotorSpeedThread = 10;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        
    }

            /**
     * Метод для управления поворотом стрелы
     * @param degree - позиция, на которую захват должен повернуться 
     */
    // Для данного механизма не предусмотрен сброс позиции поэтому придется каждый раз при запуске выравнивать его рукой
    // Данная функция не дописана!

    public boolean rotateToPos(double degree) {
        
        double currentRotatePos = getEncRotateThread();
       
        double rotateDegree = Function.TransitionFunction(currentRotatePos, arrOfPosForRotate);
        double rotateSpeedOut = Function.TransitionFunction(rotateDegree - degree, speedForRotate);
        
        boolean rotateStop = Function.BooleanInRange(degree - rotateDegree, -7, 7);

        SmartDashboard.putNumber("currentRotatePos", -getEncRotateThread());
        SmartDashboard.putNumber("rotateDegree", rotateDegree);
        SmartDashboard.putNumber("rotateDiff", rotateDegree - degree);
        SmartDashboard.putNumber("rotateSpeedOut", rotateSpeedOut);
        SmartDashboard.putBoolean("rotateStop", rotateStop);

        if (rotateStop) {
            rotateMotorSpeedThread = 0;
            SmartDashboard.putNumber("rotateCheck", 3);
            return true;
        } else {
            SmartDashboard.putNumber("rotateCheck", 4);
            rotateMotorSpeedThread = -rotateSpeedOut;
        }

        if ((rotateSpeedOut < 0 && -getEncRotateThread() < -1600) || (rotateSpeedOut > 0 && -getEncRotateThread() > 1600)) {
            rotateMotorSpeedThread = 0;
            SmartDashboard.putNumber("rotateCheck", 324);
            return true;
        } 
        return false;
    }


    // public boolean rotateToPos(double degree) {
        
    //     double currentRotatePos = -getRotateEncoder();
    //     double rotateDegree = Function.TransitionFunction(currentRotatePos, arrOfPosForRotate); 
    //     double rotateSpeedOut = -Function.TransitionFunction(rotateDegree - degree, speedForRotate); 
    //     boolean rotateStop = Function.BooleanInRange(rotateDegree - degree, -0.5, 0.5);

    //     SmartDashboard.putNumber("currentRotatePos", currentRotatePos); 
    //     SmartDashboard.putNumber("rotateDegree", rotateDegree); 
    //     SmartDashboard.putNumber("rotateDiff", rotateDegree - degree); 
    //     SmartDashboard.putNumber("rotateSpeedOut", rotateSpeedOut); 

    //     if (rotateSpeedOut < 0 && currentRotatePos < -1600) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("RotateCheck", 1);
    //         return true;
    //     } else if (rotateSpeedOut > 0 && currentRotatePos > 1600) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("RotateCheck", 2);
    //         return true;
    //     } else if (rotateStop) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("RotateCheck", 3);
    //         return true;
    //     } else {
    //         rotateMotorSpeedThread = rotateSpeedOut;
    //         if(rotateStop) {
    //             SmartDashboard.putNumber("RotateCheck", 4);
    //             isFirstRotateCall = false;
    //             return true;
    //         }
    //     }
        
    //     return false;
    // }

    // /**
    //  * Метод для управления позицией поворота стрелы
    //  * @param pos - позиция, на которую захват должен повернуться 
    //  */
    // // Для данного механизма не предусмотрен сброс позиции поэтому придется каждый раз при запуске выравнивать его рукой
    // // Данная функция не дописана!

    // public boolean rotateToPos(double degree) {

    //     double currentRotatePos = -getRotateEncoder();
    //     double rotateDegree = Function.TransitionFunction(currentRotatePos, arrOfPosForRotate); 
    //     double rotateSpeedOut = Function.TransitionFunction(rotateDegree - degree, speedForRotate); 
    //     boolean rotateStop = Function.BooleanInRange(rotateDegree - degree, -5, 5);

    //     if (rotateSpeedOut > 0 && currentRotatePos < -1600) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("RotateCheck", 1);
    //         return true;
    //     } else if (rotateSpeedOut < 0 && currentRotatePos > 1600) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("RotateCheck", 2);
    //         return true;
    //     } else if (rotateStop) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("RotateCheck", 3);
    //         return true;
    //     } else {
    //         rotateMotorSpeedThread = -rotateSpeedOut;
    //         if(rotateStop) {
    //             SmartDashboard.putNumber("RotateCheck", 4);
    //             return true;
    //         }
    //     }

    //     SmartDashboard.putNumber("currentRotatePos", currentRotatePos); 
    //     SmartDashboard.putNumber("rotateDegree", rotateDegree); 
    //     SmartDashboard.putNumber("rotateDiff", rotateDegree - degree); 
    //     SmartDashboard.putNumber("rotateSpeedOut", rotateSpeedOut); 

    //     return false;
    // }

    // public boolean rotateToPos(double degree) {

    //     currentRotatePos = -getRotateEncoder();
        
    //     double rotateDegree = Function.TransitionFunction(currentRotatePos, arrOfPosForRotate); 
    //     double rotateSpeedOut = Function.TransitionFunction(rotateDegree - degree, speedForRotate); 
    //     boolean rotateStop = Function.BooleanInRange(rotateDegree - degree, -5, 5);

    //     if (rotateSpeedOut < 0 && -getRotateEncoder() < -1000) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("rotateCheck", 1);
    //         return true;
    //     } else if (rotateSpeedOut > 0 && -getRotateEncoder() > 1000) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("rotateCheck", 2);
    //         return true;
    //     } else if (rotateStop) {
    //         rotateMotorSpeedThread = 0;
    //         SmartDashboard.putNumber("rotateCheck", 3);
    //         return true;
    //     } else {
    //         SmartDashboard.putNumber("rotateCheck", 4);
    //         rotateMotorSpeedThread = -rotateSpeedOut;
    //         if(rotateStop) {
    //             rotateMotorSpeedThread = 0;
    //             resetRotateEncoder();
    //             return true;
    //         }
    //     }
    //     SmartDashboard.putNumber("currentRotatePos", -getRotateEncoder()); 
    //     SmartDashboard.putNumber("rotateDegree", rotateDegree); 
    //     SmartDashboard.putNumber("rotateDiff", rotateDegree - degree); 
    //     SmartDashboard.putNumber("rotateSpeedOut", rotateSpeedOut); 
    //     return false;
    // }

        // public boolean rotateToPos(double degree) {

        //     double currEncValue = -getEncRotateThread();
        //     double encDegree = Function.TransitionFunction(currEncValue, arrOfPosForRotate);
        //     double speedRotate = Function.TransitionFunction(degree - encDegree, speedForRotate);
        //     boolean stopRotate = Function.BooleanInRange(degree - encDegree, -1, 1);
        //     rotateMotorSpeedThread = 30;
        //     if(rotateMotorSpeedThread > 0 && currEncValue > 500) {
        //         rotateMotorSpeedThread = 0;
        //     }
        //     return false;
        // }

    /**
     * Метод для управления позицией лифта
     * @param pos - позиция, на которую лифт должен подвинуться
     */
    public boolean liftToMovePos(double pos) {

        double nowPos = -getLiftEncoder();
        double encPos = Function.TransitionFunction(pos, arrOfPosForLift); 
        double speed = Function.TransitionFunction(nowPos - encPos, arrForLift); 
        boolean liftStop = Function.BooleanInRange(nowPos - encPos, -5, 5);
        
        SmartDashboard.putNumber("LiftSpeed", speed); 
        SmartDashboard.putNumber("DiffForLift", encPos - nowPos); 
        
        if (getLimitSwitchLift() && speed > 0) {
            liftMotorSpeedThread = 0;
            resetLiftEncoder();
            return true; 
        } else if (speed < 0 && nowPos < -3000) {
            liftMotorSpeedThread = 0;
            return true;
        } else if (liftStop && !getLimitSwitchLift()) {
            liftMotorSpeedThread = 0;
            return true; 
        } else {
            liftMotorSpeedThread = speed;
            return false;
        }  
    }

    /**
     * @return значение с энкодера правого мотора
     */
    public double getRightEncoder(){
        return rightEnc.getEncoderDistance();
    }

    /** 
     * @return значение с энкодера левого мотора
     */
    public double getLeftEncoder(){
        return leftEnc.getEncoderDistance();
    }

    /**
     * @return Значение с энкодера крутящего механизма
     */
    public double getRotateEncoder(){
        return rotateEnc.getEncoderDistance();
    }

    /**
     * Сброс энкодера мотора поворотного механизма
     */
    public void resetRotateEncoder(){
        rotateEnc.reset();
    }

    /**
     * @return значение с энкодера мотора лифта
     */
    public double getLiftEncoder() {
        return liftEnc.getEncoderDistance();
    }
    
    public void resetLeftEnc() {
        leftEnc.reset();
    }

    public void resetRightEnc() {
        rightEnc.reset();
    }

    // эксперименты
    public void resetEncRight()
    {
        encRightResetValue = rightEnc.getEncoderDistance();
    }

    public void resetEncLeft()
    {
        encLeftResetValue = leftEnc.getEncoderDistance();
    }

    public double getEncLeftThread() {
        double enc = leftEnc.getEncoderDistance() - encLeftResetValue;
        return enc;
    }

    public double getEncRightThread() {
        double enc = rightEnc.getEncoderDistance() - encRightResetValue;
        return enc;
    }

    public double getEncRotateThread() {
        double enc = rotateEnc.getEncoderDistance() - encRotateResetValue;
        return enc;
    }

    public void resetEncRotate()
    {
        encRotateResetValue = rotateEnc.getEncoderDistance();
    }


    /**
     * Сброс энкодера мотора лифта
     */
    public void resetLiftEncoder(){
        liftEnc.reset();
    }

    /**
     * Опрос концевого выключателя лифта
     * @return Нажатие на концевик
     */
    public boolean getLimitSwitchLift(){
        try {
            boolean out = limitSwitchLift.get(); 
            SmartDashboard.putBoolean("LimitSwitchLift", out);
            return out;
        } catch (Exception e) {
            return false;
        }  
    }

    /**
     * Опрос кнопки экстренной остановки
     * @return Нажатие на кнопку
     */
    public boolean getEMSButton(){
        try {
            boolean out = EMS.get();
            return out;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Опрос кнопки старта
     * @return Нажатие на кнопку
     */
    public boolean getStartButton(){
        try {
            // boolean out = startButton.getDistance() == 2 || startButton.getDistance() == -1; 
            // return out;
            return false;
        } catch (Exception e) {
            return false;
        }   
    }

     /**
     * Управление зеленой индикацией
     * @param led - включен или выключен для true и false соответственно
     */
    public void setGreenLED(boolean led){
        greenLED.set(led);
    }

    /**
     * Управление красной индикацией
     * @param led - включен или выключен для true и false соответственно
     */
    public void setRedLED(boolean led){ 
        redLED.set(led);
    }

    /**
     * Управление левым мотором
     * @param speed - скорость в диапазоне от -100 до 100
     * @param withPID - подключает ПИДы к левому мотору
     */
    public void setLeftMotorSpeed(double speed, boolean withPID) {
        if (speed == 0.0) {
            leftPID.reset();
            leftMotor.set(0);
        } else {
            leftPID.calculate(-leftEnc.getSpeed(), speed);
            if (withPID) {
                leftMotor.set(leftPID.getOutput());
            } else {
                leftMotor.set(speed/100);
            }
        }
        SmartDashboard.putNumber("outLeftMotorSpeed", speed); 
    }

    /**
     * Управление правым мотором
     * @param speed - скорость в диапазоне от -100 до 100
     * @param withPID - подключает ПИДы к правому мотору
     */
    public void setRightMotorSpeed(double speed, boolean withPID) {
        if (speed == 0.0) {
            rightPID.reset();
            rightMotor.set(0);
        } else {
            rightPID.calculate(-rightEnc.getSpeed(), speed);
            if (withPID) {
                rightMotor.set(rightPID.getOutput());
            } else {
                rightMotor.set(speed/100);
            }
        }
        SmartDashboard.putNumber("outRightMotorSpeed", speed); 
    }

    /**
     * Управление мотором лифта
     * @param speed - скорость в диапазоне от -100 до 100
     * @param withPID - подключает ПИДы к мотору лифта
     */
    public void setLiftMotorSpeed(double speed, boolean withPID) {
        if (speed == 0.0) {
            liftPID.reset();
            liftMotor.set(0);
        } else {
            liftPID.calculate(liftEnc.getSpeed(), speed);
            if (withPID) {
                liftMotor.set(liftPID.getOutput());
                // liftMotor.set(Function.getLimitedValue(liftPID.getOutput(), -0.15, 0.15));
            } else {
                liftMotor.set(speed / 100);
            }
        }
        SmartDashboard.putNumber("outLiftMotorSpeed", speed); 
    }

    /**
     * Управление мотором вращающего механизма
     * @param speed - скорость в диапазоне от -100 до 100
     * @param withPID - подключает ПИДы к мотору выдвижного механизма
     */
    public void setRotateMotorSpeed(double speed, boolean withPID) {
        if (speed == 0.0) {
            rotatePID.reset();
            rotateMotor.set(0);
        } else {
            rotatePID.calculate(-rotateEnc.getSpeed(), speed);
            if (withPID) {
                rotateMotor.set(-rotatePID.getOutput());
                // glideMotor.set(Function.getLimitedValue(glidePID.getOutput(), -0.15, 0.15));
            } else {
                rotateMotor.set(speed / 100);
            }
        }
        SmartDashboard.putNumber("outRotateMotorSpeed", speed); 
    }

     /**
     * Установка скоростей для моторов
     * @param x - скорость на ось X в диапазоне от -100 до 100
     * @param z - скорость на ось Z в диапазоне от -100 до 100
     */
    public void setAxisSpeed(double x, double z) {
        leftMotorSpeedThread = -x - z; 
        rightMotorSpeedThread = x - z;
    }

    public void OdometryReset(double x, double y) {
        rightLast = 0;
        leftLast = 0;

        rightEnc.reset();
        leftEnc.reset();

        posX = x;
        posY = y;
    }

    public void reset2Motors() {
        rightEnc.reset();
        leftEnc.reset();
    }

    /**
    * Возвращает значение с правого инфракрасного датчика
    * @return Значение с правого инфракрасного датчика в миллиметрах
    */
    public double getRightSharpDistance(){
        try {
            return (sharpRightFilter.Filter((Math.pow(sharpRight.getAverageVoltage(), -1.2045) * 27.726)));
        } catch (Exception e){
            return 0;
        }  
    }
    /**
    * Возвращает значение с левого инфракрасного датчика
    * @return Значение с левого инфракрасного датчика в миллиметрах
    */
    public double getLeftSharpDistance(){
        try {
            return (sharpLeftFilter.Filter((Math.pow(sharpLeft.getAverageVoltage(), -1.2045) * 27.726)));
        } catch (Exception e) {
            return 0;
        }   
    }
     /**
     * Возвращает значение с бокового ультразвукового датчика
     * @return значение с правого ультразвукового датчика в миллиметрах
     */
    public double getSideSonicDistance(){
        try{
            sonicRight.ping();
            Timer.delay(0.005);
            return sonicRightFilter.Filter(sonicRight.getRangeMM() / 10);
        }catch (Exception e){
            return 0;
        }
    }
    /**
     * Возвращает значение с заднего ультразвукового датчика
     * @return значение с заднего инфракрасного датчика в миллиметрах
     */
    public double getBackSonicDistance(){
        try{
            sonicBack.ping();
            Timer.delay(0.005);
            // return sonicBack.getRangeMM() / 10;
            return sonicBackFilter.Filter(sonicBack.getRangeMM() / 10);
        }catch (Exception e){
            return 0;
        }
    }

    /**
     * Устанавливает угол поворота для сервомотра захвата
     * @param value - значение угла для установки на сервомотор захвата
     */
    public void setGripServoValue(int value) {
        try {
            servoGrab.setAngle(value);
        } catch (Exception e) {
            System.out.println("Pizdes servaky setGripServoValue");
        }
        
    }

    /**
     * Устанавливает угол поворота сервомотора для поворота захвата
     * @param value - значение угла для установки на сервомотор поворота захвата
     */
    public void setGripRotateServoValue(int value) {
        try {
            servoTurnGrab.setAngle(value);
        } catch (Exception e) {
            System.out.println("Pizdes servaky setGripRotateServoValue");
        }
        
    }

    //с этой строчки кринж для EMSThread 
    public Servo getGripRotate() {
        return servoTurnGrab;
    }

    public Servo getGrip() {
        return servoGrab;
    } 
    // это для End
    public ServoContinuous getGlide() {
        return servoGlide;
    }

    /**
    * @return Значение с гироскопа в диапазоне от -180 до 180 
    */
    public double getYaw()
    {
        return gyro.getYaw();
    }

    public double getLongYaw()
    {
        return gyro.getAngle();
    }

    public void resetGyro()
    {
        gyro.zeroYaw();
    }

    public double getCobraVoltage() {
        return cobraGlide.getAverageVoltage();
    }

    /**
     * Устанавливает выдвижной механизм на указанную позицию.
     * @param position - от 0 до 16
     */
    public void servoGlidePosition(int targetPosition) { 
        boolean blackLineDetect = getCobraVoltage() > 2.0;
        double glideServoSpeed = Function.TransitionFunction(targetPosition - this.currentGlidePosition, speedForGlideServo);
        SmartDashboard.putNumber("currentGlidePosition", currentGlidePosition);

        this.direction = targetPosition > this.currentGlidePosition;

        if (targetPosition != this.currentGlidePosition) {

            SmartDashboard.putNumber("glideServoSpeed", glideServoSpeed);

            if (this.direction) {
                servoGlide.setSpeed(glideServoSpeed); 
            } else { 
                servoGlide.setSpeed(glideServoSpeed);
            }

            this.glideExit = false;
        } else {
            servoGlide.setDisabled();
            this.glideExit = true;
        }

        if (blackLineDetect && !this.blackLineFlag) {
            if (this.direction) {
                this.currentGlidePosition++; 
            } else {
                this.currentGlidePosition--;
            }
            this.blackLineFlag = true;
        }

        if (!blackLineDetect && this.blackLineFlag) {
            this.blackLineFlag = false;
        }
    }

    @Override
    public void periodic()
    {
        // ENCODERS ------------------------------------------------------
        SmartDashboard.putNumber("rightEnc", getRightEncoder());
        SmartDashboard.putNumber("leftEnc", getLeftEncoder());
        SmartDashboard.putNumber("rotateEnc", getRotateEncoder());
        SmartDashboard.putNumber("liftEnc", getLiftEncoder());

        SmartDashboard.putNumber("speedRightMotor", rightEnc.getSpeed());
        SmartDashboard.putNumber("speedLeftMotor", leftEnc.getSpeed());
        SmartDashboard.putNumber("speedLiftotor", liftEnc.getSpeed());
        SmartDashboard.putNumber("speedRotateMotor", -rotateEnc.getSpeed());

        SmartDashboard.putNumber("posZ", getLongYaw());

        // SENSORS -------------------------------------------------------
        SmartDashboard.putNumber("sharpRight", getRightSharpDistance());
        SmartDashboard.putNumber("sharpLeft", getLeftSharpDistance());
        SmartDashboard.putNumber("sonicRight", getSideSonicDistance());
        SmartDashboard.putNumber("sonicBack", getBackSonicDistance());
        SmartDashboard.putNumber("GlideCobra", getCobraVoltage()); 
    
        // BUTTONS -------------------------------------------------------
        SmartDashboard.putBoolean("startButton", getStartButton());
        SmartDashboard.putBoolean("EMS", getEMSButton());
        SmartDashboard.putBoolean("initlift", initLift);
        SmartDashboard.putBoolean("limitLift", getLimitSwitchLift());

        // test
        SmartDashboard.putBoolean("end", finish);
        SmartDashboard.putNumber("iterationTime", StateMachine.iterationTime);
    }
}