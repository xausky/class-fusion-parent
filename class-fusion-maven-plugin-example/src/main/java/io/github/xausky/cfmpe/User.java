package io.github.xausky.cfmpe;

import io.github.xausky.cfc.FusionTemplate;
import io.github.xausky.cfmpe.impl.UserExtImpl;
import io.github.xausky.cfmpe.impl.UserImpl;

/**
 * Created by xausky on 10/31/16.
 */
@FusionTemplate({UserImpl.class,UserExtImpl.class})
public class User implements UserInterface,UserExtInterface {
    @Override
    public String toString() {
        return String.format("User{ id=%s, name=%s, email=%s }",getId(),getName(),getEmail());
    }
}
