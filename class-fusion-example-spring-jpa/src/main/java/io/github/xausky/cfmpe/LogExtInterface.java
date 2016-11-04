package io.github.xausky.cfmpe;

/**
 * Created by xausky on 11/4/16.
 */
public interface LogExtInterface {
    default String getOwner() {
        return null;
    }

    default void setOwner(String owner) {
    }
}
