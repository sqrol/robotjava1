package frc.robot.subsystems;

import com.studica.frc.Servo;
import com.studica.frc.ServoContinuous;
import com.studica.frc.TitanQuad;
import com.studica.frc.TitanQuadEncoder;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants;
import frc.robot.Main;
import frc.robot.Maths.Common.PID;

public class MotorController implements Runnable {

    public static double motorsUpdateTime; 
    public double previousDegrees = 0;

    private final TitanQuad MOTOR_RIGHT = new TitanQuad(Constants.TITAN_ID, Constants.MOTOR_RIGHT);
    private final TitanQuad MOTOR_LEFT = new TitanQuad(Constants.TITAN_ID, Constants.MOTOR_LEFT);
    private final TitanQuad MOTOR_ROTATE = new TitanQuad(Constants.TITAN_ID, Constants.MOTOR_ROTATE);
    private final TitanQuad MOTOR_LIFT = new TitanQuad(Constants.TITAN_ID, Constants.MOTOR_LIFT); 

    private final TitanQuadEncoder ENC_RIGHT = new TitanQuadEncoder(MOTOR_RIGHT, Constants.ENC_RIGHT, Constants.DIST_PER_TICK);
    private final TitanQuadEncoder ENC_LEFT = new TitanQuadEncoder(MOTOR_LEFT, Constants.ENC_LEFT, Constants.DIST_PER_TICK);
    private final TitanQuadEncoder ENC_ROTATE = new TitanQuadEncoder(MOTOR_ROTATE, Constants.ENC_ROTATE, Constants.DIST_PER_TICK);
    private final TitanQuadEncoder ENC_LIFT = new TitanQuadEncoder(MOTOR_LIFT, Constants.ENC_LIFT, Constants.DIST_PER_TICK);

    private final PID PID_RIGHT = new PID(0.051, 0.43, 0.0, -100, 100); 
    private final PID PID_LEFT = new PID(0.051, 0.43, 0.0, -100, 100); 
    private final PID PID_ROTATE = new PID(0.051, 0.43, 0.0, -100, 100); 
    private final PID PID_LIFT = new PID(0.051, 0.43, 0.0, -100, 100);

    private final Servo SERVO_GRAB = new Servo(Constants.SERVO_GRAB);
    private final Servo SERVO_GRIP_ROTATE = new Servo(Constants.SERVO_GRIP_ROTATE);
    private final ServoContinuous SERVO_GLIDE = new ServoContinuous(Constants.SERVO_GLIDE);

    double liftCurrentPosition = 0; // Первоначальная позиция лифта

