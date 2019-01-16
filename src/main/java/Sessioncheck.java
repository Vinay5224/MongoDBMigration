

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Sessioncheck
 */
@WebServlet("/Sessioncheck")
public class Sessioncheck extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sessioncheck() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
	    PrintWriter out = response.getWriter();  
		String uname=request.getParameter("value");
		HttpSession session = request.getSession();
		String data = (String) session.getAttribute("uname");
		String str="";
		
		//System.out.println("@@@@@"+data);
		
		if(data!=null)
		{
			
			//System.out.println("working");
			
			str="success";
			
		}
		else
		{
			//System.out.println("Nothing");
			
			str="failure";
			
		}
		response.getWriter().write(str);
	}

}
