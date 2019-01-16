
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.encrypt.MySQLAPI;

import org.json.JSONException;
import org.json.JSONObject;




/**
 * Servlet implementation class whole
 */
@WebServlet("/whole")
public class whole extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public whole() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at:
		// ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		String jdbcurl = "";
		String usernamejdbc = "";
		String jdbcpassword = "";
		String mongourl = "";
		String usernamemongo = "";
		String mongopassword = "", jdbctablename = "",appID="";
		/* String val = request.getParameter("val"); */
		String sqldb = request.getParameter("sqldb") + "";
		String mongodb = request.getParameter("mongodb") + "";
		jdbcurl = request.getParameter("jdbcurl");
		usernamejdbc = request.getParameter("usernamejdbc");
		jdbcpassword = request.getParameter("jdbcpassword");
		mongourl = request.getParameter("mongourl");
		usernamemongo = request.getParameter("usernamemongo");
		mongopassword = request.getParameter("mongopassword");
		jdbctablename = request.getParameter("jdbctab");
		appID= request.getParameter("appID");
		System.out.println("connn:::::");
		Connectors con = new Connectors();
		System.out.println("com11111");

		/////////////// Test source//////
		String tstsrc = "", testtar = "";

		Testsource tst = new Testsource();
		try {
			tstsrc = tst.testsrc(jdbcurl, usernamejdbc, jdbcpassword, jdbctablename, sqldb);
			testtar = tst.testtarget(mongourl, usernamemongo, mongopassword, mongodb);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//////////// end test source/////
		
		
		/*
		 * Check table name exists or not
		 * 
		 */
		int tablelength = 0;
		ArrayList<String> tblnames = new ArrayList<String>();
		ArrayList<String>duplitab = new ArrayList<>();
		String duptablist = "";
		try {
			tblnames = tablelist(jdbcurl, usernamejdbc, jdbcpassword);
			tablelength = tblnames.size();
			System.out.println("tblnames length:::"+tablelength);
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		
		String url = mongourl.split(":")[0];
		String portDatabase = mongourl.split(":")[1];
		String port = portDatabase.split("/")[0];
		String database = mongourl.split("/")[1];
		String mongoConnectionString = "mongodb://"+usernamemongo+":"+mongopassword+"@"+url+":"+port+"/?authSource="+database;
		//MongoClient client = new MongoClient(new MongoClientURI(mongoConnectionString));
		MongoClient client;
		if(!usernamemongo.equalsIgnoreCase("") && !usernamemongo.equalsIgnoreCase(null) && !mongopassword.equalsIgnoreCase("") && !mongopassword.equalsIgnoreCase(null))
			client = new MongoClient(new MongoClientURI("mongodb://"+usernamemongo+":"+mongopassword+"@"+url+":"+port+"/?authSource="+database));
		else
			client = new MongoClient(url, Integer.parseInt(port));
		MongoDatabase db = client.getDatabase(database);
		try {
			
			MongoIterable<String> collection = db.listCollectionNames();
			int len = 0;
			for (int i = 0; i < tblnames.size(); i++) {
				for (String collectionName : collection) {
					//System.out.println("collectionName::" + collectionName);
					len =collectionName.length();
		        	 if(len != 0){
					if (tblnames.get(i).equalsIgnoreCase(collectionName)) {

						System.out.println(tblnames.get(i) + "=====" + collectionName);
						 duplitab.add(tblnames.get(i));
						break;
					}
				
					
		        	 }
				}
				
				
			}
			
			System.out.println("Collectionlength::"+len);
			System.out.println("duplitab:::"+duplitab);
			duplitab.size();
			System.out.println("length of collections:::"+duplitab.size());
			System.out.println("tblnames length:::"+tablelength);
			
			
			/*
			 * Converting duplicate table arraylist to string
			 */
			 duptablist=  String.join(", ", duplitab);
	        System.out.println(duptablist);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			client.close();
		}
		
			/*    end of checking table name   */
		
		
		/*
		 * If both test source and test target become success then only data get migrated into Mongodb
		 */
		//remove this if not working
		int length = duplitab.size();
		
		if(length == 0){
		//Sotero Conditional Check
			if(appID.equalsIgnoreCase("") && appID.equalsIgnoreCase(null)){
			
		if (tstsrc.equalsIgnoreCase("success") && testtar.equalsIgnoreCase("success")) {
			String temp = "";
			
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection conn = null;
				con.loadProperties(usernamemongo, mongopassword, mongourl, jdbctablename);
				if (sqldb.toLowerCase().equalsIgnoreCase("mysql")) {
					conn = DriverManager.getConnection(jdbcurl, usernamejdbc, jdbcpassword);
					
					
					DatabaseMetaData md = conn.getMetaData();

					ResultSet tables = md.getTables(null, null, "%", null);
					boolean a = false;
					System.out.println("a:::" + a);

					while (tables.next()) {
						String table = tables.getString(3);
						System.out.println("table::" + table);
						System.out.println("jdbctname::::::::;" + jdbctablename);
						temp = con.wholemig(jdbcurl, usernamejdbc, jdbcpassword, table, mongourl, usernamemongo,
								mongopassword, sqldb);

						
					}
					
					
				} else {
					conn = DriverManager.getConnection(jdbcurl, usernamejdbc, jdbcpassword);
					DatabaseMetaData md = conn.getMetaData();

					ResultSet tables = md.getTables(null, null, "%", null);

					boolean a = false;
					System.out.println("a:::" + a);
					while (tables.next()) {
						String table = tables.getString(3);
						System.out.println("table::" + table);
						System.out.println("jdbctname::::::::;" + jdbctablename);
						temp = con.wholemig(jdbcurl, usernamejdbc, jdbcpassword, table, mongourl, usernamemongo,
								mongopassword, sqldb);

					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

			String colname ="";
			colname = Connectors.collectionName;
			colname = colname.substring(0, colname.length()-1);
			 System.out.println("colname:::::::::::"+colname);
			 
			
			System.out.println("whole:::::" + jdbcurl + usernamejdbc + jdbcpassword + jdbctablename + mongourl
					+ usernamemongo + mongopassword + sqldb);
			System.out.println("temp::" + temp);
			if (temp.equalsIgnoreCase("migration")){
				
				int count =Connectors.count;
				 String collength ="";
				 collength = Integer.toString(count);
				System.out.println("collength:::"+collength);
				String tblen= Integer.toString(tablelength);
				response.getWriter().write("success"+"@"+collength+"@"+tblen);
					
			}
				
			else{
				response.getWriter().write("fail");
			}

		} else {
			response.getWriter().write("fail");
		}
			
			}else{
				//Sotero Code Comes here
				MySQLAPI mysql = new MySQLAPI();
				
					mysql.mysql2api(jdbcurl, usernamejdbc, jdbcpassword, appID, mongourl, usernamemongo, mongopassword);
			
				if(!usernamemongo.equalsIgnoreCase("") && !usernamemongo.equalsIgnoreCase(null) && !mongopassword.equalsIgnoreCase("") && !mongopassword.equalsIgnoreCase(null))
					client = new MongoClient(new MongoClientURI("mongodb://"+usernamemongo+":"+mongopassword+"@"+url+":"+port+"/?authSource="+database));
				else
					client = new MongoClient(url, Integer.parseInt(port));
				MongoDatabase db2 = client.getDatabase(database);
				MongoIterable<String> collection = db2.listCollectionNames();
				int mongoCollCount = 0;
				for(String coll : collection){
					mongoCollCount++;
				}
				response.getWriter().write("success"+"@"+MySQLAPI.mysqltablecount+"@"+mongoCollCount);
			}
		
		}else{
			response.getWriter().write(duptablist);
		}
		
		
		
	}
	
	/*
	 * Getting the table names from mysql 
	 * 
	 */
	
	public static ArrayList<String> tablelist(String jdbcurl, String jdbcuser, String jdbcpwd)
			throws ClassNotFoundException, SQLException {

		ArrayList<String> tablelist = new ArrayList<>();

		Class.forName("com.mysql.jdbc.Driver");

		Connection conn = DriverManager.getConnection(jdbcurl, jdbcuser, jdbcpwd);

		DatabaseMetaData md = conn.getMetaData();

		ResultSet tables = md.getTables(null, null, "%", null);
		boolean a = false;
		System.out.println("a:::" + a);

		while (tables.next()) {
			String table = tables.getString(3);
			System.out.println("table::" + table);

			tablelist.add(table);

		}

		return tablelist;
	}
	

}
