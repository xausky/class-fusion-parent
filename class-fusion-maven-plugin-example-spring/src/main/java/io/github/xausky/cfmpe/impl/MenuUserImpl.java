package io.github.xausky.cfmpe.impl;

import io.github.xausky.cfc.FusionClass;
import io.github.xausky.cfmpe.MenuInterface;
import io.github.xausky.cfmpe.User;
import io.github.xausky.cfmpe.MenuUserInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xausky on 11/1/16.
 */
@FusionClass
public class MenuUserImpl implements MenuInterface,MenuUserInterface {
    @Autowired
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
