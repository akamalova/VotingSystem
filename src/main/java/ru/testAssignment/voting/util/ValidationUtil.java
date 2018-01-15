package ru.testAssignment.voting.util;


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

    public static void checkNew(AbstractBaseEntity entity) {
        if (!entity.isNew()) {
            throw new IllegalArgumentException(entity + " must be new (id=null)");
        }
    }

    public static void assureIdConsistent(AbstractBaseEntity entity, int id) {
//      http://stackoverflow.com/a/32728226/548473
        if (entity.isNew()) {
            entity.setId(id);
        } else if (entity.getId() != id) {
            throw new IllegalArgumentException(entity + " must be with id=" + id);
        }
    }

    public static void checkTime(LocalTime time) {
        if (!time.isBefore(isTest? TIME_LIMIT_MAX : TIME_LIMIT)) throw new TimesUpException();
    }
}