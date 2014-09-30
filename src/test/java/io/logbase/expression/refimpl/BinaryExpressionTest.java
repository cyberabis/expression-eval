package io.logbase.expression.refimpl;

import static org.junit.Assert.*;
import io.logbase.expression.ExpressionExecutor;

import org.junit.Test;

public class BinaryExpressionTest {

  @Test
  public void test() {
    String stringExpression = "((id.Double = 461506965680951296) "
        + "AND ((user.screen_name.String) = jileyssex)) OR "
        + "((in_reply_to_screen_name.String) = \"smiley ugly  bieber15\")";
    String separator = " ";
    BinaryExpression be = new BinaryExpression(stringExpression);
    System.out.println("Postorder: " + be.getPostOrder());

    ExpressionExecutor.execute(be, new DummyFunctionFactory());
  }

}
