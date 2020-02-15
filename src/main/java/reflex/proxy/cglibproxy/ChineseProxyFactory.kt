package reflex.proxy.cglibproxy

import net.sf.cglib.proxy.Enhancer
import reflex.proxy.jdkproxy.Chinese

object ChineseProxyFactory {
    // 设置要代理的目标类, cglib生成子类的字节码
    // 设置要代理的拦截器
    // 生成代理类的实例
    @JvmStatic
    val authInstance: Chinese
        get() {
            val en = Enhancer()
            // 设置要代理的目标类, cglib生成子类的字节码
            en.setSuperclass(Chinese::class.java)
            // 设置要代理的拦截器
            en.setCallback(AroundAdvice())
            // 生成代理类的实例
            return en.create() as Chinese
        }
}