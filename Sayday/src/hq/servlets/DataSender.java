package hq.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import hq.misc.DBConnection;

/**
 * Servlet implementation class DataSender
 */
@WebServlet("/DataSender")
public class DataSender extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DataSender() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ResultSet rs = null;
		Connection myConnection = null;
		String result = "";
		try{
			myConnection = DBConnection.getInstance();
			Statement stmt = myConnection.createStatement();
			String query = "SELECT * FROM info;";
			rs = stmt.executeQuery(query);
		}
		catch(Exception e){
			return;
		}
		
		try {
			while (rs.next()) {
			    int numColumns = rs.getMetaData().getColumnCount();
			    for ( int i = 1 ; i <= numColumns ; i++ ) {
			    	result += rs.getObject(i) + " | ";
			    }
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		String[] split = result.split("\\|");
		JsonObject jsonobj = new JsonObject();
		jsonobj.addProperty("id", split[0].trim());
		jsonobj.addProperty("name", split[1].trim());
		jsonobj.addProperty("grade", split[2].trim());

		response.setContentType("application/json");
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
	    String prettyJson = gson.toJson(jsonobj);
		PrintWriter out = response.getWriter();
		// Assuming your json object is **jsonObject**, perform the following, it will return your json object  
		out.print(prettyJson);
		out.flush();
	}

}
