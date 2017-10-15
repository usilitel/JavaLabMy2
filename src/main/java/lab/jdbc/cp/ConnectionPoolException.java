package lab.jdbc.cp;

public class ConnectionPoolException extends RuntimeException {
    public ConnectionPoolException(String message, Exception e){
        super(message, e);
    }
}
