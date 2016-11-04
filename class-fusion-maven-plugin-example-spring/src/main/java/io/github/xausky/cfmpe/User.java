package io.github.xausky.cfmpe;

import io.github.xausky.cfa.Fusion;
import org.springframework.stereotype.Component;

/**
 * Created by xausky on 11/1/16.
 */
@Fusion
@Component
public class User implements UserInterface,UserExtInterface {
    @Override
    public String toString() {
        return String.format("User{ id=%s, name=%s, email=%s }",getId(),getName(),getEmail());
    }
}
