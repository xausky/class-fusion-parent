package io.github.xausky.cfmpe;

import io.github.xausky.cfa.FusionImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Created by xausky on 11/4/16.
 */
@FusionImpl
public class LogImpl implements LogInterface{
    @Id
    @GeneratedValue
    private Integer id;

    @Column(nullable = false)
    private String message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
