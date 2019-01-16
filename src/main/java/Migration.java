

import java.io.File;
import java.io.IOException;
import java.io.PrintStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mysql.jdbc.PreparedStatement;

/**
 * Servlet implementation class Migration
 */
@WebServlet("/Migration")
public class Migration extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	static String sqldb,mongodb,Path; 
	static PrintStream ps;
	
    public Migration() {
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
		String jdbcurl=""; 
		String usernamejdbc=""; 
		String jdbcpassword="";
		String mongourl=""; 
		String usernamemongo="";
		String mongopassword="",appID="";
		
		
		 sqldb = request.getParameter("sqldb")+"";
		 mongodb = request.getParameter("mongodb")+"";
		 
		 Testsource tst = new Testsource();
		 
		/* Save file in Real path and write*/
		 Path = getServletContext().getRealPath("/");
		 String xmlFilePath=Path+"hello.txt";
		 System.out.println(xmlFilePath);
		 ps = new PrintStream(new File(xmlFilePath)); 
		// Store current System.out before assigning a new value 
	    PrintStream console = System.out; 
	    System.setOut(ps);
	    ps.flush();
	  
	    /* End of Save file in Real path and write*/
	    
		 if((sqldb.trim(). equalsIgnoreCase("mysql")) ||(sqldb.trim().equalsIgnoreCase("SQL Server")) || (sqldb.trim().equalsIgnoreCase("Oracle")) ){
		 jdbcurl = request.getParameter("jdbcurl");
		 usernamejdbc = request.getParameter("usernamejdbc");
		 jdbcpassword = request.getParameter("jdbcpassword");
		 appID = request.getParameter("appID");
		 
		 
		 String tstsrc="";
		 String databasesize = "";
		 try {
			 tstsrc = tst.testsrc(jdbcurl, usernamejdbc, jdbcpassword, "", sqldb);
			 System.out.println("tstsrc:::"+tstsrc);
			 
			 				/* DB Size*/
			 Connection con=DriverManager.getConnection(jdbcurl,usernamejdbc,jdbcpassword);  
			 Statement stmt=con.createStatement();  
				
				String mysqldb = con.getCatalog();
				 
				 System.out.println("Mysql db name::::::"+mysqldb);
					    String sql = "SELECT table_schema AS \"Database\", "
					        + "ROUND(SUM(data_length + index_length) / 1024 / 1024, 2) AS \"Size (MB)\" "
					        + "FROM information_schema.TABLES WHERE table_schema = '"+mysqldb+ "'"; 
					     
					    PreparedStatement statement = (PreparedStatement) con.prepareStatement(sql);
					    ResultSet rs1=statement.executeQuery();
						 while (rs1.next()) {
							    System.out.println(rs1.getString("Database") +" | " + rs1.getDouble("Size (MB)"));
							    databasesize= Double.toString(rs1.getDouble("Size (MB)"));
							}
						 /* End of DB Size*/
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 if(tstsrc.equalsIgnoreCase("success"))
			 response.getWriter().write("success"+"@"+databasesize);
			 else
				 response.getWriter().write("fail");
		}
		
		
		
		else if(mongodb.equalsIgnoreCase("mongodb")){
		 
		 mongourl = request.getParameter("mongourl");
		 usernamemongo = request.getParameter("usernamemongo");
		 mongopassword = request.getParameter("mongopassword");
		 
		 String testtar="";
				 testtar = tst.testtarget(mongourl, usernamemongo, mongopassword, mongodb);
				 System.out.println("testtar::"+testtar);
				 if(testtar.equalsIgnoreCase("success"))
					 response.getWriter().write("success");
				 else
					 response.getWriter().write("fail");
	
		}

		
/*		System.out.println("sqldb:::"+sqldb+"\n"+"jdbc::"+jdbcurl+"\n"+"usernamejdbc:::"+usernamejdbc
				 +"\n"+"jdbcpassword:::"+jdbcpassword+"\n"+"jdbctname:::"+jdbctablename+"\n"+"mongodb::::"+mongodb+"\n"+"mongourl:::"+mongourl
		 		 +"\n"+"usernamemongo:::"+usernamemongo+"\n"+"mongopassword:::"+mongopassword);*/
		 
		 
		 
	}

}
