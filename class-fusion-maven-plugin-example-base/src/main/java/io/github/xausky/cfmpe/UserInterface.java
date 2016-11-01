package io.github.xausky.cfmpe;

/**
 * Created by xausky on 10/31/16.
 */
public interface UserInterface {
    default Integer getId(){return null;};
    default void setId(Integer id){};
    default String getName(){return null;};
    default void setName(String name){};
}
