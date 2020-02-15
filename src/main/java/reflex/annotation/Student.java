package reflex.annotation;

public class Student {

    @Info("123")
    private String id;
    @Info("zhouyueyue")
    private String name;
    @Info("cqu")
    private String school;


    Student(){

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
