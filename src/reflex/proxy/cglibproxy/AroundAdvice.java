package reflex.proxy.cglibproxy;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class AroundAdvice implements MethodInterceptor {
    @Override
    public Object intercept(Object target, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("执行目标方法之前，模拟开始事务 ...");
        // 执行目标方法，并保存目标方法执行后的返回值
        Object rvt = methodProxy.invokeSuper(target, new String[]{"被改变的参数"});
        System.out.println("执行目标方法之后，模拟结束事务 ...");
        return rvt + " 新增的内容";
    }
}
