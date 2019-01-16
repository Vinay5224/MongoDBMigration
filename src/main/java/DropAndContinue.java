
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

import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/**
 * Servlet implementation class DropAndContinue
 */
@WebServlet("/DropAndContinue")
public class DropAndContinue extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DropAndContinue() {
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
		/*
		 * Get the parameters of checked and unchecked table names from the
		 * frontend
		 */
		String chktabname = request.getParameter("chktabname").trim();
		chktabname = chktabname.substring(0, chktabname.length() - 1);
		System.out.println("chktabname::" + chktabname);

		String[] chklist = chktabname.split(",");
		// String[] unchklist = unchktabname.split(",");

		String url = mongourl.split(":")[0];
		String portDatabase = mongourl.split(":")[1];
		String port = portDatabase.split("/")[0];
		String database = mongourl.split("/")[1];
		String mongoConnectionString = "mongodb://" + usernamemongo + ":" + mongopassword + "@" + url + ":" + port
				+ "/?authSource=" + database;
		MongoClient client = new MongoClient(new MongoClientURI(mongoConnectionString));
		MongoDatabase db = client.getDatabase(database);

		for (String str : chklist) {

			MongoCollection<Document> dropColl = db.getCollection(str.trim());
			dropColl.drop();
		}

		Connectors con = new Connectors();
		con.loadProperties(usernamemongo, mongopassword, mongourl, jdbctablename);
		String temp = "";

		ArrayList<String> tblist = new ArrayList<>();
		// ArrayList<String> notunchktab = new ArrayList<>();

		// String notunchktablist = "";
		whole wle = new whole();
		System.out.println("jdbcurl:::" + jdbcurl);

		ArrayList<Integer> remVal = new ArrayList<Integer>();

		unchktabname = request.getParameter("unchktabname").trim();
		ArrayList<String> unchklist = new ArrayList<String>();

		if (unchktabname != null && unchktabname != "") {

			unchktabname = unchktabname.substring(0, unchktabname.length() - 1);
			System.out.println("unchktabname::" + unchktabname);

			unchklist = new ArrayList<String>(Arrays.asList(unchktabname.split(",")));
			System.out.println("unchklist:::" + unchklist.toString());
		}

		int existab = unchklist.size();
		System.out.println("existab:::" + existab);
		int tablen = 0;

		try {
			tblist = wle.tablelist(jdbcurl, usernamejdbc, jdbcpassword);
			System.out.println("tblist::" + tblist);

			if (unchktabname != null && unchktabname != "") {
				for (int i = 0; i < tblist.size(); i++) {

					String str = tblist.get(i).trim();
					for (String rm : unchklist) {

						if (rm.trim().equalsIgnoreCase(str)) {
							System.out.println("removed::" + str);
							remVal.add(i);
							System.out.println();
							// break;
						}
					}

				}
			}
			HashSet<String> hs = new HashSet<String>();

			hs.addAll(tblist);
			System.out.println("hslist::" + hs);

			if (unchktabname != null && unchktabname != "") {
				for (int j : remVal) {
					System.out.println(j);
					// tblist.remove(j);

					hs.remove(tblist.get(j));

				}
			}
			System.out.println("hs:::" + hs);

			tablen = hs.size();
			System.out.println("Drop&tablelen:::" + tablen);
			// tblist.removeAll(unchklist);
			System.out.println("remaining tables:" + tblist);
			for (String remaintab : hs) {
				System.out.println("remain::::" + remaintab);
				temp = con.wholemig(jdbcurl, usernamejdbc, jdbcpassword, remaintab, mongourl, usernamemongo,
						mongopassword, sqldb);
			}

			System.out.println("temp::Drop::" + temp);
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (temp.equalsIgnoreCase("migration")) {

			int count = Connectors.count;
			String collength = "";
			collength = Integer.toString(count);
			System.out.println("collength:::" + collength);
			String tlen = Integer.toString(tablen);
			String exitab = Integer.toString(existab);

			response.getWriter().write("success" + "@" + collength + "@" + tlen + "@" + exitab);
		}

		else {
			response.getWriter().write("fail");
		}
	}

}
