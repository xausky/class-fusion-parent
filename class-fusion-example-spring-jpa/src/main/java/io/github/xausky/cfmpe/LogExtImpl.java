package io.github.xausky.cfmpe;

import io.github.xausky.cfa.FusionImpl;

import javax.persistence.Column;

/**
 * Created by xausky on 11/4/16.
 */
@FusionImpl
public class LogExtImpl implements LogExtInterface {
    @Column
    private String owner;

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }
}
