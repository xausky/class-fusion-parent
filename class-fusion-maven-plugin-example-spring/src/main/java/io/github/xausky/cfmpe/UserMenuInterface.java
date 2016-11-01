package io.github.xausky.cfmpe;

/**
 * Created by xausky on 11/1/16.
 */
public interface UserMenuInterface {
    default User getUser() {return null;}

    default void setUser(User user) {}
}
