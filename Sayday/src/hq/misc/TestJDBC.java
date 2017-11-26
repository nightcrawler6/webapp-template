package hq.misc;

import java.sql.*;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import hq.testPack.HibernateUtil;

public class TestJDBC {
	public static void main(String[] args){
		try{
			String property = System.getProperty("os.name");
			System.out.println(property);
			//Class.forName("com.mysql.jdbc.Driver");
			
			//get a connection
			SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			Transaction tx = session.beginTransaction();
			session.save(new basicPerson("audai","shamalia"));
			tx.commit();
			session.close();
			sessionFactory.close();
			//Connection myConnection = DriverManager.getConnection("jdbc:mysql://localhost:3306/catalina", "root", "zubur1");
			//Statement stmt = myConnection.createStatement() ;
			//String query = "select * from info ;" ;
			//ResultSet rs = stmt.executeQuery(query) ;
			
			/*while ( rs.next() ) {
	            int numColumns = rs.getMetaData().getColumnCount();
	            for ( int i = 1 ; i <= numColumns ; i++ ) {
	               // Column numbers start at 1.
	               // Also there are many methods on the result set to return
	               //  the column as a particular type. Refer to the Sun documentation
	               //  for the list of valid conversions.
	               System.out.print( rs.getObject(i) + " | ");
	            }
	        }
			
			myConnection.close();*/
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
}
