package net.lessons.quadequation;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.fail;
import java.util.Arrays;
import org.junit.Test;
import org.junit.BeforeClass;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

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
  public void test_solve_exception_not_quadratic(){
    try{
      double[] res = qe.solve(0.0, 6.0, -24.0);
      fail("Exception expected");
    }catch(Exception e){
      assertThat(e.getMessage(), is("Equation is not quadratic."));
    }
  }

  @Test
  public void test_solve_exception_complex_result(){
    try{
      double[] res = qe.solve(1.0, 1.0, 1.0);
      fail("Exception expected");
    }catch(Exception e){
      assertThat(e.getMessage(), is("Solution is complex number."));
    }
  }
}
