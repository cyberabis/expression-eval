package io.logbase.expression.refimpl;

public class OperatorUtil {

  public static Operator checkOperator(String input) {
    if (input.equals("="))
      return Operator.EQUALS;
    else if (input.equals("!="))
      return Operator.NOT_EQUALS;
    else if (input.equals(">"))
      return Operator.GREATER_THAN;
    else if (input.equals("<"))
      return Operator.LESSER_THAN;
    else if (input.equals(">="))
      return Operator.GR_THAN_EQ;
    else if (input.equals("<="))
      return Operator.LS_THAN_EQ;
    else if (input.equals("AND"))
      return Operator.AND;
    else if (input.equals("OR"))
      return Operator.OR;
    else if (input.equals("LIKE"))
      return Operator.LIKE;
    else if (input.equals("<>"))
      return Operator.NOT_EQUALS;
    else
      return null;
  }

}
