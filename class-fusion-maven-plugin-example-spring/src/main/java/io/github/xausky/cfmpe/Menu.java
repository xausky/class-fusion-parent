package io.github.xausky.cfmpe;

import io.github.xausky.cfa.Fusion;
import org.springframework.stereotype.Component;

/**
 * Created by xausky on 11/1/16.
 */
@Fusion
@Component
public class Menu implements MenuInterface,MenuUserInterface {
    @Override
    public String toString() {
        return String.format("Menu{ name=%s,user=%s }",getName(),getUser());
    }
}
