package reflex.proxy.jdkproxy

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy

class ChineseProxyFactory(var mTarget: Any) : InvocationHandler {
    @Throws(Throwable::class)
    override fun invoke(proxy: Any, method: Method, args: Array<Any>): Any {
        return method.invoke(mTarget, *args)
    }

    companion object {
        //生成新的字节码.class文件
        @JvmStatic
        fun getProxy(target: Any): Any? { //检查是否是接口 xxx.class.isInterface();
            try {
                val c1 = Class.forName("java.reflex.proxy.jdkproxy.Chinese")
                //            Chinese chinese1 = (Chinese) c1.newInstance();
                val c2 = Class.forName("java.reflex.proxy.jdkproxy.Chinese", false, target.javaClass.classLoader)
                val chinese2 = c2.newInstance() as Chinese
                val j = Chinese.i
                return Proxy.newProxyInstance(target.javaClass.classLoader, arrayOf<Class<*>>(ChineseInterface::class.java), ChineseProxyFactory(target))
            } catch (e: ClassNotFoundException) {
                e.printStackTrace()
            } catch (e: InstantiationException) {
                e.printStackTrace()
            } catch (e: IllegalAccessException) {
                e.printStackTrace()
            }
            return null
        }
    }

}