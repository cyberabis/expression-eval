package io.logbase.expression;

public interface ExpressionNode {

  public ExpressionNodeType getType();

  public Object getValue();

  public ExpressionNode[] getChildren();

}
