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
		ServletConfig servletConfig = getServletConfig();
		String context = servletConfig.getServletContext().getRealPath("/");
		String PythonContext = context + "SOModule\\Surfer.py";
		String LogContext = context + "SOModule\\logs.txt";
		BufferedReader reader = request.getReader();
		JsonObject query = new Gson().fromJson(reader, JsonObject.class);
		String action = query.get("Action").getAsString();
		String s = null;
		StringBuilder b = new StringBuilder();
		switch(action){
		case "ask":
			Process p = Runtime.getRuntime().exec(String.format("python %s %s %s",
																	PythonContext, 
																	query.get("Question"),
																	query.get("Service")));
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

}
