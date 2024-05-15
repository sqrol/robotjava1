package frc.robot.Subsystems;

//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Functions.Function;
import frc.robot.Functions.MeanFilter;
import frc.robot.Functions.MedianFilter;
import frc.robot.Functions.PID;
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
    private DigitalInput limitSwitchLift;
    
    private AHRS gyro;

    // SERVO
    private Servo servoGrab, servoTurnGrab;
    private ServoContinuous servoGlide; 
    
    private Encoder limitSwitchGlide, startButton, EMS;

    public boolean initLift, initGlide, glideReachedPos, glideStop, finish = false;
    private boolean flag, isFirstRotateCall = true;
    
    public static ArrayList<Integer> indexList = new ArrayList<>();

    // Отключение ПИДов
    private boolean usePIDForMotors = true; 

    private final PID rightPID = new PID(0.215, 0.095, 0.0001, -100, 100); // Настройка ПИДа правого мотора 
    private final PID leftPID = new PID(0.215, 0.095, 0.0001, -100, 100); // Настройка ПИДа левого мотора
    private final PID liftPID = new PID(0.35, 0.065, 0.0001, -100, 100); // Настройка ПИДа лифта
    private final PID rotatePID = new PID(0.35, 0.065, 0.0001, -100, 100); // Настройка ПИДа 
                                         // 0.15, 0.095, 0.0001, -100, 100
                                         // 0.215, 0.095, 0.0001, -100, 100 норм но можно получше
                                         // 0.215, 0.0775, 0.0001, -100, 100 говно
    // private final PID rightPID = new PID(0.15, 0.095, 0.0001, -100, 100); // Настройка ПИДа правого мотора 
    // private final PID leftPID = new PID(0.15, 0.095, 0.0001, -100, 100); // Настройка ПИДа левого мотора

    public int checkAppleResult = 0;

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
 
    private static final double[][] speedForGlideServo = { { -10, -8, -6, -4, -2, 2, 4, 6, 8, 10 } ,
                                                 { -0.4, -0.4, -0.3, -0.25, -0.2, 0.2, 0.25, 0.3, 0.4, 0.4} };

    private static final double[][] arrOfPosForLift = { { -1, 0, 15, 30, 40, 55, 70, 80, 90, 100 }, 
                                                         { 0, 10, 300, 450, 600, 800, 1100, 1500, 1900, 2300 } };

    private static final double[][] convertToDegrees = { { -1540, -500, 0, 500, 1540 },
                                                          { -90, -45, 0, 45, 90 } };

    private static final double[][] speedForRotate =  { { 0, 5, 18, 36, 54, 72, 90 },
                                                        { 10, 25, 35, 45, 50, 55, 60 } };

    // private static final double[][] speedForRotate =  { { 0, 5, 18, 36, 54, 72, 90 },
    //                                                     { 0, 4, 13, 20, 30, 47, 60 } };

    private static final double[][] arrForLift = { { -350, -200, -100, -20, -8, 0, 8, 100, 200, 350 } ,
                                                    { -72, -48, -24, -12, -8, 0, 13, 20, 45, 60 } };

    public Training()
    {
        rightMotor = new TitanQuad(Constants.TITAN_ID, Constants.RIGHT_MOTOR);
        leftMotor = new TitanQuad(Constants.TITAN_ID, Constants.LEFT_MOTOR); 
        rotateMotor = new TitanQuad(Constants.TITAN_ID, Constants.ROTATE_MOTOR); 
        liftMotor = new TitanQuad(Constants.TITAN_ID, Constants.LIFT_MOTOR); 

        rightEnc = new TitanQuadEncoder(rightMotor, Constants.RIGHT_ENC, Constants.ENCODER_DIST_PER_TICK);
        rightEnc.setReverseDirection();
        leftEnc = new TitanQuadEncoder(leftMotor, Constants.LEFT_ENC, Constants.ENCODER_DIST_PER_TICK);
        rotateEnc = new TitanQuadEncoder(rotateMotor, Constants.ROTATE_ENC, Constants.ENCODER_DIST_PER_TICK);
        liftEnc = new TitanQuadEncoder(liftMotor, Constants.LIFT_ENC, Constants.ENCODER_DIST_PER_TICK);
        liftEnc.setReverseDirection();

        sharpRight = new AnalogInput(Constants.RIGHT_SHARP);
        sharpLeft = new AnalogInput(Constants.LEFT_SHARP);

        // Датчик черной линии для подсчета линий на Glide
        cobraGlide = new AnalogInput(Constants.COBRA_CHANNEL);

        sonicBack = new Ultrasonic(Constants.BACK_SONIC_TRIGG, Constants.BACK_SONIC_ECHO);
        // sonicRight = new Ultrasonic(Constants.SIDE_SONIC_TRIGG, Constants.SIDE_SONIC_ECHO);

        sharpRightFilter = new MedianFilter(5);
        sharpLeftFilter = new MedianFilter(5);

        sonicRightFilter = new MedianFilter(6);
        sonicBackFilter = new MedianFilter(10);

        redLED = new DigitalOutput(20);
        greenLED = new DigitalOutput(21);

        // Инициализация концевого выключателя 
        limitSwitchLift = new DigitalInput(0);

        // Инициализация сервоприводов
        servoGrab = new Servo(Constants.GRAB_SERVO);
        // 172 закрыть 
        // 130 открыть
        // для фруктов:
        // большое яблоко - 160
        // маленькое яблоко - 171
        // груша - 159
        servoTurnGrab = new Servo(Constants.GRAB_ROTATE_SERVO);
        // 190 смотрит вперед
        // 283 смотрит вниз
        // 222 смотрит вперед и чуть ниже
        // servoGlide = new ServoContinuous(1);
        
        // 
        // 
        // 
        // 

        // startButton = new Encoder(4, 5);
        // EMS = new Encoder(0, 1);

        gyro = new AHRS();
        
    //     // Поток для anal
        new Thread( () -> {
            while(!Thread.interrupted())
            {
                    try
                        {
    //                 // // Инициализируем СМО
    //                 // if (firstInitForGlide && !firstInitForGlideDone) {
    //                 //     firstInitForGlideDone = initForGlide();
    //                 //     if(firstInitForGlideDone) {
    //                 //         // setGripRotateServoValue(32); 
    //                 //         // setGripServoValue(60);
    //                 //         // setMainRotateServoValue(230);
    //                 //     }
    //                 // } else {
    //                 //     if (firstInitForGlideDone) {
    //                 //         if (firstInitForLift && !firstInitForLiftDone) {
    //                 //             firstInitForLiftDone = initForLift();
    //                 //         } else {
    //                 //             if(firstInitForGlideDone && firstInitForLiftDone) {
    //                 //                 glideReachedPos = true; //glideToMovePos(50)
    //                 //                 if(glideReachedPos) {
    //                 //                     successInit = true;
    //                 //                 }
    //                 //             }  
    //                 //         }   
    //                 //     }
    //                 // }
                    successInit = getLimitSwitchLift();
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
                        // glideServo.setDisabled();
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
                liftMotorSpeedThread = 60;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        
    }

        /**
     * Метод для управления позицией поворота стрелы
     * @param pos - позиция, на которую захват должен повернуться 
     */
    // Для данного механизма не предусмотрен сброс позиции поэтому придется каждый раз при запуске выравнивать его рукой
    // Данная функция не дописана!
    public boolean rotateToPos(double degree) {

        

        double currentRotatePos = -getRotateEncoder();
        
        double rotateDegree = Function.TransitionFunction(currentRotatePos, convertToDegrees); 
        double rotateSpeedOut = Function.TransitionFunction(rotateDegree - degree, speedForRotate); 
        boolean rotateStop = Function.BooleanInRange(rotateDegree - degree, -1, 1);

        if (degree < 0 && currentRotatePos < -1600) {
            rotateMotorSpeedThread = 0;
            SmartDashboard.putNumber("rotateCheck", 1);
            return true;
        } else if (degree > 0 && currentRotatePos > 1600) {
            rotateMotorSpeedThread = 0;
            SmartDashboard.putNumber("rotateCheck", 2);
            return true;
        } else if (rotateStop) {
            rotateMotorSpeedThread = 0;
            SmartDashboard.putNumber("rotateCheck", 3);
            return true;
        } else {
            rotateMotorSpeedThread = rotateSpeedOut;
            SmartDashboard.putNumber("rotateCheck", 4);
            SmartDashboard.putNumber("currentRotatePos", currentRotatePos); 
            SmartDashboard.putNumber("rotateDegree", rotateDegree); 
            SmartDashboard.putNumber("rotateDiff", rotateDegree - degree); 
            SmartDashboard.putNumber("rotateSpeedOut", rotateSpeedOut); 
             
        }
        return false;
    }

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
            speed = 0;
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
     * Сброс энкодера мотора выдвижного механизма
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
            boolean out = EMS.getDistance() == -1 || EMS.getDistance() == 2;
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
            boolean out = startButton.getDistance() == 2 || startButton.getDistance() == -1; 
            return out;
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
                rotateMotor.set(rotatePID.getOutput());
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
    public void servoGlidePosition(int position) { 
        boolean blackLineDetect = getCobraVoltage() > 2.0;
        double glideServoSpeed = Function.TransitionFunction(position - this.currentGlidePosition, speedForGlideServo);
        
        SmartDashboard.putNumber("currentGlidePosition", currentGlidePosition);

        if (position != this.currentGlidePosition) {
            if (position > this.currentGlidePosition) {
                this.direction = true;
                servoGlide.setSpeed(glideServoSpeed);
            } else {
                this.direction = false;
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