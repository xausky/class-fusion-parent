package io.github.xausky.cfmpe;

import io.github.xausky.cfc.FusionTemplate;
import io.github.xausky.cfmpe.impl.UserExtImpl;
import io.github.xausky.cfmpe.impl.UserImpl;

/**
 * Created by xausky on 11/2/16.
 */
@FusionTemplate({UserImpl.class, UserExtImpl.class})
public class User implements UserInterface,UserExtInterface {
}
