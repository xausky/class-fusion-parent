package io.github.xausky.cfmpe.impl;

import io.github.xausky.cfa.FusionImpl;
import io.github.xausky.cfmpe.User;
import io.github.xausky.cfmpe.MenuUserInterface;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by xausky on 11/1/16.
 */
@FusionImpl
public class MenuUserImpl implements MenuUserInterface {
    @Autowired
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