    @Override
    public void run() {
        while (!Thread.interrupted()) {
            double startTime = Timer.getFPGATimestamp();
            try {

                // if(Main.motorControllerMap.get("resetPID") == 1.0) {
                //     resetPIDRight();
                //     resetPIDLeft();
                //     resetPIDRotate();
                //     resetPIDLift();
                //     Main.motorControllerMap.put("resetPID", 0.0);
                // }

                // if (Main.motorControllerMap.get("resetEncs") == 1.0) {
                //     resetEncRight();
                //     resetEncLeft();
                //     resetEncRotate();
                //     resetEncLift();
                //     Main.motorControllerMap.put("resetEncs", 0.0);
                // }

                // if(Main.motorControllerMap.get("resetEncRight") == 1.0) {
                //     resetEncRight();
                //     Main.motorControllerMap.put("resetEncRight", 0.0);
                // }

                // if(Main.motorControllerMap.get("resetEncRight") == 1.0) {
                //     resetEncRight();
                //     Main.motorControllerMap.put("resetEncRight", 0.0);
                // }

                // if(Main.motorControllerMap.get("resetEncLeft") == 1.0) {
                //     resetEncLeft();
                //     Main.motorControllerMap.put("resetEncLeft", 0.0);
                // }

                // if(Main.motorControllerMap.get("resetEncRotate") == 1.0) {
                //     resetEncRotate();
                //     Main.motorControllerMap.put("resetEncRotate", 0.0);
                // }

                // if(Main.motorControllerMap.get("resetEncLift") == 1.0) {
                //     resetEncLift();
                //     Main.motorControllerMap.put("resetEncLift", 0.0);
                // }

                // Main.motorControllerMap.put("rpmRight", ENC_RIGHT.getSpeed());
                // Main.motorControllerMap.put("rpmLeft", ENC_LEFT.getSpeed());
                // Main.motorControllerMap.put("rpmRotate", ENC_ROTATE.getSpeed());
                // Main.motorControllerMap.put("rpmLift", ENC_LIFT.getSpeed());

                // Main.motorControllerMap.put("encRight", ENC_RIGHT.getEncoderDistance()); 
                // Main.motorControllerMap.put("encLeft", ENC_LEFT.getEncoderDistance());
                // Main.motorControllerMap.put("encRotate", ENC_ROTATE.getEncoderDistance());
                // Main.motorControllerMap.put("encLift", ENC_LIFT.getEncoderDistance());

                // setAxisSpeed(Main.motorControllerMap.get("speedX"), Main.motorControllerMap.get("speedZ"));
                // setRotateMotorSpeed(Main.motorControllerMap.get("rotateSpeed"));
                // setLiftMotorSpeed(Main.motorControllerMap.get("liftSpeed"));

                // try {
                //     setServoGrab(Main.motorControllerMap.get("servoGrab"));
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
                
                // try {
                //     setServoGripRotate(Main.motorControllerMap.get("servoGripRotate"));
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }
                
                // try {
                //     setGlideServoSpeed(Main.motorControllerMap.get("glideServoSpeed"));
                // } catch (Exception e) {
                //     e.printStackTrace();
                // }

                Main.motorControllerMap.put("updateTime", motorsUpdateTime);

                Thread.sleep(20);
            } catch (Exception e) {
                System.err.println("!!!An error occurred in MotorController: " + e.getMessage());
                e.printStackTrace();
            }
            motorsUpdateTime = Timer.getFPGATimestamp() - startTime;
        }
    }

    private void setAxisSpeed(double x, double z) {
        double right = x + z;
        double left = -x + z;

        setRightMotorSpeed(right);
        setLeftMotorSpeed(left);
    }

    private void setRightMotorSpeed(double speed) {
        if (speed == 0.0) {
            PID_RIGHT.reset();
            MOTOR_RIGHT.set(0);
        } else {
            PID_RIGHT.calculate(ENC_RIGHT.getSpeed(), speed);
            Main.motorControllerMap.put("rightPID", PID_RIGHT.getOutput());
            MOTOR_RIGHT.set(Main.motorControllerMap.get("rightPID"));
        }
    }

    private void setLeftMotorSpeed(double speed) {
        if(speed == 0.0) {
            PID_LEFT.reset();
            MOTOR_LEFT.set(0);
        } else {
            PID_LEFT.calculate(ENC_LEFT.getSpeed(), speed);
            Main.motorControllerMap.put("leftPID", PID_LEFT.getOutput());
            MOTOR_LEFT.set(Main.motorControllerMap.get("leftPID"));
        }
    }

    private void setRotateMotorSpeed(double speed) {
        if (speed == 0.0) {
            PID_ROTATE.reset();
            MOTOR_ROTATE.set(0);
        } else {
            PID_ROTATE.calculate(ENC_ROTATE.getSpeed(), speed);
            Main.motorControllerMap.put("rotatePID", PID_ROTATE.getOutput());
            MOTOR_ROTATE.set(Main.motorControllerMap.get("rotatePID"));
        }
    }

    private void setLiftMotorSpeed(double speed) {
        if(speed == 0.0) {
            PID_LIFT.reset();
            MOTOR_LIFT.set(0);
        } else {
            PID_LIFT.calculate(-ENC_LIFT.getSpeed(), speed);
            Main.motorControllerMap.put("liftPID", PID_LIFT.getOutput());
            MOTOR_LIFT.set(Main.motorControllerMap.get("liftPID"));
        }
    }

    private void resetEncRight() {
        ENC_RIGHT.reset();
    }

    private void resetEncLeft() {
        ENC_LEFT.reset();
    }

    private void resetEncRotate() {
        ENC_ROTATE.reset();
    }

    private void resetEncLift() {
        ENC_LIFT.reset();
    }

    private void resetPIDRight() {
        PID_RIGHT.reset();
    }

    private void resetPIDLeft() {
        PID_LEFT.reset();
    }

    private void resetPIDRotate() {
        PID_ROTATE.reset();
    }

    private void resetPIDLift() {
        PID_LIFT.reset();
    }

    private void setServoGrab(double angle) {
        try {
            SERVO_GRAB.setAngle(angle);
        } catch (Exception e) {
            System.out.println("Error in setServoGrab");
            e.printStackTrace();
        } 
    }

    private void setServoGripRotate(double angle) {
        try {
            SERVO_GRIP_ROTATE.setAngle(angle);
        } catch (Exception e) {
            System.out.println("Error in setServoGripRotate");
            e.printStackTrace();
        }
    }

    private void setGlideServoSpeed(double speed) {
        try {
            SERVO_GLIDE.set(speed);
        } catch (Exception e) {
            System.out.println("Error in setGlideServoSpeed");
            e.printStackTrace();
        }  
    }
}