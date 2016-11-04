package io.github.xausky.cfmpe;

import io.github.xausky.cfa.Fusion;

import javax.persistence.Entity;

/**
 * Created by xausky on 11/4/16.
 */
@Entity
@Fusion
public class Log implements LogInterface{
    @Override
    public String toString() {
        return String.format("Log{ id:%s, message:%s }",getId(),getMessage());
    }
}
