package ru.voting.util.exception;


public class TimesUpException extends RuntimeException  {
    public TimesUpException() {
        super("Late to voted today!");
    }
}
