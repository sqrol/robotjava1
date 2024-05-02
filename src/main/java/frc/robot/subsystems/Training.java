package frc.robot.subsystems;

//WPI imports
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.functions.Function;
import frc.robot.functions.MeanFilter;
import frc.robot.functions.MedianFilter;
import frc.robot.functions.PID;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DigitalOutput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Encoder;

import java.util.concurrent.TimeUnit;

//Vendor imports
import com.kauailabs.navx.frc.AHRS;
import com.studica.frc.Servo;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

public class Training extends SubsystemBase
{
    private TitanQuad rightMotor, leftMotor, glideMotor, liftMotor;
    private TitanQuadEncoder rightEnc, leftEnc, glideEnc, liftEnc;

    private double speedRpid, speedLpid, speedBpid;
    private double rightLast, leftLast = 0;
 
    public static double posX, posY;

    private double liftSpeed, glideSpeed;

    private MedianFilter sharpRightFilter, sharpLeftFilter, sonicRightFilter, sonicBackFilter;
    private MeanFilter rightMotorMeanFilter, leftMotorMeanFilter, glideMotorMeanFilter, liftMotorMeanFilter;
    private Ultrasonic sonicRight, sonicBack;
    private AnalogInput sharpRight, sharpLeft;

    private DigitalOutput redLED, greenLED;
    // private double confIk = 1.2f;
    private AHRS gyro;

    private Servo grip, gripRotate, mainRotate;

    private Encoder limitSwitchLift, limitSwitchGlide, startButton, EMS;

    public boolean initLift, initGlide, glideReachedPos, glideStop, finish = false;
    private boolean flag = true;

    // Отключение ПИДов
    private boolean usePIDForMotors = true; 

    private final PID rightPID = new PID(0.15, 0.095, 0.0001, -100, 100); // Настройка ПИДа правого мотора 
    private final PID leftPID = new PID(0.15, 0.095, 0.0001, -100, 100); // Настройка ПИДа левого мотора
    private final PID liftPID = new PID(0.35, 0.065, 0.0001, -100, 100); // Настройка ПИДа лифта
    private final PID glidePID = new PID(0.35, 0.065, 0.0001, -100, 100); // Настройка ПИДа 
                                         // 0.15, 0.095, 0.0001, -100, 100
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
    public double glideMotorSpeedThread = 0;

    private double[][] speedForLift = { { 0, 20, 90, 150, 250, 350, 400, 500, 600 },
                                         { 0, 4, 10, 16, 27, 39, 46, 53, 60 } }; 
    
    private double[][] speedForGlide = { { 0, 20, 90, 150, 250, 350, 400, 500, 600 },
                                         { 0, 4, 10, 16, 27, 39, 46, 53, 60 } }; 

    private static final double[][] arrOfPosForGlide = { { -1, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 },
                                             { 0, 10, 300, 500, 700, 900, 1200, 1350, 1500, 1700, 2000, 2600 } };

    private static final double[][] arrOfPosForLift = { { -1, 0, 10, 20, 30, 40, 50, 60, 70, 80, 90, 100 }, 
                                             { 0, 10, 300, 600, 800, 1100, 1500, 1900, 2300, 2700, 3000, 3300 } };

    private static final double[][] arrForGlide = { { -300, -150, -100, -50, 50, 100, 150, 300 }, 
                                                    { -80, -50, -25, -5, 5, 25, 50, 80 } };
    
    private static final double[][] arrForLift = { { -350, -200, -100, -20, -8, 0, 8, 100, 200, 350 } ,
                                                    { -72, -48, -24, -12, -8, 0, 13, 40, 70, 75 } };

