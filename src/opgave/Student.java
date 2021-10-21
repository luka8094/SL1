package opgave;

public class Student {

    private String userName, firstName, lastName, classOrigin;

    public Student(String username, String firstName, String lastName, String classOrigin){

        this.userName = username;
        this.firstName = firstName;
        this.lastName = lastName;
        this.classOrigin = classOrigin;

    }

    public Student(){ }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getClassOrigin() {
        return classOrigin;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setClassOrigin(String classOrigin) {
        this.classOrigin = classOrigin;
    }

    @Override
    public String toString(){
        return "Username: "+ getUserName()
                +", first name: "+ getFirstName()
                +", last name: "+ getLastName()
                +", class origin: "+ getClassOrigin();
    }
}
