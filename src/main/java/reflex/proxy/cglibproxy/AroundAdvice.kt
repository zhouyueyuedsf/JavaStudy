package reflex.proxy.cglibproxy

import net.sf.cglib.proxy.MethodInterceptor
import net.sf.cglib.proxy.MethodProxy
import java.lang.reflect.Method

class AroundAdvice : MethodInterceptor {
    @Throws(Throwable::class)
    override fun intercept(target: Any?, method: Method?, objects: Array<Any?>?, methodProxy: MethodProxy): Any {
        println("执行目标方法之前，模拟开始事务 ...")
        // 执行目标方法，并保存目标方法执行后的返回值
        val rvt: Any = methodProxy.invokeSuper(target, arrayOf("被改变的参数"))
        println("执行目标方法之后，模拟结束事务 ...")
        return "$rvt 新增的内容"
    }
}