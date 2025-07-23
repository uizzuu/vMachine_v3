package exception;

public class MyException extends Exception {
    public static final Long serialVersionUID = 1L;
    // default 생성자 (기본생성자)
    public MyException(){};
    //
    public MyException(String message) {
        super(message);
    }
}
