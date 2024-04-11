package frc.robot.subsystems;

import org.opencv.core.Mat;
import org.opencv.core.Point;

import edu.wpi.cscore.CvSink;
import edu.wpi.cscore.CvSource;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class JavaCam implements Runnable
{
    private UsbCamera camera;
    private CvSink cvSink;
    public CvSource ripeApple;
    public CvSource ripePear;

    public int nowTask = 0;
    public int nowResult = 0; 
    public String colorCube, colorStand;
    public boolean alignCamera = false;

    @Override
    public void run() {
        // TODO Auto-generated method stub
        SmartDashboard.putNumber("RED1", 0.0);
        SmartDashboard.putNumber("RED2", 0.0);
        SmartDashboard.putNumber("GREEN1", 0.0);
        SmartDashboard.putNumber("GREEN2", 0.0);
        SmartDashboard.putNumber("BLUE1", 0.0);
        SmartDashboard.putNumber("BLUE2", 0.0);

        camera = CameraServer.getInstance().startAutomaticCapture(); // Находим доступные камеры и подсасывам его
        camera.setResolution(640, 480); // Разрешение
        camera.setFPS(30); // Частота кадров
        cvSink = CameraServer.getInstance().getVideo(camera);

        ripeApple = CameraServer.getInstance().putVideo("ripeApple", 640, 480);
        ripePear = CameraServer.getInstance().putVideo("ripePear", 640, 480);

        while(true) {
            try {
                final Mat source = new Mat();
                if (nowTask == 1) {
                    CheckApple(source);
                }
                if (nowTask == 2) {

                }
            } catch (final Exception e) {
                DriverStation.reportError("Cam problem!", false);
                e.printStackTrace();
            }
        }
    }

    public int CheckApple(final Mat orig) // Распознавание яблока
    {
        // Нужно заменить массивом 
        final double red1 = SmartDashboard.getNumber("RED1", 0);
        final double red2 = SmartDashboard.getNumber("RED2", 0);
 
        final double green1 = SmartDashboard.getNumber("GREEN1", 0);
        final double green2 = SmartDashboard.getNumber("GREEN2", 0);

        final double blue1 = SmartDashboard.getNumber("BLUE1", 0);
        final double blue2 = SmartDashboard.getNumber("BLUE2", 0);

        final Point redPoint = new Point(red1, red2);
        final Point greenPoint = new Point(green1, green2);
        final Point bluePoint = new Point(blue1, blue2);

        final Mat threshImage = Viscad2.colorThreshold(orig, redPoint, greenPoint, bluePoint); // Пороговая обработка кадра
        final int imageArea = Viscad2.ImageTrueArea(threshImage); // Определение площади выделенной области на обработанном кадре
        ripeApple.putFrame(threshImage); // Передача обработанного кадра на выходной поток

        threshImage.release();

        if (imageArea > 6000) {
            return 1;
        } else {
            return 0;
        }
    }

}