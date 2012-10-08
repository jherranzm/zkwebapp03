package net.herranzmartin.uploader;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.herranzmartin.project977r.model.Tbl000000;
import net.herranzmartin.project977r.services.FicherosService;

public class ListaFicheros {
	
	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("977R");
	private EntityManager em = emf.createEntityManager();

	
	public List<Tbl000000> ficheros = getAllFicheros();

	/**
	 * @return the ficheros
	 */
	public List<Tbl000000> getFicheros() {
		return ficheros;
	}

	/**
	 * @param ficheros the ficheros to set
	 */
	public void setFicheros(List<Tbl000000> ficheros) {
		this.ficheros = ficheros;
	}

	public List<Tbl000000> getAllFicheros() {
		
		FicherosService fs = new FicherosService(em);
		List<Tbl000000> list = fs.getAllItems();
		return list;
	}


}
