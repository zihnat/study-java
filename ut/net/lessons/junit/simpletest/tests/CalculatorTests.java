package net.lessons.junit.simpletest.tests;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import net.lessons.junit.simpletest.Calculator;

public class CalculatorTests{

  @Test
  public void Sum_1plus1_res7(){
    Calculator calc = new Calculator();
    int res = calc.intSum(1,1);
    assertEquals(2, res);
  }

}
