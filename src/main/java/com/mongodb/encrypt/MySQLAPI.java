package com.mongodb.encrypt;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;


import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.apache.spark.storage.StorageLevel;
import org.bson.Document;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.JSONArray;
import com.mongodb.spark.MongoSpark;

public class MySQLAPI {
	public static int mysqltablecount;

	private SparkSession spark;
	//private Dataset<Row> mongoData;

	public static void main(String args[]) throws ClassNotFoundException, SQLException {
		/*
		 * Class.forName("com.mysql.jdbc.Driver"); Connection con =
		 * DriverManager.getConnection(
		 * "jdbc:mysql://sotero-perftest.cqzcaawwqsmw.us-east-1.rds.amazonaws.com:3306/dbtest",
		 * "root", "Exafluence201"); String query = "SHOW TABLES IN dbtest";
		 * Statement stmt = con.createStatement(); DatabaseMetaData md =
		 * con.getMetaData(); ResultSet rs = md.getTables(null, null, "%",
		 * null); while (rs.next()) { System.out.println(rs.getString(3)); }
		 */

		MySQLAPI mysql = new MySQLAPI();
/*		mysql.mysql2api("jdbc:mysql://sotero-perftest.cqzcaawwqsmw.us-east-1.rds.amazonaws.com:3306/dbtest_new", "root",
				"Exafluence201", "4");*/
		mysql.mysql2api("jdbc:mysql://targetschema-new.cqzcaawwqsmw.us-east-1.rds.amazonaws.com:3306/perf_test_src", "secured",
				"pwd4secured", "4","localhost:27017/Sotero","","");
		

	}

