package ru.testAssignment.voting.util.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

//  http://stackoverflow.com/a/22358422/548473
@ResponseStatus(value = HttpStatus.FORBIDDEN, reason = "Late to voted")  // 403
public class TimesUpException extends RuntimeException  {
    public TimesUpException() {
        super("Late to voted today!");
    }
}
