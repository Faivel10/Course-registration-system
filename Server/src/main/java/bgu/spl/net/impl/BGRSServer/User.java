package bgu.spl.net.impl.BGRSServer;

import java.util.Comparator;
import java.util.LinkedList;

public class User {
    private boolean isAdmin;
    private String userName;
    private String password;
    private LinkedList<Course> courses;
    private boolean isOnline;

    public User(boolean isAdmin, String userName, String password) {
        this.isAdmin = isAdmin;
        this.userName = userName;
        this.password = password;
        courses = new LinkedList<>();
    }
    //check if a user is admin.
    public  boolean isAdmin() {
        return isAdmin;
    }
    //get the username
    public  String getUserName() {
        return userName;
    }
    //get the password.
    public  String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    //adding the course to the list.
    public  void addCourse(Course course)
    {

        courses.addLast(course);
    }
    //removing the course from the list.
    public  void removeCource(int numCourse)
    {

        for(Course c:courses)
        {

            if (c.getNumCourse()==numCourse)
            {
               courses.remove(c);
                return;
            }
        }
    }

    public  LinkedList<Course> getCourses() {
        return courses;
    }

    @Override
    public String toString() {
        String result="[";
        if(courses.isEmpty())
        {
            result= "[]";
        }
        else {
            //we sort the courses accoring to their position in the file.
            courses.sort(Comparator.comparingInt(Course::getPositionInFile));
            //for each course we add it to the courses list we will output.
            for (Course c : courses) {
                result += c.getNumCourse()+"".trim() + ",";

            }
            result=result.substring(0,result.length()-1)+"]";
        }
        return
                "Student: "+userName.trim()+"\n"+"Courses: "+result;
    }
}
