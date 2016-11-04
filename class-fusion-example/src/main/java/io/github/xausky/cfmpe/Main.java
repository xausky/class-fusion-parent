package io.github.xausky.cfmpe;

/**
 * Created by xausky on 10/31/16.
 */
public class Main {
    public static void main(String[] args){
        User user = new User();
        user.setId(0);
        user.setName("xausky");
        user.setEmail("xausky@gmail.com");
        System.out.println(user);
    }
}
