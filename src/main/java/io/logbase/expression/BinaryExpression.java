package io.logbase.expression;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class BinaryExpression {

  private Node rootNode;

  public BinaryExpression(String stringExpression) {
    tokenize(stringExpression);
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
    Node newNode = null;
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
        newNode = new Node("op", op);
        newNode.setLeftNode(lOpr.getClass().isInstance(newNode) ? (Node) lOpr
                : new Node("opr", lOpr));
        newNode.setRightNode(rOpr.getClass().isInstance(newNode) ? (Node) rOpr
                : new Node("opr", rOpr));
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
          newNode = new Node("op", op);
          newNode.setLeftNode(lOpr.getClass().isInstance(newNode) ? (Node) lOpr
                  : new Node("opr", lOpr));
          newNode
              .setRightNode(rOpr.getClass().isInstance(newNode) ? (Node) rOpr
                  : new Node("opr", rOpr));
          lOpr = null;
          op = null;
          rOpr = null;
        }
      }
      if (lOpr != null)
        rootNode = (Node) lOpr;
      else
        rootNode = newNode;
    }

  }

  public String getInfix() {
    // TODO
    return null;
  }

  private static class Node {
    private String type;
    private Object value;
    private Node leftNode;
    private Node rightNode;

    public Node(String type, Object value) {
      this.type = type;
      this.value = value;
    }

    public void setLeftNode(Node leftNode) {
      this.leftNode = leftNode;
    }

    public void setRightNode(Node rightNode) {
      this.rightNode = rightNode;
    }
  }

  public static void main(String[] args) {
    String stringExpression = "((id.Double = 461506965680951296) "
        + "AND ((user.screen_name.String) = jileyssex)) OR "
        + "((in_reply_to_screen_name.String) = \"smiley ugly  bieber15\")";
    String separator = " ";
    BinaryExpression be = new BinaryExpression(stringExpression);
    System.out.println("Infix: " + be.getInfix());
  }

}
