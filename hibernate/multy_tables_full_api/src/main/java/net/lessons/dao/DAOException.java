package net.lessons.dao;

public class DAOException extends Exception{

  public DAOException() {}

  public DAOException(String detailMessage){
    super(detailMessage);
  }

  public DAOException(String detailMessage, Throwable throwable) {
      super(detailMessage, throwable);
  }

  public DAOException(Throwable throwable) {
      super(throwable);
  }
}
