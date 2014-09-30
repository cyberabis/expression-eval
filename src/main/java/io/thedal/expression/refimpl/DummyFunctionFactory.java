package io.thedal.expression.refimpl;

import io.thedal.expression.Function;
import io.thedal.expression.FunctionFactory;
import io.thedal.expression.exceptions.NoFunctionFound;

public class DummyFunctionFactory implements FunctionFactory {

  @Override
  public Function getFunction(Enum operator, Object[] operands)
      throws NoFunctionFound {
    return new DummyFunction();
  }

}
