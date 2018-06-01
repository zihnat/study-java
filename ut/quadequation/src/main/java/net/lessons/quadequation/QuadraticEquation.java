package net.lessons.quadequation;
import java.util.*;

public class QuadraticEquation implements IQuadraticEquation{
  // квадратное уравнение - это уравнение вида "a * x^2 + b * x + c = 0"
  // приведенное квадратное уравнение это квадратное уравнение в котором a = 1
  // если в квадратном уравнении коэффициент(ы) b и/или с равен(равны) нулю, то квадратное уравнение является неполным
  @Override
  public double[] solve(double a, double b, double c)
  throws Exception{
    if(a == 0){
      throw new Exception("Equation is not quadratic");
    }
    double d = Math.pow(b, 2) - 4 * a * c;
    if(d < 0){
      throw new Exception("Soulution would not be a real number! Complex numbers are not supported in class QuadraticEquation.");
    }
    double x1 = (-b - Math.sqrt(d)) / (2 * a);
    double x2 = (-b + Math.sqrt(d)) / (2 * a);
    return new double[]{x1, x2};
  }
}
