package io.logbase.expression;

import io.logbase.expression.exceptions.NoFunctionFound;

public interface FunctionFactory {

  public Function getFunction(Enum operator, Object[] operands)
      throws NoFunctionFound;

}
