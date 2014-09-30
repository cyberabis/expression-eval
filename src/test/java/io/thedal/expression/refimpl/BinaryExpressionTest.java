package io.thedal.expression.refimpl;

import static org.junit.Assert.*;
import io.thedal.expression.ExpressionExecutor;
import io.thedal.expression.refimpl.BinaryExpression;
import io.thedal.expression.refimpl.DummyFunctionFactory;

import org.junit.Test;

public class BinaryExpressionTest {

  @Test
  public void test() {
    String stringExpression = "((id.Double = 461506965680951296) "
        + "AND ((user.screen_name.String) = jileyssex)) OR "
        + "((in_reply_to_screen_name.String) = \"smiley ugly  bieber15\")";
    BinaryExpression be = new BinaryExpression(stringExpression);
    System.out.println("Postorder in reverse: " + be.getPostOrder());
    ExpressionExecutor.execute(be, new DummyFunctionFactory());
  }

}
