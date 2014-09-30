package io.thedal.expression;

import io.thedal.expression.exceptions.FunctionValidationFailed;

public interface Function {

  public Object execute(Object[] operands) throws FunctionValidationFailed;

}
