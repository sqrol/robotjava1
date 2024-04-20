package frc.robot.subsystems;

public class PID {
    private boolean isFirstCall = true;
    private double kP, kI, kD, lowerLimit, upperLimit;

    public PID(double kP, double kI, double kD, double lowerLimit, double upperLimit) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
        this.lowerLimit = lowerLimit;
        this.upperLimit = upperLimit;
    }

    private double errorP;
    private double errorI;
    private double errorD;

    private double output;

    public void calculate(double input, double setPoint) {
        if (isFirstCall) {
            errorP = 0;
            errorI = 0;
            errorD = 0;
            isFirstCall = false;
        } else {
            errorP = (setPoint - input)*kP;
            errorI += errorP*kI;
            errorD += -errorI*kD;
        }
        output = Function.InRange((errorP + errorI + errorD),lowerLimit, upperLimit) / 100;
    }

    public double getOutput() {
        return output;
    }
    public void reset() {
        this.isFirstCall = true; 
    }

}