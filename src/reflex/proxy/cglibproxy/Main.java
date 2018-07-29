package reflex.proxy.cglibproxy;

import reflex.proxy.cglibproxy.Chinese;
import reflex.proxy.cglibproxy.ChineseProxyFactory;

public class Main {
    public static void main(String[] args)
    {
        Chinese chin = ChineseProxyFactory.getAuthInstance();
        System.out.println(chin.sayHello("孙悟空"));
        chin.eat("西瓜");
        System.out.println(chin.getClass());
    }
}