	public void mysql2api(String url, String user, String pwd, String appID,String mongourl, String mongouser, String mongopwd) {
		String mongoURL = mongourl.split("\\:")[0];
		String dup = mongourl.split("\\:")[1];
		String port = dup.split("/")[0]; 
		String db = dup.split("/")[1];
		
		spark = SparkSession.builder().appName("MongoDB").master("local[*]")
				.config("spark.mongodb.input.uri", "mongodb://"+mongoURL+":"+port+"/"+db)
				.config("spark.mongodb.output.uri", "mongodb://"+mongoURL+":"+port+"/"+db).getOrCreate();
		ArrayList<String> tableList = new ArrayList<String>();
		List<String> tableDetails = new ArrayList<String>();
		String encTable = "";
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection con = DriverManager.getConnection(url, user, pwd);
			DatabaseMetaData md = con.getMetaData();

			ResultSet rs = md.getTables(null, null, "%", null);
			while (rs.next()) {
				tableList.add(rs.getString(3));
			}
			rs.close();
			
			for (String table : tableList) {
				JSONObject jsonObj = new JSONObject();
				JSONArray jsonarray = new JSONArray();
				JSONObject json = new JSONObject();
				String query = "SELECT * FROM " + table + " limit 500";
				Statement stmt = con.createStatement();
				ResultSet rsCol = stmt.executeQuery(query);
				ResultSetMetaData rsmd = rsCol.getMetaData();
				int rscount = rsmd.getColumnCount();
				JSONArray jsonarr2 = new JSONArray();
				while (rsCol.next()) {
					int assign = 0;
					// JSONObject temp = new JSONObject();
					LinkedHashMap<String, Object> temp = new LinkedHashMap<String, Object>();
					for (int i = 1; i <= rscount; i++) {
						temp.put(String.valueOf(assign), rsCol.getObject(i));
						assign++;

					}

					jsonarray.add(temp);

				}
				// Getting tableDetails
				rs = md.getColumns(null, null, table, "%");
				while (rs.next()) {
					// Column Names= rs.getString(4) Column
					// Schema=rs.getString(6)
					for (int j = 1; j <= rscount; j++) {
						if (rsmd.getColumnName(j).toUpperCase().equalsIgnoreCase(rs.getString(4).toUpperCase())) {
							//tableDetails.add(rs.getString(4) + "@" + rs.getString(6));
							tableDetails.add(rs.getString(6));
							break;
						}
					}
				}
				rs.close();

				for (int j = 1; j <= rscount; j++) {
					JSONObject temp1 = new JSONObject();
					temp1.put("name", rsmd.getColumnName(j));
					temp1.put("tablename", table);
					jsonarr2.add(temp1);
				}
				json.put("RECORDS", jsonarray);
				json.put("COLUMNS", jsonarr2);
				json.put("aid", appID);
				json.put("tid", "0");

				System.out.println(json);
				JSONObject jsonout = readJsonFromUrl("http://localhost:45670/path7", json);
				System.out.println(jsonout);
				Document doc = Document.parse(jsonout.toString());
				ArrayList<Document> columns = (ArrayList<Document>) doc.get("COLUMNS");

				List<String> columnames = new ArrayList<String>();
				for (int i = 0; i < columns.size(); i++) {
					Document doc2 = columns.get(i);
					columnames.add(doc2.get("name").toString()+"@"+tableDetails.get(i));
					encTable = doc2.get("tablename").toString();
				}

				List<Row> data2 = new ArrayList<Row>();
				List<Row> arrfulldata = new ArrayList<Row>();

				ArrayList<Document> records = (ArrayList<Document>) doc.get("RECORDS");
				for (int i = 0; i < records.size(); i++) {
					Document doc2 = records.get(i);
					Set<String> keys = doc2.keySet();
					String val = "";

					ArrayList<Integer> set = new ArrayList<Integer>();
					for (String s : keys)
						set.add(Integer.parseInt(s));
					Collections.sort(set);

					for (int keyset : set)
						val += doc2.get(String.valueOf(keyset)) + ",";

					data2 = Arrays.asList(RowFactory.create(val.split(",")));
					arrfulldata.addAll(data2);
				}

				StructType schema = createSchema(columnames);
				
				Dataset<Row> mongoData = spark.createDataFrame(arrfulldata, schema).persist(StorageLevel.MEMORY_AND_DISK());
				arrfulldata.clear();
				for (String s : mongoData.columns()) {

					for (String ss : columnames) {
						String key = ss.split("@")[0];
						String value = ss.split("@")[1];

						if (key.toUpperCase().equalsIgnoreCase(s.toUpperCase())) {
							if (value.toUpperCase().contains("CHAR") || value.toUpperCase().contains("VARCHAR")
									|| value.toUpperCase().contains("TEXT") || value.toUpperCase().contains("TINYTEXT")
									|| value.toUpperCase().contains("MEDIUMTEXT")
									|| value.toUpperCase().contains("LONGTEXT") || value.toUpperCase().contains("ENUM")
									|| value.toUpperCase().contains("BLOB") || value.toUpperCase().contains("TINYBLOB")
									|| value.toUpperCase().contains("MEDIUMBLOB")
									|| value.toUpperCase().contains("LONGBLOB")) {
								
								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.StringType));
								
							} else if (value.toUpperCase().contains("INT") || value.toUpperCase().contains("TINYINT")
									|| value.toUpperCase().contains("SMALLINT")
									|| value.toUpperCase().contains("MEDIUMINT")
									|| value.toUpperCase().contains("BIGINT")) {
								
								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.IntegerType));
								
							} else if (value.toUpperCase().contains("FLOAT")) {

								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.FloatType));
								;

							} else if (value.toUpperCase().contains("DOUBLE")) {

								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.DoubleType));

							} else if (value.toUpperCase().contains("DECIMAL")) {

								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.DoubleType));

							} else if (value.toUpperCase().contains("TIMESTAMP") || value.toUpperCase().contains("TIME")
									|| value.toUpperCase().contains("YEAR")) {

								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.TimestampType));

							} else if (value.toUpperCase().contains("DATE") || value.toUpperCase().contains("DATETIME")) {

								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.DateType));

							} else if (value.toUpperCase().contains("MONEY")) {

								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.DoubleType));

							} else {

								mongoData = mongoData.withColumn(s, mongoData.col(s).cast(DataTypes.StringType));

							}
						}
					}

				}
				
				MongoSpark.write(mongoData).option("collection", encTable).mode(SaveMode.Append).save();
				encTable = "";
				//mongoData.unpersist();
				mongoData = spark.emptyDataFrame();
				
				
			
			} 

			if(rs!=null)
				rs.close();
			if(con!=null)
				con.close();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (ClassCastException e) {
			// e.printStackTrace();
			throw e;
		}/*finally{
			mongoData.unpersist();
		}*/

	}


	public static JSONObject readJsonFromUrl(String url, JSONObject jsonip)
			throws IOException, java.text.ParseException {
		// InputStream is = new URL(url).openStream();
		URL object = new URL(url);
		JSONObject jsonOut = null;
		HttpURLConnection con = (HttpURLConnection) object.openConnection();
		try {

			con.setDoOutput(true);
			con.setDoInput(true);
			con.setRequestProperty("Content-Type", "application/json");
			con.setRequestProperty("Accept", "application/json");
			con.setRequestMethod("POST");
			OutputStream os = con.getOutputStream();
			os.write(jsonip.toString().getBytes());
			os.flush();
			BufferedReader rd = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String jsonText = readAll(rd);
			jsonOut = new JSONObject(jsonText);

		} catch (JSONException e) {
			e.printStackTrace();
		}

		finally {

			con.disconnect();
		}
		return jsonOut;
	}

	/**
	 * This method reads the API output as a Reader and reads line by and
	 * converts to the String.
	 * 
	 * @param rd
	 *            Reader from the API output
	 * @return String sb
	 * @throws IOException
	 */
	public static String readAll(Reader rd) throws IOException {
		StringBuilder sb = new StringBuilder();
		int cp;
		while ((cp = rd.read()) != -1) {
			sb.append((char) cp);
		}
		return sb.toString();
	}

	public static StructType createSchema(List<String> tableColumns) {

		List<StructField> fields = new ArrayList<StructField>();
		// for(String column : tableColumns){
		for (String allSchema : tableColumns) {
			String key = allSchema.split("@")[0];
			String value = allSchema.split("@")[1];

			fields.add(DataTypes.createStructField(key, DataTypes.StringType, false));
		}
		return DataTypes.createStructType(fields);
	}

}
