package net.lessons.dao;

class DAOException extends Exception{
  private Exception stack;

  public DAOException(String message/*, String methodName*/){
    super(message);
  }

  public DAOException(String message, Exception exc){
    super(message);
    stack = exc;
  }

  public Exception getStack(){
    return stack;
  }
}
