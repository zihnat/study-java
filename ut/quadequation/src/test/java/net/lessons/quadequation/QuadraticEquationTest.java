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
  public void test_solveQE_zeroC1(){
    double[] res = qe.solveQE_zeroC(1, 1);
    Arrays.sort(res);
    assertEquals(-1, res[0], 0.01);
    assertEquals(0, res[1], 0.01);
  }

  @Test
  public void test_solveQE_zeroC2(){
    double[] res = qe.solveQE_zeroC(1, -1);
    Arrays.sort(res);
    assertEquals(0, res[0], 0.01);
    assertEquals(1, res[1], 0.01);
  }

  @Test
  public void test_solveQE_zeroB1(){
    try{
      double[] res = qe.solveQE_zeroB(1.0, -1.0);
      Arrays.sort(res);
      assertEquals(-1.0, res[0], 0.01);
      assertEquals(1.0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solveQE_zeroB2(){
    try{
      double[] res = qe.solveQE_zeroB(1.0, -4.0);
      Arrays.sort(res);
      assertEquals(-2.0, res[0], 0.01);
      assertEquals(2.0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solveQE(){
    try{
      double[] res = qe.solveQE(3, 6 , -24);
      Arrays.sort(res);
      assertEquals(-4.0, res[0], 0.01);
      assertEquals(2.0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solve1(){
    try{
      double[] res = qe.solve(3.0, 6.0, -24.0);
      Arrays.sort(res);
      assertEquals(-4.0, res[0], 0.01);
      assertEquals(2.0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solve2(){
    try{
      double[] res = qe.solve(0.0, 6.0, -24.0);
      fail("Exception expected");
    }catch(Exception e){}
  }

  @Test
  public void test_solve_zeroC(){
    try{
      double[] res = qe.solve(1.0, 1.0, 0.0);
      Arrays.sort(res);
      assertEquals(-1.0, res[0], 0.01);
      assertEquals(0.0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }

  @Test
  public void test_solve_zeroB(){
    try{
      double[] res = qe.solve(1.0, 0.0, -1.0);
      Arrays.sort(res);
      assertEquals(-1.0, res[0], 0.01);
      assertEquals(1.0, res[1], 0.01);
    }catch(Exception e){
      fail("Wgot exception");
    }
  }
}
