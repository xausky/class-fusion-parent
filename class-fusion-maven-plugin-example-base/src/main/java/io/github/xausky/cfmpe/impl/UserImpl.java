package io.github.xausky.cfmpe.impl;

import io.github.xausky.cfc.FusionImpl;
import io.github.xausky.cfmpe.UserInterface;

/**
 * Created by xausky on 10/31/16.
 */
@FusionImpl
public class UserImpl implements UserInterface {
    private Integer id;
    private String name;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
