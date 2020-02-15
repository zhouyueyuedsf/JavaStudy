package reflex.annotation;

public class Test {

    @Constructor
    public Student student;

    public static void main(String[] args) {
        Test test  = new Test();
        ClassProxy.initConstructor(test);

        System.out.println(test.student);
    }
}
