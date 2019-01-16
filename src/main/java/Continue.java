

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Continue
 */
@WebServlet("/Continue")
public class Continue extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Continue() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
		
		String jdbcurl = "";
		String usernamejdbc = "";
		String jdbcpassword = "";
		String jdbctablename = "";
		String mongourl = "";
		String usernamemongo = "";
		String mongopassword = "";
		
		String sqldb = request.getParameter("sqldb") + "";
		String mongodb = request.getParameter("mongodb") + "";
		jdbcurl = request.getParameter("jdbcurl");
		usernamejdbc = request.getParameter("usernamejdbc");
		jdbcpassword = request.getParameter("jdbcpassword");
		jdbctablename = request.getParameter("jdbctab");
		mongourl = request.getParameter("mongourl");
		usernamemongo = request.getParameter("usernamemongo");
		mongopassword = request.getParameter("mongopassword");
		
		String unchktabname = request.getParameter("unchktabname").trim();
		unchktabname = unchktabname.substring(0, unchktabname.length() - 1);
		
		ArrayList<String> unchklist = new ArrayList<String>(Arrays.asList(unchktabname.split(",")));
		 System.out.println("unchklist:::"+unchklist.toString());
		 
		 
		 Connectors con = new Connectors();
			con.loadProperties(usernamemongo, mongopassword, mongourl, jdbctablename);
			String temp = "";
			
			ArrayList<String> tblist = new ArrayList<>();
			whole wle = new whole();
			System.out.println("jdbcurl:::"+jdbcurl);
		
		ArrayList<Integer> remVal = new ArrayList<Integer>();
		int tablen = 0;	
		 
		 try {
				tblist = wle.tablelist(jdbcurl, usernamejdbc, jdbcpassword);
				System.out.println("tblist::"+tblist);
				
				for(int i=0;i<tblist.size(); i++){
					
					String str = tblist.get(i).trim();
					for(String rm : unchklist){
						
						if(rm.trim().equalsIgnoreCase(str)){
							System.out.println("removed::"+str);
							remVal.add(i);
							System.out.println();
						//break;
						}
					}
					
				}
				HashSet<String> hs = new HashSet<String>();
				
				hs.addAll(tblist);
				
				for(int j : remVal){
					System.out.println(j);
					//tblist.remove(j);
					
					hs.remove(tblist.get(j));
					
				}
		
				System.out.println("hs:::"+hs);
				tablen=hs.size();
				System.out.println("Drop&tablelen:::"+tablen);
				
				System.out.println("remaining tables:"+tblist);
				if(hs != null && !hs.isEmpty() ){
					for(String remaintab: hs){
						System.out.println("remain::::"+remaintab);
						temp = con.wholemig(jdbcurl, usernamejdbc, jdbcpassword, remaintab, mongourl, usernamemongo,mongopassword, sqldb);
					}
					
					System.out.println("temp::Continue::"+temp);
				}else{
					System.out.println("All tables are already existed in MongoDB");
					response.getWriter().write("tablemigrated");
				}
				
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 if (temp.equalsIgnoreCase("migration")){
				
				int count =Connectors.count;
				 String collength ="";
				 collength = Integer.toString(count);
				System.out.println("collength:::"+collength);
				String tlen = Integer.toString(tablen);
				
				response.getWriter().write("success"+"@"+collength+"@"+tlen);
			}
				
			else{
				response.getWriter().write("fail");
			}

	}

}
