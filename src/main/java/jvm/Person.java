package jvm;

public class Person {

    private final String name = "person";
    private final Language mLanguage = new Language();

    public void say() {
        System.out.println("Person say " +  mLanguage.getContent());
    }

    public static void main(String[] args) {
        Person person = new Person();
        person.say();
    }
}
