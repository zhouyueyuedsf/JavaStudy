package reflex.proxy.jdkproxy;

public class Main {



    public static void main(String[] args) {
        System.getProperties().put("sun.misc.ProxyGenerator.saveGeneratedFiles", true);
        ChineseInterface chinese = (ChineseInterface) ChineseProxyFactory.getProxy(new Chinese());
        chinese.sayHello();
    }
}
