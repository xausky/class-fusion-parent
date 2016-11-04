package io.github.xausky.cfmpe;

import io.github.xausky.cfa.Fusion;

/**
 * Created by xausky on 10/31/16.
 */
@Fusion
public class User implements UserInterface,UserExtInterface {
    @Override
    public String toString() {
        return String.format("User{ id=%s, name=%s, email=%s }",getId(),getName(),getEmail());
    }
}
