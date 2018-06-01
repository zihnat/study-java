package net.lessons.quadequation;

public interface IQuadraticEquation{
  // квадратное уравнение - это уравнение вида "a * x^2 + b * x + c = 0"
  double[] solve(double a, double b, double c) throws Exception;
}
