package hq.servlets;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.Connection;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import hq.misc.DBConnection;
import hq.misc.basicPerson;
import hq.testPack.HibernateUtil;

/**
 * Servlet implementation class FormServlet
 */
@WebServlet("/FormServlet")
public class FormServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public FormServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String parameter = request.getParameter("type");
		if(parameter == null){
			RequestDispatcher view = request.getRequestDispatcher("html/form-person.html");
			view.forward(request, response);
		}
		else{
			switch(parameter){
			case "getAllPeople":
				basicPerson.getAllFromDB();
				break;
			default:
				break;
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		BufferedReader reader = request.getReader();
		basicPerson person = null;
		try{
			person = new Gson().fromJson(reader, basicPerson.class);
			saveToDB(person);
			response.setStatus(HttpServletResponse.SC_OK);
		}
		catch(Exception e){
			System.out.println("something went wrong");
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		}
		finally{
			reader.close();
		}
	}
	
	private void saveToDB(basicPerson person){
		try{
			Session session = HibernateUtil.getSessionFactory().openSession();
			Transaction transaction = session.beginTransaction();
			session.save(person);
			transaction.commit();
			session.close();
		}
		catch(Exception e){
			System.out.println("Failed to save object to DB");
		}
	}
}
