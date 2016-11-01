package io.github.xausky.cfmpe.impl;

import io.github.xausky.cfc.FusionClass;
import io.github.xausky.cfmpe.UserExtInterface;

/**
 * Created by xausky on 10/28/16.
 */
@FusionClass
public class UserExtImpl implements UserExtInterface {
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
