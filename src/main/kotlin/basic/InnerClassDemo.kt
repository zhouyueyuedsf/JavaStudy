package basic

open class InnerClassDemo(var innerClass: InnerClass) {

    val v = InnerClassDemo(InnerClass())

    inner class InnerClass {

    }
}

//class OutClass : InnerClassDemo(InnerClass()) {
//
//}