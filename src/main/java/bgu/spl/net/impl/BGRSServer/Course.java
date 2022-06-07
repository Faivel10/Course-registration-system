package bgu.spl.net.impl.BGRSServer;


import java.util.*;

/** class represnts each course in the database
 */

public class Course {
    private String name;
    private int numCourse;
    private final LinkedList<Integer> kdamCourses;
    private int numofMaxStudents;
    private LinkedList<User> users;
    //position of the course in the courses file so we can sort later

    private int positionInFile=-1;

    public Course(int numCourse, String name, LinkedList<Integer> kdamCourses, int numofMaxStudents,int positionInFile) {
        this.name = name;
        this.numCourse = numCourse;
        this.kdamCourses = kdamCourses;
        this.numofMaxStudents = numofMaxStudents;
        users=new LinkedList<>();
        this.positionInFile=positionInFile;

    }

    public int getPositionInFile()
    {
        return positionInFile;
    }
    //if we have user registered
    public boolean hasUser(User s)
    {
        return users.contains(s);
    }

    //inserting new user.- if we dont have him yer
    //and we have more free places only.
    public boolean insertUser(User s)
    {

        if(!users.contains(s) && users.size()<numofMaxStudents)
        {
            users.addLast(s);
            return true;
        }
        return false;
    }

    public  boolean removeUser(User s)
    {
        boolean deleted = users.contains(s);

        return users.remove(s);
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumCourse() {
        return numCourse;
    }

    public void setNumCourse(int numCourse) {
        this.numCourse = numCourse;
    }

    public  LinkedList<Integer> getKdamCourses() {
        return kdamCourses;
    }
    //outputing the kdam courses using a string
    public String getKdamCoursesString()
    {
        if(kdamCourses.isEmpty())
            return "[]";
        String s="[";
        for(Integer c:kdamCourses)
        {
            s=s+(c+",").trim();
        }

        return s.substring(0,s.length()-1)+"]";
    }

    public void addKdamCourse(Integer c) {
        kdamCourses.addLast(c);
    }

    public int getNumofMaxStudents() {

        return numofMaxStudents;
    }

    public void setNumofMaxStudents(int numofMaxStudents) {
        this.numofMaxStudents = numofMaxStudents;
    }

    @Override
    public String toString() {
        StringBuffer buffer= new StringBuffer();

        //sorting the users accoring to the abc
        users.sort(Comparator.comparing(User::getUserName));

        buffer.append("[");
        if(users.isEmpty())
            buffer.append("]");
        else {
            for (User c : users) {
               String s=c.getUserName().trim();
                buffer.append(s);
               buffer.append(",".trim());
            }
            buffer.deleteCharAt(buffer.length()-1);
             buffer.append("]".trim());
        }

        return
                "Course: ("+numCourse+") "+name+"\n"+"Seats Available: "+(numofMaxStudents-users.size())+"/"+numofMaxStudents+
                "\n"+"Students Registered: "+buffer.toString().trim();
    }

}
