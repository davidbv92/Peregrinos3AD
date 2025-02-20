package com.luisdbb.tarea3AD2024base.db4o;

import org.springframework.stereotype.Component;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Component
public class DataConnection {

       private static DataConnection INSTANCE = null;
       private final String PATH = "peregrinos.db4o";
       private static ObjectContainer db;


       private DataConnection() {
       }

       private synchronized static void createInstance() {
	   		if (INSTANCE == null) { 
	   			INSTANCE = new DataConnection();
	   			INSTANCE.performConnection();
	   		}
   		}

       public static ObjectContainer getInstance() {
            if (INSTANCE == null)
                createInstance();
            return db;
       }

       public void performConnection() {
	   		db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), PATH);
   	}
       
       public void closeConnection() {
           db.close();
       }

}