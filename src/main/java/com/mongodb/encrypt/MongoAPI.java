package com.mongodb.encrypt;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.bson.Document;
import org.json.JSONObject;
import org.json.simple.JSONArray;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.Projections;


public class MongoAPI {
	static MongoClient mongoClient = new MongoClient();
	static MongoDatabase mongoDatabase;
	static MongoCollection<Document> mongoColl;
	//static JSONObject mongoJson = new JSONObject();
	static String appID="",mongodata="";
	private ArrayList<String> encCollections= new ArrayList<String>();
	
	public static void main(String args[]){
		MongoAPI ap = new MongoAPI();
		//ap.mongoapi("localhost",27017, "", "","Sotero", "4");
		ap.findMongoObj("", "");
	
	}
	public void mongoProps(String mongourl, int port, String mongouser, String mongopwd, String mongoDB){
		if(!mongouser.equalsIgnoreCase("") && !mongouser.equalsIgnoreCase(null) && !mongopwd.equalsIgnoreCase("") && !mongopwd.equalsIgnoreCase(null))
			mongoClient = new MongoClient(new MongoClientURI("mongodb://"+mongouser+":"+mongopwd+"@"+mongourl+":"+port+"/?authSource="+mongoDB));
		else
			mongoClient = new MongoClient(mongourl, port);
		
		mongoDatabase = mongoClient.getDatabase(mongoDB);
		mongodata=mongoDB;
	}
	
	public String mongoapi(String mongourl,int port, String mongouser, String mongopwd,String mongoDB, String appId) {
		String html="",table="";
		appID = appId;
		
		mongoProps(mongourl,port,mongouser,mongopwd,mongoDB);
		html+="<a id='icon' href='#' ><span id='icon' class='glyphicon glyphicon-chevron-right'></span>"+mongoDB+"</a>";	
		MongoIterable<String> allCollections = mongoDatabase.listCollectionNames();
		try{
		html+="<div id='lst' align='left'><ul id='unlst'>";	
		for(String collNames : allCollections){
			//encCollections.add(collNames);
			table+="<tr> <td>";
			collNames = collNames.trim();
			MongoCollection<Document> colCount = mongoDatabase.getCollection(collNames);
			BufferedReader br=new BufferedReader(new InputStreamReader(new URL("http://localhost:45670/path2?arg1="+collNames.length()+",0,0&arg2="+collNames.replaceAll("\\+", "%2B")+"&aid="+appId.trim()+"&tid=9").openStream()));
		    String str="",response="";
		    while((str=br.readLine())!=null)
		    {
		        response+=str;
		    }
		    
		   html+= "<li id='linames' at='"+collNames+"'>"+response.split(" |\n")[1]+"</li>";
		   table+="<a id='collnames' at='"+collNames+"'>"+response.split(" |\n")[1]+"</a></td> <td>"+colCount.count()+"</td></tr>";
		}
		html+="</ul></div>";
		}catch(Exception e){
			e.printStackTrace();
		}

		return html+":"+table;
	}
	//http://localhost:45670/path2?arg1=32,35,0&arg2=ATxgI8v7N6g7nX5Ucxrxo4UvSbaGOHMvATxgI8viZa4DVXOnr5gkPz1LJqCHVBqqOxE&aid=4&tid=9
	//http://localhost:45670/path2?arg1=32,0,0&arg2=ATxgI8v7N6g7nX5Ucxrxo4UvSbaGOHMv&aid=4&tid=9
	//public String mongoapi(String mongourl,int port, String mongouser, String mongopwd,String mongoDB,String mongoCollection, String appId) {
	public String mongoapi(String mongoCollection, int skip, int limit){
		String html="";
		JSONObject mongoJson = new JSONObject();
		JSONArray metaJson = new JSONArray();
		JSONArray innerValues = new JSONArray();
		try{
		//mongoProps(mongourl,port,mongouser,mongopwd,mongoDB);
			
		
		Iterator<Document> iter = null;

/*		if(mongoCollection.contains("@")){
			String localColl = mongoCollection.split("@")[0];
			mongoColl = mongoDatabase.getCollection(localColl);
			String queryObj = mongoCollection.split("@")[1];
			BasicDBObject query = new BasicDBObject(queryObj.split(":")[0],queryObj.split(":")[1].replaceAll("'", ""));
			iter = mongoColl.find(query).projection(Projections.excludeId()).iterator();
			mongoCollection=localColl;
			
		}else{*/
			mongoColl = mongoDatabase.getCollection(mongoCollection);
			iter = mongoColl.find().skip(skip).limit(limit).projection(Projections.excludeId()).iterator();
		//}
		while (iter.hasNext()) {
			Document doc = iter.next();
			Set<String> keys = doc.keySet();
			//Preparing Column JSON Format
			if (metaJson.isEmpty()) {
				for (String key : keys) {
					JSONObject temp1 = new JSONObject();
					temp1.put("name", key);
					temp1.put("tablename", mongoCollection);
					metaJson.add(temp1);
				}

			}
			
			//Preparing Rows JSON Format
			int assign = 0;
			LinkedHashMap<String, Object> temp = new LinkedHashMap<String, Object>();
			for(String key: keys){			
				temp.put(String.valueOf(assign), doc.get(key));
				assign++;
			}
			innerValues.add(temp);
			mongoJson.put("RECORDS", innerValues);
			mongoJson.put("COLUMNS", metaJson);
			mongoJson.put("aid", appID);
			mongoJson.put("tid", "0");
		}
		
		JSONObject mongoPlain = MySQLAPI.readJsonFromUrl("http://localhost:45670/path5", mongoJson);
		//System.out.println(mongoPlain);
		Document doc = Document.parse(mongoPlain.toString());
		ArrayList<Document> columns = (ArrayList<Document>) doc.get("COLUMNS");
		String plainName = "";
		List<String> columnames = new ArrayList<String>();
		for (int i = 0; i < columns.size(); i++) {
			Document doc2 = columns.get(i);
			columnames.add(doc2.get("name").toString());
			plainName = doc2.get("tablename").toString();
			
		}
		
		html+="<h3><a id='colldb'>"+mongodata+"</a>."+plainName+"&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;<small>Documents</small>&nbsp;"+mongoColl.count()+
				"&emsp;&emsp;&emsp;&emsp;<small>Displaying-Documents</small> <a id='skipvalue' style='text-decoration: none;'>"+skip+"</a>-<a id='limitvalue' style='text-decoration: none;'>"+(skip+20)+"</a><small> of "+mongoColl.count()+
				"</small> <a href='#' id='bwd'><span class='glyphicon glyphicon-chevron-left'></span></a> <a id='fwd' href='#'><span class='glyphicon glyphicon-chevron-right'></span></a>&emsp;<a id='execute' href='#'><span class='glyphicon glyphicon-play'></span></a><a id='hidcoll' hidden>"+mongoCollection+"</a></h3><hr>";
		
		html+="<input class='form-control' id='query' value='db.getCollection(&quot;"+plainName+"&quot;).find({})' type='text'/><div style='overflow: scroll;width:1028px;height:580px;'> ";
		ArrayList<Document> records = (ArrayList<Document>) doc.get("RECORDS");
		for (int i = 0; i < records.size(); i++) {
			Document doc2 = records.get(i);
			Set<String> keys = doc2.keySet();
			String val = "";

			ArrayList<Integer> set = new ArrayList<Integer>();
			for (String s : keys)
				set.add(Integer.parseInt(s));
			Collections.sort(set);

			//for (int keyset : set)
			html+="<div><br>";
			for(int j=0; j < set.size(); j++){
				html+="&emsp;<label>"+columnames.get(j)+"</label>: <q><em>"+doc2.get(String.valueOf(set.get(j)))+"</em></q><br>";
								
			}
			html+="</div>";
		}
		html+="</div>";
		skip =0;limit=0;
		}catch(Exception e){
			e.printStackTrace();;
		}
	return html;
	}
	
	
	
