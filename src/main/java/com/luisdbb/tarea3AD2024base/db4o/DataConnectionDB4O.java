package com.luisdbb.tarea3AD2024base.db4o;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Component
public class DataConnectionDB4O {

       private static DataConnectionDB4O INSTANCE = null;
       
       @Value("${db40.path}")
   	   private String PATH;
       private static ObjectContainer db;


       private DataConnectionDB4O() {
       }

       private synchronized static void createInstance() {
	   		if (INSTANCE == null) { 
	   			INSTANCE = new DataConnectionDB4O();
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