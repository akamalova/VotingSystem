package ru.testAssignment.voting.util;


import ru.testAssignment.voting.HasId;
import ru.testAssignment.voting.model.AbstractBaseEntity;
import ru.testAssignment.voting.util.exception.NotFoundException;
import ru.testAssignment.voting.util.exception.TimesUpException;

import java.time.LocalTime;

public class ValidationUtil {

    private static boolean isTest = false;

    public static void setTest(boolean test) { isTest = test; }                                          //only for tests

    public static final LocalTime TIME_LIMIT = LocalTime.of(11,0,0);

    public static final LocalTime TIME_LIMIT_MAX = LocalTime.MAX;

    private ValidationUtil() {
    }

    public static void checkNotFoundWithId(boolean found, int id) {
        checkNotFound(found, "id=" + id);
    }

    public static <T> T checkNotFoundWithId(T object, int id) {
        return checkNotFound(object, "id=" + id);
    }

    public static <T> T checkNotFound(T object, String msg) {
        checkNotFound(object != null, msg);
        return object;
    }

    public static void checkNotFound(boolean found, String msg) {
        if (!found) {
            throw new NotFoundException("Not found entity with " + msg);
        }
    }

    public static void checkNew(HasId bean) {
        if (!bean.isNew()) {
            throw new IllegalArgumentException(bean + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(HasId bean, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (bean.isNew()) {
            bean.setId(id);
        } else if (bean.getId() != id) {
            throw new IllegalArgumentException(bean + " must be with id=" + id);
        }
    }

    public static void checkTime(LocalTime time) {
        if (!time.isBefore(isTest? TIME_LIMIT_MAX : TIME_LIMIT)) throw new TimesUpException();
    }
}