	public String findMongoObj(String table, String fullObj){
		String html="";
		try{
			BufferedReader reader = new BufferedReader(new InputStreamReader(((new URL("http://localhost:45670/path1?arg1=select%20*%20from%20"+table.replaceAll("\\+", "%2B")+"%20where%20"+fullObj.split(":")[0].trim().replaceAll("\\+", "%2B")+"%20='"+fullObj.split(":")[1].trim()+"'&aid="+appID+"&tid=4"))
					//"http://localhost:45670/path1?arg1=select%20*%20from%20"+table.trim()+"%20where%20"+fullObj.split(":")[0]+"="+fullObj.split(":")[1]+"&aid="+appID+"&tid=4"))
							.openConnection()).getInputStream()));
			String str = "", response = "";
			while ((str = reader.readLine()) != null) {
				response += str;
			}
			JSONObject out = new JSONObject(response.toString());
			String result = out.getString("modsql").replaceAll("\\*" , "").split("select  from ")[1];
			String encColl = result.split(" where ")[0].replaceAll("`", "");
			encColl+="@"+result.split(" where ")[1].split("\\s+")[0].replaceAll("`", "");
			encColl+=":"+result.split(" where ")[1].split("\\s+")[1].replaceAll("=", "");
			
			System.out.println(encColl);
			MongoAPI mongoAPI = new MongoAPI();
			 html = mongoAPI.mongoapi(encColl, 0, 0);
			
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return html;
	}
}
