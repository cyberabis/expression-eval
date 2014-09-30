package io.thedal.expression;

public interface Expression {

  public ExpressionNode getRootNode();

  public boolean isFullyExecuted();

  public Operation getNextOperation();

  public void storeLastOperationOutput(Object output);

  public Object getLastOperationOutput();

}
