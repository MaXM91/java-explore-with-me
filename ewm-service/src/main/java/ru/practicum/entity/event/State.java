package ru.practicum.entity.event;

import ru.practicum.validate.exception.NotValidException;

public enum State {
    PENDING,
    PUBLISHED,
    CANCELED,
    CONFIRMED,
    REJECTED;

    public static State toState(String state) {
        switch (state) {
            case "PENDING" :
                return State.PENDING;
            case "PUBLISHED" :
                return State.PUBLISHED;
            case "CANCELED" :
                return State.CANCELED;
            case "CONFIRMED" :
                return State.CONFIRMED;
            case "REJECTED" :
                return State.REJECTED;
            default :
                throw new NotValidException("state " + state + " does not exist.");
        }
    }
}