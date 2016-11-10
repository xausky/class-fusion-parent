package io.github.xausky.cfmpe.impl;

import io.github.xausky.cfa.FusionImpl;
import io.github.xausky.cfmpe.MenuInterface;

import java.util.Timer;

/**
 * Created by xausky on 11/1/16.
 */
@FusionImpl
public class MenuImpl extends Timer implements MenuInterface{
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
