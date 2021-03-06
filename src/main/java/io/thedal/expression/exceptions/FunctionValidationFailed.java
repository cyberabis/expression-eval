package io.thedal.expression.exceptions;

public class FunctionValidationFailed extends Exception {

  private static final long serialVersionUID = 1L;
  private String message = null;

  public FunctionValidationFailed() {
    super();
  }

  public FunctionValidationFailed(String message) {
    super(message);
    this.message = message;
  }

  public FunctionValidationFailed(Throwable cause) {
    super(cause);
  }

  @Override
  public String toString() {
    return message;
  }

  @Override
  public String getMessage() {
    return message;
  }

}
