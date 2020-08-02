package lambda

import java.util.*
import java.util.concurrent.locks.Lock
import java.util.concurrent.locks.ReentrantLock
import javax.swing.tree.TreeNode

inline fun <T> synchronized(lock: Lock, action: () -> T) : T {
    lock.lock()
    try {
        return action()
    } finally {
        lock.unlock()
    }
}

class LockOwner(val lock: Lock) {
    inline fun runUnderLock(body: () -> Unit) {
//        synchronized(lock, body)
    }
}

/**
 * 定义简单的内联函数
 */
inline fun inlined(block: () -> Unit) {
    println("hi!")
}

/**
 * 定义简单的非内联函数
 */
fun ordinaryFunction(block: () -> Unit) {
    println("hi!")
}

/**
 * 测试内联函数是否可以非局部返回
 */
fun foo() {
    ordinaryFunction {
        // 错误：不能使 `foo` 在此处返回
//        return
    }
    inlined {
        // OK：该 lambda 表达式是内联的
        return
    }
}

/**
 * lambda表达式执行在lambda体里面，需要添加crossInline
 */
inline fun crossLineF(crossinline body: () -> Unit) {
    val f = Runnable { body() }
}

/**
 * 具体化的类型参数
 */

fun <T> TreeNode.findParentOfType(clazz: Class<T>): T? {
    var p = parent
    while (p != null && !clazz.isInstance(p)) {
        p = p.parent
    }
    @Suppress("UNCHECKED_CAST")
    return p as T?
}

inline fun <reified T> TreeNode.findParentOfType(): T? {
    var p = parent
    while (p != null && p !is T) {
        p = p.parent
    }
    return p as T?
}

class MyTreeNode : TreeNode {
    override fun children(): Enumeration<*> {
        TODO("Not yet implemented")
    }

    override fun isLeaf(): Boolean {
        TODO("Not yet implemented")
    }

    override fun getChildCount(): Int {
        TODO("Not yet implemented")
    }

    override fun getParent(): TreeNode {
        TODO("Not yet implemented")
    }

    override fun getChildAt(p0: Int): TreeNode {
        TODO("Not yet implemented")
    }

    override fun getIndex(p0: TreeNode?): Int {
        TODO("Not yet implemented")
    }

    override fun getAllowsChildren(): Boolean {
        TODO("Not yet implemented")
    }

}

fun main() {
    val lock = ReentrantLock()
    synchronized(lock) {
        println("synchronized inline test")
    }
    LockOwner(lock).runUnderLock {
        println("lockOwner inline test")
    }

    crossLineF {
        println("crossLine test")
    }

    MyTreeNode().findParentOfType<MyTreeNode>()
}