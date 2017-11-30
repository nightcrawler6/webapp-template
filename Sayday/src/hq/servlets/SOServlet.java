package hq.servlets;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;

import hq.misc.basicPerson;

/**
 * Servlet implementation class SOServlet
 */
@WebServlet("/SOServlet")
public class SOServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SOServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String delimiter = "\\";
			String property = System.getProperty("os.name");
			if(!property.toLowerCase().contains("windows")){
				delimiter = "/"; 
			}
			
			ServletConfig servletConfig = getServletConfig();
			String context = servletConfig.getServletContext().getRealPath("/");
			String PythonContext = String.format("%s%s%s%s", context, "SOModule", delimiter, "Surfer.py");
			//String LogContext = context + "SOModule\\logs.txt";
			BufferedReader reader = request.getReader();
			JsonObject query = new Gson().fromJson(reader, JsonObject.class);
			String action = query.get("Action").getAsString();
			String s = null;
			StringBuilder b = new StringBuilder();
			System.out.println(String.format("python %s %s %s",
																		PythonContext, 
																		query.get("Question"),
																		query.get("Service")));
			switch(action){
			case "ask":
				String[] execPayload = new String[4];
				execPayload[0] = String.format("%s", "python");
				execPayload[1] = String.format("%s", PythonContext);
				execPayload[2] = String.format("%s", query.get("Question"));
				execPayload[3] = String.format("%s", query.get("Service"));
				Process p = Runtime.getRuntime().exec(execPayload);
				BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
	
				// read the output from the command
				while ((s = stdInput.readLine()) != null) {
					b.append(s);
				}
	            
				break;
			default:
				break;
			}
			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json");
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			PrintWriter out = response.getWriter();
			JsonReader jsonParseReader = new JsonReader(new StringReader(b.toString()));
			System.out.println(b.toString());
			jsonParseReader.setLenient(true);
			JsonObject responseJson = gson.fromJson(jsonParseReader, JsonObject.class);
			System.out.println(responseJson.toString());
			/*try (FileWriter fw = new FileWriter(LogContext, true);
				BufferedWriter bw = new BufferedWriter(fw);
				PrintWriter pout = new PrintWriter(bw)) {
				pout.println(responseJson.toString());
			} catch (IOException e) {
				
			}*/
			out.print(responseJson);
			out.flush();
		}
		catch(Exception e){
			e.printStackTrace();
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
		}
	}

}
