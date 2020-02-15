package reflex.proxy.cglibproxy

import reflex.proxy.cglibproxy.ChineseProxyFactory.authInstance

object Main {
    @JvmStatic
    fun main(args: Array<String>) {
        val chin = authInstance
        //        System.out.println(chin.sayHello("孙悟空"));
//        chin.eat("西瓜");
        println(chin.javaClass)
    }
}