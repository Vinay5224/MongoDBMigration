
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Projections;

/**
 * Servlet implementation class MigrationStatus
 */
@WebServlet("/MigrationStatus")
public class MigrationStatus extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MigrationStatus() {
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
		String mongopassword = "", jdbctablename = "", unchktabname = "";

		String sqldb = request.getParameter("sqldb") + "";
		String mongodb = request.getParameter("mongodb") + "";
		jdbcurl = request.getParameter("jdbcurl");
		usernamejdbc = request.getParameter("usernamejdbc");
		jdbcpassword = request.getParameter("jdbcpassword");
		jdbctablename = request.getParameter("jdbctab");
		mongourl = request.getParameter("mongourl");
		usernamemongo = request.getParameter("usernamemongo");
		mongopassword = request.getParameter("mongopassword");

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

		Connection conn = null;
		String html = "";
		long collreccount;
		ArrayList<String> tablelist = new ArrayList<>();
		ArrayList<String> collist = new ArrayList<>();
		ArrayList<String> duplitab = new ArrayList<>();
		ArrayList<Long> collength = new ArrayList<>();

		try {
			conn = DriverManager.getConnection(jdbcurl, usernamejdbc, jdbcpassword);
			String mysqldb = conn.getCatalog();
			System.out.println("mysqldatabase:::" + mysqldb);

			DatabaseMetaData md = conn.getMetaData();
			ResultSet tables = md.getTables(null, null, "%", null);
			if (sqldb.toLowerCase().equalsIgnoreCase("mysql")) {
				html = "<div class='container'><div class='row'><br><div class='col-sm-6'><div class='panel panel-primary'><div class='panel-heading' ><strong>MYSQL</strong></div><table class='table table-bordered'><thead><tr><th>Table Name</th><th>Record count</th><th>Attributes Names</th><th>Data type</th></tr></thead><tbody>";
				while (tables.next()) {
					html += "<tr>";
					String table1 = tables.getString(3);
					System.out.println("table::" + table1);
					tablelist.add(table1);
					// System.out.println("jdbctname::::::::;" + jdbctablename);
					Statement stmt = conn.createStatement();

					/* Attribute count for each table and table name */
					ResultSet rs1 = stmt
							.executeQuery("SELECT COUNT(*) FROM INFORMATION_SCHEMA.COLUMNS WHERE table_schema = '"
									+ mysqldb + "' AND table_name = '" + table1 + "'");
					while (rs1.next()) {
						//System.out.println(rs1.getString(1));
						int colcount = Integer.parseInt(rs1.getString(1)) + 1;
						html += "<td rowspan='" + colcount + "'>" + table1 + "</td><td rowspan='" + colcount + "'>";

					}

					/* record count for each table */
					ResultSet rs2 = stmt.executeQuery(
							"select count(*) from " + table1 );
					while (rs2.next()) {
						html += rs2.getString(1) + "</td>";
						//System.out.println("tab:::" + rs2.getString(1));
						//System.out.println("tab:::" + rs2.getString(3) + "-------	" + rs2.getString(8));
					}

					/* Attribute name and Data type for each Attribute */
					ResultSet rs = stmt
							.executeQuery("select * from INFORMATION_SCHEMA.COLUMNS where TABLE_NAME='" + table1 + "'");
					while (rs.next()) {
						//System.out.println(
								//rs.getString(3) + "-------	" + rs.getString(4) + "	--------	" + rs.getString(16));
						html += "<tr><td>" + rs.getString(4) + "</td><td>" + rs.getString(16) + "</td></tr>";

					}

				}
				//System.out.println(html);
				conn.close();
			} else {

			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String url = mongourl.split(":")[0];
		String portDatabase = mongourl.split(":")[1];
		String port = portDatabase.split("/")[0];
		String database = mongourl.split("/")[1];
		String mongoConnectionString = "mongodb://" + usernamemongo + ":" + mongopassword + "@" + url + ":" + port
				+ "/?authSource=" + database;
		MongoClient client = new MongoClient(new MongoClientURI(mongoConnectionString));

		String newString = "";
		int fieldlen = 0;
		Set<String> fieldnames = null;
		Class<? extends Object> data = null;
		try {
			MongoDatabase db = client.getDatabase(database);
			MongoIterable<String> collection = db.listCollectionNames();

			html += "</tbody></table></div></div><div class='col-sm-6'><div class='panel panel-primary'><div class='panel-heading' ><strong>Mongo DB</strong></div><table class='table table-bordered'><thead><tr><th>Collection Name</th><th>Record count</th><th>Field Names</th><th>Data type</th></tr></thead><tbody>";

			for (int i = 0; i < tablelist.size(); i++) {
				for (String collectionName : collection) {
					// System.out.println("collectionName::" + collectionName);

					if (tablelist.get(i).equalsIgnoreCase(collectionName)) {
						html += "<tr>";
						System.out.println(tablelist.get(i) + "=====" + collectionName);
						duplitab.add(tablelist.get(i));
						MongoCollection<Document> coll = db.getCollection(tablelist.get(i));

						Document collStats = db.runCommand(new Document("collStats", tablelist.get(i)));
						System.out.println(collStats.get("size"));

						BasicDBObject allQuery = new BasicDBObject();
						FindIterable<Document> iterDoc = coll.find(allQuery).projection(Projections.excludeId());
						int j = 1;

						// Getting the iterator
						Iterator<Document> it = iterDoc.iterator();
						//////// *field names*//////////
						Document doc = null;
						while (it.hasNext()) {
							doc = it.next();
							fieldnames = doc.keySet();
						}
						/* field count for each collection */
						fieldlen = fieldnames.size() + 1;

						html += "<td rowspan='" + fieldlen + "'>" + tablelist.get(i) + "</td><td rowspan='" + fieldlen
								+ "'>";

						/* record count for each collection */
						collreccount = coll.count();
						html += collreccount + "</td>";

						collength.add(collreccount);

						/*
						 * Field names for each collection and Data type for
						 * each field
						 */
						for (String str : fieldnames) {
							data = doc.get(str).getClass();
							String datatype = data.toString();
							String my = datatype.split("\\. ")[0];
							String myd = my.split("\\.")[2];

							System.out.println("fieldnames itr:::" + str + "--------" + myd);
							html += "<tr><td>" + str + "</td><td>" + myd + "</td></tr>";

						}

						System.out.println(tablelist.get(i) + "=====" + collectionName);

						System.out.println(tablelist.get(i) + "------" + collreccount);
						// System.out.println("collstats:::"+collStats);
						System.out.println("fieldnames:::" + fieldnames);

						System.out.println("fieldlen:::::" + fieldlen);
						break;
					}
				}
			}
			html += "</tbody></table></div></div></div>";
			//System.out.println(html);

			System.out.println("duplitab:::" + duplitab);

			duplitab.size();
			System.out.println("length of collections:::" + duplitab.size());
			System.out.println("duplen:::" + collength);

		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			client.close();
		}

		if (testtar.equalsIgnoreCase("success") && tstsrc.equalsIgnoreCase("success")) {
			response.getWriter().write("success" + "@" + html);
		}

		else {
			response.getWriter().write("fail");
		}

	}

}
