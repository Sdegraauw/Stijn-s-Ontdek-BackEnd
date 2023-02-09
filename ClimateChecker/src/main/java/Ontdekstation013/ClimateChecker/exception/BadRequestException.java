package Ontdekstation013.ClimateChecker.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(Integer customStatusCode) {
        super(String.valueOf(customStatusCode));
    }
}
