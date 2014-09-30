package io.thedal.expression.refimpl;

import io.thedal.expression.ExpressionNode;
import io.thedal.expression.ExpressionNodeType;

public class BinaryExpressionNode implements ExpressionNode {

  private ExpressionNodeType type;
  private Object value;
  private BinaryExpressionNode leftNode;
  private BinaryExpressionNode rightNode;

  public BinaryExpressionNode(ExpressionNodeType type, Object value) {
    this.type = type;
    this.value = value;
  }

  public void setLeftNode(BinaryExpressionNode leftNode) {
    this.leftNode = leftNode;
  }

  public void setRightNode(BinaryExpressionNode rightNode) {
    this.rightNode = rightNode;
  }

  @Override
  public ExpressionNodeType getType() {
    return type;
  }

  public void setType(ExpressionNodeType type) {
    this.type = type;
  }

  public Object getValue() {
    return value;
  }

  public void setValue(Object value) {
    this.value = value;
  }

  public BinaryExpressionNode getLeftNode() {
    return leftNode;
  }

  public BinaryExpressionNode getRightNode() {
    return rightNode;
  }

  @Override
  public ExpressionNode[] getChildren() {
    ExpressionNode[] children = null;
    if (rightNode != null)
      children = new ExpressionNode[] { leftNode, rightNode };
    else if (leftNode != null)
      children = new ExpressionNode[] { leftNode };
    return children;
  }

}
