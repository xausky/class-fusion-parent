package io.github.xausky.cfmpe;

import java.util.TimerTask;

/**
 * Created by xausky on 10/31/16.
 */
public class Main {
    public static void main(String[] args){
        User user = new User();
        user.setId(0);
        user.setName("xausky");
        user.setEmail("xausky@gmail.com");
        Menu menu = new Menu();
        menu.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("TimerTask");
                System.exit(0);
            }
        },1000);
        System.out.println(user);
    }
}
