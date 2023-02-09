package Ontdekstation013.ClimateChecker.exception;

public class ConflictException extends RuntimeException {
    public ConflictException(Integer customStatusCode) {
        super(String.valueOf(customStatusCode));
    }
}
