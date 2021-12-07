package com.disqo.exchangerate.exception;

public class InvalidCurrencyException extends RuntimeException {

  public InvalidCurrencyException(String message, Throwable cause) {
    super(message, cause);
  }
}
