package io.github.xausky.cfmpe;

/**
 * Created by xausky on 10/28/16.
 */
public interface UserExtInterface {
    default String getEmail(){return null;};
    default void setEmail(String email){};
}
