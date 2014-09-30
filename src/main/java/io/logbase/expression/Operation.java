package io.logbase.expression;

public class Operation {
  Enum operator;
  Object[] operands;

  public Operation(Enum operator, Object[] operands) {
    this.operands = operands;
    this.operator = operator;
  }

  public Enum getOperator() {
    return operator;
  }

  public void setOperator(Enum operator) {
    this.operator = operator;
  }

  public Object[] getOperands() {
    return operands;
  }

  public void setOperands(Object[] operands) {
    this.operands = operands;
  }
}
