package com.luisdbb.tarea3AD2024base.db4o;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;

@Component
public class DataConnectionDB4O {

	private ObjectContainer db;

    @Value("${db40.path}")
    private String path;

    public DataConnectionDB4O() {
    }

    public void performConnection() {
        if (db == null || db.ext().isClosed()) {
            db = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(), path);
        }
    }

    public ObjectContainer getInstance() {
        if (db == null || db.ext().isClosed()) {
            performConnection();
        }
        return db;
    }

    public void closeConnection() {
        if (db != null) {
            db.close();
        }
    }

}