package frc.robot.subsystems;

import java.util.ArrayList;
import java.util.List;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

public class Viscad2 {
    public static Mat colorThreshold(Mat src, Point red, Point green, Point blue) {
        Mat hsv = new Mat();
        Mat threshImage = new Mat();
        Imgproc.cvtColor(src, hsv, Imgproc.COLOR_BGR2HSV); 
        Core.inRange(hsv, new Scalar(red.x, green.x, blue.x), new Scalar(red.y, green.y, blue.y), threshImage);

        hsv.release();
        return threshImage;
    }

    public static int ImageTrueArea(Mat src)
    {
        return Core.countNonZero(src);
    }

    public static Rect findLargestObject(Mat binaryImage) {
        // Находим контуры на бинарном изображении
        Mat hierarchy = new Mat();
        List<MatOfPoint> contours = new ArrayList<>();
        Imgproc.findContours(binaryImage, contours, hierarchy, Imgproc.RETR_EXTERNAL, Imgproc.CHAIN_APPROX_SIMPLE);

        // Находим самый большой контур по площади
        double maxArea = 0;
        Rect largestBoundingRect = new Rect();
        for (MatOfPoint contour : contours) {
            double area = Imgproc.contourArea(contour);
            if (area > maxArea) {
                maxArea = area;
                largestBoundingRect = Imgproc.boundingRect(contour);
            }
        }

        return largestBoundingRect;
    }
}