package reflex.proxy.jdkproxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ChineseProxyFactory implements InvocationHandler {

    Object mTarget;

    public ChineseProxyFactory(Object target) {
        mTarget = target;
    }

    //生成新的字节码.class文件
    public static Object getProxy(Object target) {
        //检查是否是接口 xxx.class.isInterface();
        try {
            Class<?> c1 = Class.forName("reflex.proxy.jdkproxy.Chinese");
//            Chinese chinese1 = (Chinese) c1.newInstance();
            Class<?> c2 = Class.forName("reflex.proxy.jdkproxy.Chinese", false, target.getClass().getClassLoader());
            Chinese chinese2 = (Chinese) c2.newInstance();
            int j = Chinese.i;
            return Proxy.newProxyInstance(target.getClass().getClassLoader(), new Class[]{ChineseInterface.class}, new ChineseProxyFactory(target));
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return method.invoke(mTarget, args);
    }
}
