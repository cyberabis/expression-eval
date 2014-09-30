package io.thedal.expression.exceptions;

public class NoFunctionFound extends Exception {

  private static final long serialVersionUID = 1L;
  private String message = null;

  public NoFunctionFound() {
    super();
  }

  public NoFunctionFound(String message) {
    super(message);
    this.message = message;
  }

  public NoFunctionFound(Throwable cause) {
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
