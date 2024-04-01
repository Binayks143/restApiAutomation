package pojo.deserialization_test;
/*
1. create all the variable
2. add private variable type
3. add getter and setter method (for auto creation in intellij use alt+ins)
Note: courses contains sub child json so for that need to create another java class and point to parent
 return type of that mini pojo class will be class name i.e. courses
With class name we can connect the parent json and mini json so that mini json will
 return the java object and will be used in parent json
 4. If json contains multiple records then we have to use List<CLassName> so that we can dynamic add n number
 of records.
 5. first you add all the dependencies class and data type then use getter and setter
*/

public class GetCourse {
    private String url;
    private String instructor;
    private String services;
    private String expertise;
    private Courses courses;

    private String linkedIn;

    //Pojo class


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getServices() {
        return services;
    }

    public void setServices(String services) {
        this.services = services;
    }

    public String getExpertise() {
        return expertise;
    }

    public void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public Courses getCourses() {
        return courses;
    }

    public void setCourses(Courses courses) {
        this.courses = courses;
    }

    public String getLinkedIn() {
        return linkedIn;
    }

    public void setLinkedIn(String linkedIn) {
        this.linkedIn = linkedIn;
    }
}
