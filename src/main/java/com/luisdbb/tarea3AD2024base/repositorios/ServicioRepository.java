package com.luisdbb.tarea3AD2024base.repositorios;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import com.luisdbb.tarea3AD2024base.db4o.DataConnectionDB4O;
import com.luisdbb.tarea3AD2024base.modelo.MiAlerta;
import com.luisdbb.tarea3AD2024base.modelo.Servicio;

@Repository
public class ServicioRepository {
	
	private final DataConnectionDB4O dataConnection;

    @Autowired
    public ServicioRepository(DataConnectionDB4O dataConnection) {
        this.dataConnection = dataConnection;
    }
	
	public void save(Servicio servicio) {
		ObjectContainer db = dataConnection.getInstance(); 
		
		try {
			db.store(servicio);
			db.commit();
		}catch (Exception e) {
			db.rollback();
			MiAlerta.showErrorAlert("Error en la inserción", e.getMessage());
			//e.printStackTrace();
		}finally {
			db.close();
		}
    }
	
	public Servicio findById(Long id) {
		ObjectContainer db = dataConnection.getInstance(); 
        try {
	        Servicio ejemplo = new Servicio();
	        ejemplo.setId(id);
	        ObjectSet<Servicio> result = db.queryByExample(ejemplo);
	        return result.isEmpty() ? null : result.get(0); 
	    } catch (Exception ex) {
	        //ex.printStackTrace();
	    	MiAlerta.showErrorAlert("Error en la búsqueda", ex.getMessage());
	        return null;
	    } finally {
	        db.close();
	    }
    }
	
	public Servicio findByName(String name) {
		ObjectContainer db = dataConnection.getInstance(); 
	    try {
	        Servicio ejemplo = new Servicio();
	        ejemplo.setNombre(name);
	        ObjectSet<Servicio> result = db.queryByExample(ejemplo);
	        return result.isEmpty() ? null : result.get(0); 
	    } finally {
	        db.close();
	    }
	}
	
	public List<Servicio> findAll() {
        ObjectContainer db =dataConnection.getInstance(); 
        return db.query(Servicio.class);
    }
	
	public void deleteById(Long id) {
        ObjectContainer db = dataConnection.getInstance(); 
        Servicio servicio = findById(id);
        if (servicio != null) {
            db.delete(servicio);
            db.commit();
        }
    }
	
	public Long calcularIdMaximo() {
		ObjectContainer db = dataConnection.getInstance(); 
		
		try {
			ObjectSet<Servicio> result = db.queryByExample(new Servicio());
			return result.isEmpty() ? 0L : result.get(result.size() - 1).getId();
		} finally {
			db.close();
		}
	}

	public void update(Servicio servicio) {
		ObjectContainer db = dataConnection.getInstance(); 

		try {
			
			Servicio ejemplo = new Servicio();
			ejemplo.setId(servicio.getId());
			ObjectSet<Servicio> result = db.queryByExample(ejemplo);

			if (!result.isEmpty()) {
				Servicio actualizar = result.get(0);
				actualizar.setNombre(servicio.getNombre());
				actualizar.setPrecio(servicio.getPrecio());
				actualizar.setParadas(servicio.getParadas());
				db.store(actualizar);
				db.commit();
			}
		} catch (Exception e) {
			db.rollback();
			//e.printStackTrace();
			MiAlerta.showErrorAlert("Error en la actualización", e.getMessage());
		} finally {
			db.close();
		}
		
	}

	public List<Servicio> findAllByParadaId(Long id) {
		ObjectContainer db =dataConnection.getInstance(); 
		List<Servicio> servicios=db.query(Servicio.class);
		List<Servicio> filtrados = new ArrayList<>(0);
		for(Servicio s:servicios) {
			if(s.contieneParada(id)) {
				filtrados.add(s);
			}
		}
        return filtrados;
        		
	}
}
