package io.github.xausky.cfmpe;

import io.github.xausky.cfc.Fusion;
import io.github.xausky.cfmpe.impl.MenuImpl;
import io.github.xausky.cfmpe.impl.MenuUserImpl;
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
