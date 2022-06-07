package bgu.spl.net.impl.BGRSServer;


import java.io.*;
import java.util.Comparator;
import java.util.LinkedList;
import java.io.FileReader;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Passive object representing the Database where all courses and users are stored.
 * <p>
 * This class must be implemented safely as a thread-safe singleton.
 * You must not alter any of the given public methods of this class.
 * <p>
 * You can add private fields and methods to this class as you see fit.
 */
public class Database {

	private final ConcurrentLinkedQueue<Course> courses;
	private final ConcurrentLinkedQueue<User> users;
	private final ConcurrentLinkedQueue<User> onlineUsers;

	private static class SingletonHolder{
		private static final Database singleton = new Database();
	}

	//to prevent user from creating new Database
	private Database() {
		// TODO: implement
		courses = new ConcurrentLinkedQueue<Course>();
		users=new ConcurrentLinkedQueue<>();
		onlineUsers= new ConcurrentLinkedQueue<>();
		initialize("./Courses.txt");
	}

	/**
	 * Retrieves the single instance of this class.
	 */
	public static Database getInstance() {
		return SingletonHolder.singleton;
	}

	/**
	 * loades the courses from the file path specified
	 * into the Database, returns true if successful.
	 */
	public void initialize(String coursesFilePath) {
		// TODO: implement
		File f = new File(coursesFilePath);
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String line;
			int position=0;
			while( (line=br.readLine()) !=null) {
				position++;
				String[] lineSplited = line.split("\\|");
				String[] kdam = lineSplited[2].substring(1, lineSplited[2].length() - 1).split(",");

				LinkedList<Integer> kdamCourses= changeToLinkedIntegerArray(kdam);
				courses.add(new Course(Integer.parseInt(lineSplited[0]),
						lineSplited[1],
						kdamCourses,
						Integer.parseInt(lineSplited[3]),position));
			}



		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	//turning the kdam courses numbers into integers
	private LinkedList<Integer> changeToLinkedIntegerArray(String[] splittedKdam) {
		LinkedList<Integer> integers = new LinkedList<>();
		if(!splittedKdam[0].equals("")) {
			for (String t : splittedKdam) {
				integers.addLast(Integer.parseInt(t));
			}
		}
		return integers;
	}
	//goes though the  users and checkes if they are registered.
	private User isRegistered(String userName)
	{
		for(User c:users)
		{
			if(c.getUserName().equals(userName))
				return c;
		}
		return null;
	}
	//register account to database
	public boolean RegisterAccount(User s) {
		//if there is already a username like this we return false.
		if(isRegistered(s.getUserName())!=null){
			return false;
		}
		users.add(s);
		return true;
	}
	//check if some account it online.
	private boolean isOnline(String userName)
	{
		for (User c : onlineUsers) {
			if (c.getUserName().equals(userName))
				return true;
		}

		return false;
	}
	//login to the user
	//you use synchronized so a user wont be able to connect from two threads at the same time.
	public synchronized User login(String userName,String password)
	{
		User current=isRegistered(userName);
		//if the user and password are correct and the user is not online we add him to the online users.
		if(current!=null && !isOnline(userName) && current.getPassword().equals(password)) {
			onlineUsers.add(current);
			return current;
		}
		return null;
	}
	//we remove the user from being online.
	public  boolean logout(User user)
	{
		return  onlineUsers.remove(user);
	}
	//getting the course we need.
	private  Course getCourse(int numCourse) {
		for (Course c : courses) {
			if (c.getNumCourse() == numCourse) {
				return c;
			}
		}
			return null;
		}
	//registering a user to a course.
	public synchronized    boolean courseRegister(User s,short numCourse)
	{
		//if the user is not online or is an admin we return false.
		if(!onlineUsers.contains(s) || s.isAdmin())
		{
			return false;
		}
		Course c=getCourse(numCourse);
		//we check if the user is already registered to all the kdam courses so we can register him.
		if (c!=null && getNumCoursesList(s.getCourses()).containsAll(c.getKdamCourses()))
			{

				boolean result = c.insertUser(s);
				if(result){
					s.addCourse(c);
					return true;
				}
			}
		return false;
	}

	//getting a list of number of courses in the courses file.
	public  LinkedList<Integer> getNumCoursesList(LinkedList<Course> courses)
	{

		LinkedList<Integer> result = new LinkedList();
			for (Course c : courses) {
				result.add(c.getNumCourse());
			}
			return result;

	}


	public String KdamCheck(String courseNum)
	{
		Course c=getCourse(Integer.parseInt(courseNum));
		if(c!=null){
			//sorting the courses accoring to their position in the file.
			c.getKdamCourses().sort(Comparator.comparingInt(integer -> getCourse(integer).getPositionInFile()));

			return c.getKdamCoursesString();
		}
		return null;
	}

	public Course getCourse(User s,int courseNumm)
	{
		//getting  the course if we are admin only.
		if(s.isAdmin()) {
			return getCourse(courseNumm);
		}
		return null;
	}
	//getting the student for information only admin can do that.
	//before returning the user we sort his courses to be as in the file.
	public User getUser(User s,String username)
	{
		if(s.isAdmin())
		{
			for(User x:users)
			{
				if(x.getUserName().equals(username))
				{
					x.getCourses().sort(Comparator.comparingInt(Course::getPositionInFile));
					return x;
				}
			}
		}
		return null;
	}
	//check if a user is registered to a course.
	public boolean isRegistered(User s,int numCourse)
	{
		Course c=getCourse(numCourse);
			if(c!=null)
				return c.hasUser(s);
		return false;
	}
	//removing user from the course.Only normal student can do that.
	public boolean unRegisterCourse(User s,String numCourse)
	{
		if(s.isAdmin())
			return false;
		int number=Integer.parseInt(numCourse);
		Course c= getCourse(number);
		//if we have the user in the course continue.
			if(c!=null && c.hasUser(s)) {
				s.removeCource(number);
				return c.removeUser(s);
		}
		return false;
	}
	//getting the courses of the user.
	public LinkedList<Course> UserCourses(User s)
	{
		for(User x:users)
		{
			if(x==s)
			{
				//sorting the courses to be as in the file given.
				x.getCourses().sort(Comparator.comparingInt(Course::getPositionInFile));
				return x.getCourses();
			}
		}
		return null;
	}
	//check if there exists a course with this number of course.
	public boolean hasCourse(String numCourse)
	{
		int number= Integer.parseInt(numCourse);
		for(Course c:courses)
		{
			if(c.getNumCourse()==number)
			{
				return true;
			}
		}
		return false;

	}
}
