package typedismiss;

public class TypeDismissStudy<T> {

    T t;

    public static void main(String[] args) {
        TypeDismissStudy<String> typeDismissStudy = new TypeDismissStudy<>();
        typeDismissStudy.t = "123";
        String s = "234";
    }
}
