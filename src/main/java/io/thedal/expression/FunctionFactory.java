package io.thedal.expression;

import io.thedal.expression.exceptions.NoFunctionFound;

public interface FunctionFactory {

  public Function getFunction(Enum operator, Object[] operands)
      throws NoFunctionFound;

}
