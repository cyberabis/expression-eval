package io.logbase.expression.refimpl;

import io.logbase.expression.Function;
import io.logbase.expression.FunctionFactory;
import io.logbase.expression.exceptions.NoFunctionFound;

public class DummyFunctionFactory implements FunctionFactory {

  @Override
  public Function getFunction(Enum operator, Object[] operands)
      throws NoFunctionFound {
    return new DummyFunction();
  }

}
