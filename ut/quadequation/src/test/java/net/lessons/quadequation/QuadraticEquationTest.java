package net.lessons.quadequation;
import static org.junit.Assert.assertEquals;
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
  public void test_solve_a1_b1_c0(){
    try{
      double[] res = qe.solve(1, 1, 0);
      Arrays.sort(res);
      assertEquals(-1, res[0], 0.01);
      assertEquals(0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solve_a1_bm1_c0(){
    try{
      double[] res = qe.solve(1, -1, 0);
      Arrays.sort(res);
      assertEquals(0, res[0], 0.01);
      assertEquals(1, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solve_a1_b0_cm1(){
    try{
      double[] res = qe.solve(1.0, 0, -1.0);
      Arrays.sort(res);
      assertEquals(-1.0, res[0], 0.01);
      assertEquals(1.0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solve_a1_b0_cm4(){
    try{
      double[] res = qe.solve(1.0, 0, -4.0);
      Arrays.sort(res);
      assertEquals(-2.0, res[0], 0.01);
      assertEquals(2.0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solve_a3_b6_cm24(){
    try{
      double[] res = qe.solve(3, 6 , -24);
      Arrays.sort(res);
      assertEquals(-4.0, res[0], 0.01);
      assertEquals(2.0, res[1], 0.01);
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
