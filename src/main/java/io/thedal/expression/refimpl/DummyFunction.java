package io.thedal.expression.refimpl;

import io.thedal.expression.Function;
import io.thedal.expression.exceptions.FunctionValidationFailed;

public class DummyFunction implements Function {

  @Override
  public Object execute(Object[] operands) throws FunctionValidationFailed {
    System.out.println("Dummy function executing for: " + operands[0] + " | "
        + operands[1]);
    return "dummy output";
  }

}
