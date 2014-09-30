package io.logbase.expression;

import io.logbase.expression.exceptions.FunctionValidationFailed;

public interface Function {

  public Object execute(Object[] operands) throws FunctionValidationFailed;

}
