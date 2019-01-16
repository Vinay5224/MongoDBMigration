import java.sql.Connection;

import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.sql.catalog.Database;
import org.bson.Document;
import org.spark_project.jetty.util.security.Credential;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoClientURI;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;

/*import org.bson.codecs.configuration.CodecRegistries.fromCodecs;*/
public class Testsource {
	
	public static void main(String [] args) throws ClassNotFoundException, SQLException{
		
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		  
		//step2 create  the connection object  
		Connection con=DriverManager.getConnection(  
		"jdbc:oracle:thin:@192.168.0.43:1521:test","system","test123");  
		
	}

	public  String testsrc(String url,String username, String password, String jdbctablename,String dbtype) throws ClassNotFoundException, SQLException{
		// TODO Auto-generated method stub
		/*Class.forName("oracle.jdbc.driver.OracleDriver");*/
		Class.forName("com.mysql.jdbc.Driver");
		//Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
	
		Connection conn= null;
		System.out.println("dbtype:::"+dbtype);
		if(dbtype.toLowerCase().equalsIgnoreCase("mysql")){
			 conn= DriverManager.getConnection(url,username,password);
			DatabaseMetaData md = conn.getMetaData();
		
			 ResultSet tables = md.getTables(null, null, "%", null);
			 
			/*
			 boolean a = false;
			 System.out.println("a:::"+a);
			while(tables.next()){
				String table = tables.getString(3);
				System.out.println("table::"+table);
				System.out.println("jdbctname::::::::;"+jdbctablename);
				if(jdbctablename.toLowerCase().equalsIgnoreCase(table.toLowerCase())){
					
					System.out.println("jdbctname:::"+jdbctablename+"------"+"table:::"+table);
					a = true;
					break;
				}
			}
			if(a)
			{
				return "success"; 
			}
			else{
				return "failure";
			}*/
		}else{
			//sql server
			System.out.println("sqlserver connection established");
			 conn= DriverManager.getConnection(url,username,password);
				DatabaseMetaData md = conn.getMetaData();
				
				 ResultSet tables = md.getTables(null, null, "%", null);
			
				 /*boolean a = false;
				 System.out.println("a:::"+a);
				while(tables.next()){
					String table = tables.getString(3);
					System.out.println("table::"+table);
					System.out.println("jdbctname::::::::;"+jdbctablename);
					if(jdbctablename.toLowerCase().equalsIgnoreCase(table.toLowerCase())){
						
						System.out.println("jdbctname:::"+jdbctablename+"------"+"table:::"+table);
						a = true;
						break;
					}
				}
				if(a)
				{
					return "success"; 
				}
				else{
					return "failure";
				}*/
			 
		}
	/*	else{
			
			System.out.println("oracle connection established");
			 conn= DriverManager.getConnection(url,username,password);
				DatabaseMetaData md = conn.getMetaData();
				 ResultSet tables = md.getTables(null, null, "%", null);
				 boolean a = false;
				 System.out.println("a:::"+a);
				while(tables.next()){
					String table = tables.getString(3);
					System.out.println("table::"+table);
					System.out.println("jdbctname::::::::;"+jdbctablename);
					if(jdbctablename.toLowerCase().equalsIgnoreCase(table.toLowerCase())){
						
						System.out.println("jdbctname:::"+jdbctablename+"------"+"table:::"+table);
						a = true;
						break;
					}
				}
				if(a)
				{
					return "success"; 
				}
				else{
					return "failure";
				}
		}
		*/
	
		System.out.println(conn);
		
		return "success";
	}
	
	@SuppressWarnings("resource")
	public String testtarget(String host,String username, String password, String dbtype){
		//String dbname ="";
		
		
		String url = host.split(":")[0];
		String val = host.split(":")[1];
		String[] vals = val.split("/");
		int port = Integer.parseInt(vals[0]);
		String database = vals[1];
		MongoClient mongoclient;
		if(!username.equalsIgnoreCase("") && !username.equalsIgnoreCase(null) && !password.equalsIgnoreCase("") && !password.equalsIgnoreCase(null))
			mongoclient = new MongoClient(new MongoClientURI("mongodb://"+username+":"+password+"@"+url+":"+port+"/?authSource="+database));
		else
			mongoclient = new MongoClient(url, port);
		
		String mongourl = "mongodb://"+username+":"+password+"@"+url+":"+port+"/?authSource="+database;
		System.out.println("Testtarurl:::"+"username::"+username+"\t"+"password::"+password+"\t"+"url:::"+url+"\t"+"port:::"+port+"\t"+"Database::"+database);
		//MongoClient mongoclient = new MongoClient(new MongoClientURI(mongourl));
		
		String test = "";
		
		try{
			MongoDatabase db = mongoclient.getDatabase(database);
			System.out.println(db);
			db.listCollectionNames();
			
			MongoIterable<String> collections = db.listCollectionNames();
	         for (String collectionName: collections) {
	             System.out.println(collectionName);
	         }
	         return "success";  
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return "fail";
	    } 
		finally {
			mongoclient.close();
	    }
		
	

		 
}
	
}