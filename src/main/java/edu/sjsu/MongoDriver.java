package edu.sjsu;

import java.net.UnknownHostException;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

public class MongoDriver {
	
	public DB getMongoConnection() throws UnknownHostException
	{
	
	String textUri = "mongodb://cmpe273:College6@ds041188.mongolab.com:41188/cmpe273";
	
	 MongoClientURI uri = new MongoClientURI(textUri);
	 MongoClient client = new MongoClient(uri);
    
    DB db = client.getDB("cmpe273");
    
    return db;
	}

}
