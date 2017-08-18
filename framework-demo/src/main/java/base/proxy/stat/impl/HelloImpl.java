package base.proxy.stat.impl;

import base.proxy.stat.iface.Hello;

public class HelloImpl implements Hello {
    public void say(String name) {
        System.out.println("Hello " + name);
    }
}
