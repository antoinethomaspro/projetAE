package sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import posts.Post;
import users.User;

public class DB {
	private static DB db;
	Connection con = null;
	Statement statement = null;

	ResultSet res = null;
	String driver = "com.mysql.cj.jdbc.Driver";
	public static final String URL = "jdbc:mysql://localhost:3306/appent_projet";
	public static final String USER = "root";
	public static final String PASSWD = "root";

	private DB() throws ClassNotFoundException, SQLException {
		Class.forName(driver);
		con = DriverManager.getConnection(URL, USER, PASSWD);
	}
	
	public static DB getInstance() {
		if(db == null) {
			try {
				db = new DB();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return db;
	}

	public boolean signup(User user) {
		String name = user.getName();
		String password = user.getPassword();
		
		// on vérifie si le nom n'est pas déjà utilisé
		try {
			statement = con.createStatement();
			String sql = "select name from users";
			ResultSet res = statement.executeQuery(sql);
			boolean verif = false;
			while(res.next()) {
				if(res.getString("name").compareTo(name) == 0) verif = true;
				else verif = false;
			}
			res.close();
			if(verif) return false;
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// si le nom n'est pas encore utilisé, on ajoute le compte dans la base de données
		String sql = "insert into users (name, password)" + "VALUES (\""+name+"\", \""+password+"\")";
		try {
			statement = con.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return true;
	}
	
	public boolean connect(String name, String password) {
		String sql = "select id from users where name = \""+ name + "\" and password = \"" + password + "\"";
		try {
			statement = con.createStatement();
			ResultSet res = statement.executeQuery(sql);
			int i = -1;
			while(res.next()) {
				i = res.getInt("id");
				System.out.println(i);
			}
			if(i != -1) return true;
			else return false;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean publish(Post post, String user_name) {
		System.out.println(userExists(user_name));
		if(!userExists(user_name)) return false;
		int id = getIDbyName(user_name);
		String sql = "insert into posts (text, id_user, date) values (\""+post.getText()+"\","+id+", \""+post.getDate()+"\")";
		try {
			statement = con.createStatement();
			statement.executeUpdate(sql);
		} catch (SQLException e) {
			return false;
		}
		return true;
	}
	
//	public List<Post> listing(){
//		ArrayList<Post> list = new ArrayList<Post>();
//		String sql = "select text, name, date from users, posts, following where posts.id_user = following.";
//	}
	
	public List<String> followers(String name){
		int i = getIDbyName(name);
		ArrayList<String> list = new ArrayList<>();
		String sql = "select name from users, following where id_followed = "+i+" and users.id = id_following";
		try {
			statement = con.createStatement();
			ResultSet res = statement.executeQuery(sql);
			while(res.next()) {
				list.add(res.getString("name"));
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public List<String> following(String name){
		int i = getIDbyName(name);
		ArrayList<String> list = new ArrayList<>();
		String sql = "select name from users, following where id_following = \""+i+"\" and users.id = id_followed";
		try {
			statement = con.createStatement();
			ResultSet res = statement.executeQuery(sql);
			while(res.next()) {
				list.add(res.getString("name"));
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public List<String> same_follow(String nameA, String nameB){
		int a = getIDbyName(nameA);
		int b = getIDbyName(nameB);
		ArrayList<String> list = new ArrayList<>();
		String sql = "select name from users , following f1, following f2 where f1.id_following = "+a+" and f2.id_following = "+b+" and f1.id_followed = f2.id_followed and f1.id_followed = users.id";
		try {
			statement = con.createStatement();
			ResultSet res = statement.executeQuery(sql);
			while(res.next()) {
				list.add(res.getString("name"));
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public boolean userExists(String name) {
		if(getIDbyName(name) == -1) return false;
		else return true;
	}
	
	public List<Post> UserProfile(String name){
		ArrayList<Post> list = new ArrayList<Post>();
		String sql = "select text, date from posts, users where posts.id_user = users.id and users.name = \""+name+"\"";
		try {
			statement = con.createStatement();
			ResultSet res = statement.executeQuery(sql);
			while(res.next()) {
				Post p = new Post();
				p.setText(res.getString("text"));
				p.setDate(res.getString("date"));
				list.add(p);
			}
			return list;
		} catch (SQLException e) {
			return null;
		}
	}
	
	public int getIDbyName(String name) {
		String sql = "select id from users where name =\""+name+"\"";
		try {
			statement = con.createStatement();
			ResultSet res = statement.executeQuery(sql);
			int i = -1;
			while(res.next()) {
				i = res.getInt("id");
			}
			res.close();
			return i;
		} catch (SQLException e) {
			return -1;
		}
	}
	
	/**
	 * Know if nameA follow nameB
	 * @param nameA
	 * @param nameB
	 * @return true if nameA follow nameB else return false
	 */
	public boolean isFollowed(String nameA, String nameB) {
		int a = getIDbyName(nameA);
		int b = getIDbyName(nameB);
		System.out.println(nameA + a+nameB+b);
		String sql = "select * from following where id_following = "+a+" and id_followed = "+b;
		try {
			statement = con.createStatement();
			ResultSet res = statement.executeQuery(sql);
			boolean verif = false;
			while(res.next()) {
				System.out.println("following"+res.getInt("id_following"));
				System.out.println("followed"+res.getInt("id_followed"));
				if(res.getInt("id_following") > 0 && res.getInt("id_followed") > 0)
					verif = true;
			}
			return verif;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean addFollow(String nameA, String nameB) {
		int a = getIDbyName(nameA);
		int b = getIDbyName(nameB);
		System.out.println(nameA + a + nameB + b);
		String sql = "insert into following (id_following, id_followed) values ("+a+","+b+")";
		try {
			statement = con.createStatement();
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public boolean removeFollow(String nameA, String nameB) {
		int a = getIDbyName(nameA);
		int b = getIDbyName(nameB);
		String sql = "delete from following where id_following = "+a+" and id_followed = "+b;
		try {
			statement = con.createStatement();
			statement.executeUpdate(sql);
			return true;
		} catch (SQLException e) {
			return false;
		}
	}
	
	public static void main(String[] args) {
		DB db = DB.getInstance();
		//if(db.signup(new User("root", "root", "root"))) System.out.println("yes");
		//else System.out.println("no");
		
		if(db.connect("root", "root")) System.out.println("connected");
		else System.out.println("unconnected");
	}
}
