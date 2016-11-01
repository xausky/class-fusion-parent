package io.github.xausky.cfmpe.impl;

import io.github.xausky.cfc.FusionClass;
import io.github.xausky.cfmpe.MenuInterface;

/**
 * Created by xausky on 11/1/16.
 */
@FusionClass
public class MenuImpl implements MenuInterface{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