    public Training()
    {
        rightMotor = new TitanQuad(42, 2);
        leftMotor = new TitanQuad(42, 3); 
        glideMotor = new TitanQuad(42, 0); 
        glideMotor.setInverted(true);
        liftMotor = new TitanQuad(42, 1); 

        rightEnc = new TitanQuadEncoder(rightMotor, 2, 1);
        rightEnc.setReverseDirection();
        leftEnc = new TitanQuadEncoder(leftMotor, 3, 1);
        glideEnc = new TitanQuadEncoder(glideMotor, 0, 1);
        liftEnc = new TitanQuadEncoder(liftMotor, 1, 1);

        sharpRight = new AnalogInput(1);
        sharpLeft = new AnalogInput(0);

        // sonicBack = new Ultrasonic(9, 8);
        // sonicRight = new Ultrasonic(11, 10);

        sharpRightFilter = new MedianFilter(4);
        sharpLeftFilter = new MedianFilter(4);

        sonicRightFilter = new MedianFilter(6);
        sonicBackFilter = new MedianFilter(6);

        redLED = new DigitalOutput(13);
        greenLED = new DigitalOutput(12);

        grip = new Servo(9);
        gripRotate = new Servo(8);
        mainRotate = new Servo(7);
        
        limitSwitchLift = new Encoder(2, 3);
        limitSwitchGlide = new Encoder(6, 7);
        startButton = new Encoder(4, 5);
        EMS = new Encoder(0, 1);

        gyro = new AHRS();

                // Поток для c anal
        new Thread( () -> {
            while(!Thread.interrupted())
            {
                try
                    {
                    // Инициализируем СМО
                    if (firstInitForGlide && !firstInitForGlideDone) {
                        firstInitForGlideDone = initForGlide();
                        if(firstInitForGlideDone) {
                            setGripRotateServoValue(32);
                            setGripServoValue(15);
                            setMainRotateServoValue(225);
                            
                        }
                        } else {
                            if (firstInitForGlideDone) {
                                if (firstInitForLift && !firstInitForLiftDone) {
                                    firstInitForLiftDone = initForLift();
                                } else {
                                    if(firstInitForGlideDone && firstInitForLiftDone) {
                                        glideReachedPos = true; //glideToMovePos(50)
                                        if(glideReachedPos) {
                                            successInit = true;
                                        }
                                    }  
                                }   
                            }
                        }
                    
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
                    if (getEMSButton()) {
                        setLeftMotorSpeed(0.0, true);
                        setRightMotorSpeed(0.0, true);
                        setLiftMotorSpeed(0.0, true);
                        setGlideMotorSpeed(0.0, true);
                        try {
                            mainRotate.setDisabled();
                            gripRotate.setDisabled();
                            grip.setDisabled();
                        } catch (Exception e) {
                            System.out.println("Pizdec Servakam");
                        }

                    } else {
                        setLeftMotorSpeed(leftMotorSpeedThread, usePIDForMotors);
                        setRightMotorSpeed(rightMotorSpeedThread, usePIDForMotors);
                        setLiftMotorSpeed(liftMotorSpeedThread, usePIDForMotors);
                        setGlideMotorSpeed(glideMotorSpeedThread, usePIDForMotors);
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

    /**
     * Метод для инициализации выдвижного механизма
     */
    private boolean initForGlide() {
        try {
            if (getLimitSwitchGlide()) {
                resetGlideEncoder();
                glideMotorSpeedThread = 0;
                return true;
            } else {
                glideMotorSpeedThread = 60;
            }
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return true;
        }
        
    }

    /**
     * Метод для инициализации лифта
     */
    private boolean initForLift() {
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
     * Метод для управления позицией выдвижного механизма
     * @param pos - позиция, на которую выдвижной механизм должен подвинуться
     */
    public boolean glideToMovePos(double pos) {

        double nowPos = getGlideEncoder();
        double encPos = Function.TransitionFunction(pos, arrOfPosForGlide); 
        double speed = Function.TransitionFunction(nowPos - encPos, arrForGlide); 
        boolean glideStop = Function.BooleanInRange(nowPos - encPos, -5, 5); 

        if (getLimitSwitchGlide() && speed > 0) {
            speed = 0;
            resetGlideEncoder();
            return true; 
        } else if (speed < 0 && nowPos < -2050) {
            glideMotorSpeedThread = 0;
            return true; 
        } else if (glideStop && !getLimitSwitchGlide()) {
            glideMotorSpeedThread = 0;
            return true; 
        } else {
            glideMotorSpeedThread = speed;
            return false;
        }  
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
            SmartDashboard.putNumber("liftCheck", 1);
            return true; 
        } else if (speed < 0 && nowPos < -3000) {
            liftMotorSpeedThread = 0;
            SmartDashboard.putNumber("liftCheck", 2);
            return true; 
        } else if (liftStop && !getLimitSwitchLift()) {
            liftMotorSpeedThread = 0;
            SmartDashboard.putNumber("liftCheck", 3);
            return true; 
        } else {
            liftMotorSpeedThread = speed;
            SmartDashboard.putNumber("liftCheck", 5);
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
     * @return Значение с энкодера выдвижного механизма
     */
    public double getGlideEncoder(){
        return glideEnc.getEncoderDistance();
    }

    /**
     * @return значение с энкодера мотора лифта
     */
    public double getLiftEncoder() {
        return liftEnc.getEncoderDistance();
    }
    
    /**
     * Сброс энкодера мотора выдвижного механизма
     */
    public void resetGlideEncoder(){
        glideEnc.reset();;
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
        return limitSwitchLift.getDistance() == -1 || limitSwitchLift.getDistance() == 2;
    }

    /**
     * Опрос концевого выключателя выдвижного механизма
     * @return Нажатие на концевик
     */
    public boolean getLimitSwitchGlide(){
        return limitSwitchGlide.getDistance() == -1 || limitSwitchGlide.getDistance() == 2;
    }

    /**
     * Опрос кнопки экстренной остановки
     * @return Нажатие на кнопку
     */
    public boolean getEMSButton(){
        return EMS.getDistance() == -1 || EMS.getDistance() == 2;
    }

    /**
     * Опрос кнопки старта
     * @return Нажатие на кнопку
     */
    public boolean getStartButton(){
        return startButton.getDistance() == 2 || startButton.getDistance() == -1;
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
            rightPID.calculate(rightEnc.getSpeed(), speed);
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
            liftPID.calculate(-liftEnc.getSpeed(), speed);
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
     * Управление мотором выдвижного механизма
     * @param speed - скорость в диапазоне от -100 до 100
     * @param withPID - подключает ПИДы к мотору выдвижного механизма
     */
    public void setGlideMotorSpeed(double speed, boolean withPID) {
        if (speed == 0.0) {
            glidePID.reset();
            glideMotor.set(0);
        } else {
            glidePID.calculate(glideEnc.getSpeed(), speed);
            if (withPID) {
                glideMotor.set(glidePID.getOutput());
                // glideMotor.set(Function.getLimitedValue(glidePID.getOutput(), -0.15, 0.15));
            } else {
                glideMotor.set(speed / 100);
            }
        }
        SmartDashboard.putNumber("outGlideMotorSpeed", speed); 
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
     * Возвращает значение с правого ультразвукового датчика
     * @return значение с правого ультразвукового датчика в миллиметрах
     */
    public double getRightSonicDistance(){
        try{
            sonicRight.ping();
            Timer.delay(0.005);
            return sonicRightFilter.Filter(sonicRight.getRangeMM() / 10);
        }catch (Exception e){
            return 0;
        }
    }
    /**
     * Возвращает значение с левого инфракрасного датчика
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
            grip.setAngle(value);
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
            gripRotate.setAngle(value);
        } catch (Exception e) {
            System.out.println("Pizdes servaky setGripRotateServoValue");
        }
        
    }

    /**
     * Устанавливает угол поворота сервомотора поворота стрелы 
     * @param value - значение угла для установки на сервомотор поворота стрелы
     */
    public void setMainRotateServoValue(int value) {
        try {
            mainRotate.setAngle(value);
        } catch (Exception e) {
            System.out.println("Pizdes servaky setMainRotateServoValue");
        }
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

    @Override
    public void periodic()
    {
        // ENCODERS ------------------------------------------------------
        SmartDashboard.putNumber("rightEnc", getRightEncoder());
        SmartDashboard.putNumber("leftEnc", getLeftEncoder());
        SmartDashboard.putNumber("glideEnc", getGlideEncoder());
        SmartDashboard.putNumber("liftEnc", getLiftEncoder());

        SmartDashboard.putNumber("speedRightMotor", rightEnc.getSpeed());
        SmartDashboard.putNumber("speedLeftMotor", leftEnc.getSpeed());
        SmartDashboard.putNumber("speedLiftotor", liftEnc.getSpeed());

        SmartDashboard.putNumber("posZ", getLongYaw());

        // SENSORS -------------------------------------------------------
        SmartDashboard.putNumber("sharpRight", getRightSharpDistance());
        SmartDashboard.putNumber("sharpLeft", getLeftSharpDistance());
        SmartDashboard.putNumber("sonicRight", getRightSonicDistance());
        SmartDashboard.putNumber("sonicBack", getBackSonicDistance());
        SmartDashboard.putNumber("yaw", getYaw());

        // BUTTONS -------------------------------------------------------
        SmartDashboard.putBoolean("startButton", getStartButton());
        SmartDashboard.putBoolean("EMS", getEMSButton());
        SmartDashboard.putNumber("EMS_number", EMS.getDistance());
        SmartDashboard.putBoolean("initlift", initLift);
        SmartDashboard.putBoolean("limitLift", getLimitSwitchLift());
        SmartDashboard.putBoolean("limitGlide", getLimitSwitchGlide());
        // test
        SmartDashboard.putBoolean("end", finish);
    }
}