package reflex.annotation;

import java.lang.reflect.Field;

public class ClassProxy {


    public static void initConstructor(Object obj){
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields){
            Constructor constructor = field.getAnnotation(Constructor.class);
            if(constructor != null){
                try {
                    field.set(obj, newStudent(field.getType()));
                    int i  = 0;
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }


//    @Nullable
    public static Object newStudent(Class clazz){
        Object object = null;
        try {
            object = clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            for (Field field : fields){
                field.setAccessible(true);
                Info annotation = field.getAnnotation(Info.class);
                String value = annotation.value();
                field.set(object, value);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return object;S
    }
}
