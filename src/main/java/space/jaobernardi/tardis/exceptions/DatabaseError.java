package space.jaobernardi.tardis.exceptions;

public class DatabaseError extends Exception{
    public DatabaseError(String errorMessage) {
        super(errorMessage);
    }
}
