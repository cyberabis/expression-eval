package io.thedal.expression;

import io.thedal.expression.exceptions.FunctionValidationFailed;
import io.thedal.expression.exceptions.NoFunctionFound;

public class ExpressionExecutor {

  public static Object execute(Expression expression, FunctionFactory ff) {

    Operation operation = null;
    Function function = null;
    Object output = null;
    while (!expression.isFullyExecuted()) {
      operation = expression.getNextOperation();
      try {
        function = ff.getFunction(operation.getOperator(),
            operation.getOperands());
        output = function.execute(operation.getOperands());
        expression.storeLastOperationOutput(output);
      } catch (NoFunctionFound e) {
        e.printStackTrace();
      } catch (FunctionValidationFailed e) {
        e.printStackTrace();
      }
    }
    return expression.getLastOperationOutput();
  }

}
