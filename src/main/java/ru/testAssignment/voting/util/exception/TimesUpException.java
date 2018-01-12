package ru.testAssignment.voting.util.exception;

public class TimesUpException extends RuntimeException  {
    public TimesUpException() {
        super("You are already voted today!");
    }
}
