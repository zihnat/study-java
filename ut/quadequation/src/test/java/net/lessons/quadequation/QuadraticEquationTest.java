package net.lessons.quadequation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import java.util.Arrays;
import org.junit.Test;
import org.junit.BeforeClass;

public class QuadraticEquationTest{
  private static IQuadraticEquation qe;

  @BeforeClass
  public static void initQuadraticEquation() {
    qe = new QuadraticEquation();
  }

  @Test
  public void test_solve(){
    try{
      double[] expected = {-1.0, 0.0};
      double[] res = qe.solve(1, 1, 0);
      assertArrayEquals(expected, res, 0.01);

      expected[0] = 0.0;
      expected[1] = 1.0;
      res = qe.solve(1, -1, 0);
      assertArrayEquals(expected, res, 0.01);

      expected[0] = -1.0;
      expected[1] = 1.0;
      res = qe.solve(1.0, 0, -1.0);
      assertArrayEquals(expected, res, 0.01);

      expected[0] = -2.0;
      expected[1] = 2.0;
      res = qe.solve(1.0, 0, -4.0);
      assertArrayEquals(expected, res, 0.01);

      expected[0] = -4.0;
      expected[1] = 2.0;
      res = qe.solve(3, 6 , -24);
      assertArrayEquals(expected, res, 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solve_exception_a0(){
    try{
      double[] res = qe.solve(0.0, 6.0, -24.0);
      fail("Exception expected");
    }catch(Exception e){}
  }

  @Test
  public void test_solve_exception_a1_b0_c1(){
    try{
      double[] res = qe.solve(1.0, 0.0, 1.0);
      fail("Exception expected");
    }catch(Exception e){}
  }

  @Test
  public void test_solve_exception_a1_b1_c1(){
    try{
      double[] res = qe.solve(1.0, 1.0, 1.0);
      fail("Exception expected");
    }catch(Exception e){}
  }
}
