package net.lessons.quadequation;

public interface IQuadraticEquation{
  // квадратное уравнение - это уравнение вида "a * x^2 + b * x + c = 0"
  double[] solve(double a, double b, double c) throws Exception;
  double[] solveQE(double a, double b, double c) throws Exception;
  double[] solveQE_zeroC(double a, double b);
  double[] solveQE_zeroB(double a, double c) throws Exception;
}
