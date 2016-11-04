package io.github.xausky.cfmpe;

/**
 * Created by xausky on 11/1/16.
 */
public interface MenuUserInterface {
    default User getUser() {return null;}

    default void setUser(User user) {}
}
