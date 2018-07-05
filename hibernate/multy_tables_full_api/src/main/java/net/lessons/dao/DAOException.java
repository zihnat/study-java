package net.lessons.dao;

class DAOException extends Exception{

  public DAOException() {}

  public DAOException(String detailMessage/*, String methodName*/){
    super(detailMessage);
  }

  public DAOException(String detailMessage, Throwable throwable) {
      super(detailMessage, throwable);
  }

  public DAOException(Throwable throwable) {
      super(throwable);
  }
}
