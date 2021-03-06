package io.thedal.expression.refimpl;

import io.thedal.expression.Expression;
import io.thedal.expression.ExpressionNode;
import io.thedal.expression.ExpressionNodeType;
import io.thedal.expression.Operation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BinaryExpression implements Expression {

  private BinaryExpressionNode rootNode;
  private final Stack execStack = new Stack();
  private final Stack mainStack;

  public BinaryExpression(String stringExpression) {
    tokenize(stringExpression);
    mainStack = getPostOrder();
  }

  @Override
  public ExpressionNode getRootNode() {
    return rootNode;
  }

  private void tokenize(String stringExpression) {
    System.out.println("Expression received: " + stringExpression);

    // Split by separator
    List<String> tokens1 = Arrays.asList(stringExpression.split(" "));
    System.out.println("Tokens1: " + tokens1);

    // Merge tokens that are actually sentences
    List<String> tokens2 = new ArrayList<String>();
    String mergedToken = "";
    for (String t : tokens1) {
      if (t.indexOf('"') > -1) {
        // Only one quote,
        if (t.indexOf('"') == t.lastIndexOf('"')) {
          if (mergedToken.equals(""))
            mergedToken = mergedToken + t;
          else
            mergedToken = mergedToken + " " + t;
        } else {
          // Two quotes means complete t, push to tokens2
          tokens2.add(t);
        }
      } else {
        // No quotes
        if (mergedToken.equals(""))
          tokens2.add(t);
        else
          mergedToken = mergedToken + " " + t;
      }
      // If merged token is complete, push to tokens2
      if ((!mergedToken.equals(""))
          && (mergedToken.indexOf('"') != mergedToken.lastIndexOf('"'))) {
        tokens2.add(mergedToken);
        mergedToken = "";
      }
    }
    System.out.println("Tokens2: " + tokens2);

    // Split by brackets
    List<String> tokens3 = new ArrayList<String>();

    boolean iterate = true;
    while (iterate) {
      iterate = false;
      for (String t : tokens2) {
        int ind1 = t.indexOf('(');
        int ind2 = t.indexOf(')');
        if ((ind1 > -1) && (t.length() > 1)) {
          tokens3.add("(");
          tokens3.add(t.substring(1));
          if (t.substring(1).indexOf('(') > -1
              || (t.substring(1).indexOf(')') > -1))
            iterate = true;
        } else if ((ind2 > -1) && (t.length() > 1)) {
          tokens3.add(t.substring(0, t.length() - 1));
          tokens3.add(")");
          if (t.substring(0, t.length() - 1).indexOf('(') > -1
              || (t.substring(0, t.length() - 1).indexOf(')') > -1))
            iterate = true;
        } else
          tokens3.add(t);
      }
      if (iterate) {
        tokens2 = tokens3;
        tokens3 = new ArrayList<String>();
      }

    }
    System.out.println("Tokens3: " + tokens3);

    // Form the tree
    Stack stack = new Stack();
    Object lOpr = null;
    Object rOpr = null;
    String op = null;
    BinaryExpressionNode newNode = null;
    for (String t : tokens3) {

      if (t.equals("(")) {
        stack.push(t);
        lOpr = null;
        rOpr = null;
        op = null;
      }
      else if (t.equals(")")) {
        rOpr = stack.pop();
        op = (String) stack.pop();
        if (op.equals("(")) {
          // implies dummy bracket.
          stack.push(rOpr);
          lOpr = rOpr;
          op = null;
          rOpr = null;
          continue;
        }
        lOpr = stack.pop();
        stack.pop(); //Pop out the open bracket, verify if this holds good for all
        newNode = new BinaryExpressionNode(ExpressionNodeType.OPERATOR, op);
        newNode.setLeftNode(lOpr.getClass().isInstance(newNode) ? (BinaryExpressionNode) lOpr
                : new BinaryExpressionNode(ExpressionNodeType.OPERAND, lOpr));
        newNode.setRightNode(rOpr.getClass().isInstance(newNode) ? (BinaryExpressionNode) rOpr
                : new BinaryExpressionNode(ExpressionNodeType.OPERAND, rOpr));
        stack.push(newNode);
        lOpr = newNode;
        rOpr = null;
        op = null;

      } else {
        // We have an op/opr
        if (lOpr == null) {
          lOpr = t;
          stack.push(t);
        } else if (op == null) {
          op = t;
          stack.push(t);
        } else {
          rOpr = t;
          stack.push(t);
          lOpr = null;
          rOpr = null;
          op = null;
        }
      }
    } //End of for loop
    
    // Construct tree from stack
    if (stack.isEmpty()) {
      rootNode = newNode;
    } else {
      lOpr = null;
      rOpr = null;
      op = null;
      newNode = null;
      Stack invStack = new Stack();
      while (!stack.isEmpty()) {
        invStack.push(stack.pop());
      }
      while (!invStack.isEmpty()) {
        if (newNode != null)
          lOpr = newNode;
        if (lOpr == null)
          lOpr = invStack.pop();
        else if (op == null)
          op = (String) invStack.pop();
        else {
          rOpr = invStack.pop();
          newNode = new BinaryExpressionNode(ExpressionNodeType.OPERATOR, op);
          newNode.setLeftNode(lOpr.getClass().isInstance(newNode) ? (BinaryExpressionNode) lOpr
                  : new BinaryExpressionNode(ExpressionNodeType.OPERAND, lOpr));
          newNode
              .setRightNode(rOpr.getClass().isInstance(newNode) ? (BinaryExpressionNode) rOpr
                  : new BinaryExpressionNode(ExpressionNodeType.OPERAND, rOpr));
          lOpr = null;
          op = null;
          rOpr = null;
        }
      }
      if (lOpr != null)
        rootNode = (BinaryExpressionNode) lOpr;
      else
        rootNode = newNode;
    }

  }

  public Stack getPostOrder() {
    Stack postorder = new Stack();
    if (rootNode != null) {
      postorderTraverse(rootNode, postorder);
    }
    // reverse the stack and return
    Stack postorderRev = new Stack();
    while (!postorder.isEmpty())
      postorderRev.push(postorder.pop());
    return postorderRev;
  }

  private void postorderTraverse(BinaryExpressionNode node, Stack postorder) {
    BinaryExpressionNode left = node.getLeftNode();
    BinaryExpressionNode right = node.getRightNode();
    if (left != null)
      postorderTraverse(left, postorder);
    if (right != null)
      postorderTraverse(right, postorder);
    postorder.push(node.getValue());
  }

  @Override
  public boolean isFullyExecuted() {
    if ((mainStack.isEmpty()) && (execStack.size() == 1))
      return true;
    else
      return false;
  }

  @Override
  public Operation getNextOperation() {
    // TODO Handle error cases
    Object leftOpr = null;
    Object rightOpr = null;
    String op = null;
    Operator o = null;
    leftOpr = mainStack.pop();
    if ((leftOpr instanceof String) && (OperatorUtil.checkOperator((String)leftOpr) != null) ) {
      // This implies 2 operands are in the op stack
      o = OperatorUtil.checkOperator((String) leftOpr);
      rightOpr = execStack.pop();
      leftOpr = execStack.pop();
      return new Operation(o, new Object[] { leftOpr, rightOpr });
    } else {
        rightOpr = mainStack.pop();
        if ((rightOpr instanceof String) && (OperatorUtil.checkOperator((String) rightOpr) != null) ) {
        // This implies left operand is in the op stack
          o = OperatorUtil.checkOperator((String) rightOpr);
          rightOpr = leftOpr;
        leftOpr = execStack.pop();
          return new Operation(o, new Object[] { leftOpr, rightOpr });
        } else {
          // After two operands it has to be an operator
          op = (String) mainStack.pop();
          o = OperatorUtil.checkOperator(op);
          return new Operation(o, new Object[] { leftOpr, rightOpr });
        }
      }
  }

  @Override
  public void storeLastOperationOutput(Object output) {
    execStack.push(output);
  }

  @Override
  public Object getLastOperationOutput() {
    if (execStack.isEmpty())
      return null;
    else
      return execStack.pop();
  }

}
