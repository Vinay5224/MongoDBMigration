import java.io.File;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
/*import org.bson.codecs.configuration.CodecRegistries.fromCodecs;*/
import com.mongodb.spark.MongoSpark;
import com.mysql.jdbc.PreparedStatement;

public class Connectors {

	public static SparkSession spark;
	public static String collectionName="";
	public static int migCount = 1, count = 0;

	public static void loadProperties(String usernamemongo, String mongopassword, String mongodburl,
			String jdbctbname) {
		migCount = 1;
		Logger.getLogger("org").setLevel(Level.OFF);
		Logger.getLogger("akka").setLevel(Level.OFF);

		Properties prop1 = new Properties();
		prop1.put("user", usernamemongo);
		prop1.put("password", mongopassword);

		spark = SparkSession.builder().appName("Java Spark Migration").master("local")
				.config("spark.mongodb.input.uri",
						"mongodb://" + usernamemongo.trim() + ":" + mongopassword.trim() + "@" + mongodburl.trim() + "."
								+ jdbctbname.trim())
				.config("spark.mongodb.output.uri", "mongodb://" + usernamemongo.trim() + ":" + mongopassword.trim()
						+ "@" + mongodburl.trim() + "." + jdbctbname.trim())
				.getOrCreate();
	}

	public static String wholemig(String jdbcurl, String jdbcusr, String jdbcpwd, String jdbctbname, String mongodburl,
			String usernamemongo, String mongopassword, String dbtype) throws SQLException {
		Properties prop = new Properties();
		prop.put("user", jdbcusr);
		prop.put("password", jdbcpwd);
		Dataset<Row> inputset;
		System.out.println("dbtype:::" + dbtype);
		String allcolumns = "", str = "";

		////////// mysql////////////////
		if (dbtype.equalsIgnoreCase("mysql")) {

			/////////////// datatype/////////
			try {
				Class.forName("com.mysql.jdbc.Driver");
				Connection con = DriverManager.getConnection(jdbcurl, jdbcusr, jdbcpwd);

				Statement stmt = con.createStatement();
				 
				ResultSet rs = stmt.executeQuery("explain " + jdbctbname);
				
				String Decimaldata, attr = "";
				while (rs.next()) {
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "		" + rs.getString(3));

					Decimaldata = rs.getString(2);
					attr = rs.getString(1);
					// System.out.println("De::"+Decimaldata);

					// String statement = "decimal(15,2)";
					Pattern pattern = Pattern.compile("(\\w+).*");
					Matcher m = pattern.matcher(Decimaldata);
					m.find();
					//System.out.println(m.group(1));
					String type = m.group(1);
					// String type=Decimaldata.split("")[0];
					// System.out.println("types::"+type);
					if (type.toLowerCase().equalsIgnoreCase("decimal")) {
						System.out.println("type");
						str += "CAST ( " + attr + " AS DOUBLE), ";
					} else {

						allcolumns += attr + ",";
					}

				}

				//System.out.println("lineitem::" + str);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/////////////////////////// end datatype///////
			
			inputset = spark.read().jdbc(jdbcurl, jdbctbname, prop).na().fill("");
			if (str != null && str != "") {
				str = str.substring(0, str.length() - 2);
				inputset.createOrReplaceTempView("db");
				Dataset<Row> insert = spark.sql("SELECT " + allcolumns + str + " FROM db");
				insert.printSchema();
				//insert.show(9);
				MongoSpark.write(insert).format("com.mongodb.spark.sql").option("collection", jdbctbname.trim())
						.mode("append").save();
				
				collectionName += jdbctbname+",";
				count = migCount++;
				System.out.println("insertion  completed");
				//System.out.println("migcount:::" + count);
				//System.out.println("collectionName::::::::"+collectionName);

			} else {
				inputset.createOrReplaceTempView("db");
				Dataset<Row> insert = spark.sql("SELECT * FROM db");
				//insert.show(9);
				MongoSpark.write(insert).format("com.mongodb.spark.sql").option("collection", jdbctbname.trim())
						.mode("append").save();
				collectionName += jdbctbname+",";
				count = migCount++;
				System.out.println("insertion  completed");
				//System.out.println("migrcount:::" + count);
				//System.out.println("collectionName::::::::"+collectionName);
			}
			// spark.close();
		}
		////////////// end mysql///////////////////
		
		
		//////////// sqlserver//////////////
		else {

			try {
				Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
				Connection con = DriverManager.getConnection(jdbcurl, jdbcusr, jdbcpwd);
				// here sonoo is database name, root is username and password
				Statement stmt = con.createStatement();
				ResultSet rs = stmt
						.executeQuery("select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME='" + jdbctbname + "'");

				String Decimaldata, attr = "";
				while (rs.next()) {
					System.out.println(rs.getString(1) + "	" + rs.getString(2) + "		" + rs.getString(3));

					Decimaldata = rs.getString(8);
					attr = rs.getString(4);
					System.out.println("attr::::" + attr);

					Pattern pattern = Pattern.compile("(\\w+).*");
					Matcher m = pattern.matcher(Decimaldata);
					m.find();
					System.out.println(m.group(1));
					String type = m.group(1);

					if (type.toLowerCase().equalsIgnoreCase("decimal")
							|| (type.toLowerCase().equalsIgnoreCase("money"))) {
						System.out.println("type");

						str += "CAST ( " + attr + " AS DOUBLE), ";

					} else {

						allcolumns += attr + ",";
					}

				}

				System.out.println("lineitem::" + str);

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("double type:" + str);

			System.out.println("jdbcurl::" + jdbcurl + "jdbctbname:::" + jdbctbname + "prop:::" + prop);
			inputset = spark.read().jdbc(jdbcurl.trim(), jdbctbname.trim(), prop);
			if (str != null && str != "") {
				str = str.substring(0, str.length() - 2);
				inputset.createOrReplaceTempView("db");
				System.out.println("test");
				// Dataset<Row> insert = spark.sql("SELECT t.*, "+str+" FROM db
				// t");
				Dataset<Row> insert = spark.sql("SELECT " + allcolumns + str + "  FROM db");
				insert.printSchema();
				MongoSpark.write(insert).format("com.mongodb.spark.sql").option("collection", jdbctbname.trim())
						.mode("append").save();
				collectionName += jdbctbname+",";
				count = migCount++;
				System.out.println("insertion  completed");
				System.out.println("migrcount:::" + count);
				System.out.println("collectionName::::::::"+collectionName);

			} else {
				inputset.createOrReplaceTempView("db");
				Dataset<Row> insert = spark.sql("SELECT * FROM db");
				MongoSpark.write(insert).format("com.mongodb.spark.sql").option("collection", jdbctbname.trim())
						.mode("append").save();
				
				collectionName += jdbctbname+",";
				count = migCount++;
				System.out.println("insertion  completed");
				System.out.println("migrcount:::" + count);
				System.out.println("collectionName::::::::"+collectionName);
			}
			

			/////// end sqlserver//////

		}
		return "migration";

	}

}
