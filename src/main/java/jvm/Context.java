package jvm;

public class Context {

    public java.lang.Object thisObject;
    public java.lang.Object targetObject;
    public String thisClassName;
    public String thisSimpleClassName;
    public String thisMethodName;

    private Context(java.lang.Object targetObject, java.lang.Object thisObject, String thisClassName, String thisSimpleClassName, String thisMethodName) {
        this.thisObject = thisObject;
        this.targetObject = targetObject;
        this.thisClassName = thisClassName;
        this.thisSimpleClassName = thisSimpleClassName;
        this.thisMethodName = thisMethodName;
    }

    /**
     * @param targetObject
     * @param thisObject
     * @param thisClassName
     * @param thisMethodName
     * @return
     */
    public static Context createInstance(java.lang.Object targetObject, java.lang.Object thisObject, String thisClassName, String thisMethodName) {
        return new Context(targetObject, thisObject, thisClassName, thisClassName.substring(thisClassName.lastIndexOf("/") + 1), thisMethodName);
    }
}
