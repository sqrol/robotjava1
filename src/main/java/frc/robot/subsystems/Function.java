package frc.robot.subsystems;

public class Function {
    public static float TransitionFunction(float value, float[][] values) {
        float result = 0;
        float max_o;
        float max_i;
        float min_o;
        float min_i;
        int minus = 1;
        if (value < 0) {
          minus = -1;
          value = Math.abs(value);
        }
        if (value >= values[0][values[0].length - 1])
          result = values[1][values[1].length - 1];
        else {
          for (int i = 0; i < values[0].length; i++) {
    
            if (value >= values[0][i] && value <= values[0][i + 1]) {
              min_i = values[0][i + 1];
              max_i = values[0][i];
              min_o = values[1][i + 1];
              max_o = values[1][i];
    
              if (value == values[0][i])
                result = values[1][i];
              else {
                result = min_o + (((max_o - min_o) * ((Math.abs(value) - min_i) * 100 / (max_i - min_i))) / 100);
              }
            }
          }
        }
        if (minus < 0) {
          result *= -1;
        }
        return result;
      }

      public static boolean BooleanInRange(float value, float min, float max) {
        return value >= min && value <= max;
      }
    
      public static float InRange(float value, float min, float max) {
        if (value > max) {
          value = max;
        } else if (value < min) {
          value = min;
        }
        return value;
      }

      public static int axis(float x, float y, float curX, float curY) {
        float xDist = 0;
        if (Math.abs(x - curX) != 0) {
          xDist = Math.abs(x - curX);
        }
        float yDist = Math.abs(y - curY);
        if (Function.BooleanInRange(yDist / xDist, 0.75f, 1.38f) || (xDist < 150 && yDist < 150)) {
        // if (Function.BooleanInRange(yDist / xDist, 0.75f, 2.5f) || (xDist < 150 && yDist < 150)) {
    
          return 0;
        } else {
          if (yDist / xDist > 1) {
            return 1;
          } else
            return 2;
        }
    
      }
}
