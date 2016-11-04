package io.github.xausky.cfmpe;

/**
 * Created by xausky on 11/4/16.
 */
public interface LogInterface {
    default Integer getId() {
        return null;
    }

    default void setId(Integer id) {
    }

    default String getMessage() {
        return null;
    }

    default void setMessage(String message) {
    }
}
