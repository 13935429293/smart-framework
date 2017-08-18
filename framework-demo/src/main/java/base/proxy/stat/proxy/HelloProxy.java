package base.proxy.stat.proxy;

import base.proxy.stat.iface.Hello;
import base.proxy.stat.impl.HelloImpl;

public class HelloProxy implements Hello {

    private Hello hello;

    public HelloProxy() {
        hello = new HelloImpl();
    }

    private void before(){
        System.out.println("before");
    }

    private void end(){
        System.out.println("end");
    }

    public void say(String name) {
        before();
        hello.say(name);
        end();
    }

    public static void main(String[] args) {
        new HelloProxy().say("world");
    }
}
