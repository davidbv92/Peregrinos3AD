package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.luisdbb.tarea3AD2024base.db4o.DataConnectionDB4O;
import com.luisdbb.tarea3AD2024base.modelo.ConjuntoContratado;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Repository
public class ConjuntoContratadoRepository {

	private final DataConnectionDB4O dataConnection;

    @Autowired
    public ConjuntoContratadoRepository(DataConnectionDB4O dataConnection) {
        this.dataConnection = dataConnection;
    }
    
    public void save(ConjuntoContratado conjunto) {
		ObjectContainer db = dataConnection.getInstance(); 
		
		try {
			db.store(conjunto);
			db.commit();
		}catch (Exception e) {
			db.rollback();
			MiAlerta.showErrorAlert("Error en la inserción", e.getMessage());
			//e.printStackTrace();
		}finally {
			db.close();
		}
    }
    
    public List<ConjuntoContratado> findAll() {
        ObjectContainer db =dataConnection.getInstance(); 
        return db.query(ConjuntoContratado.class);
    }
    
    public Long calcularIdMaximo() {
		ObjectContainer db = dataConnection.getInstance(); 
		
		try {
			ObjectSet<ConjuntoContratado> result = db.queryByExample(new ConjuntoContratado());
			return result.isEmpty() ? 0L : result.get(result.size() - 1).getId();
		} finally {
			db.close();
		}
	}
}
