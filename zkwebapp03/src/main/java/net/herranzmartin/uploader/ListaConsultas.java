package net.herranzmartin.uploader;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import net.herranzmartin.project977r.model.TblConsultaSQL;
import net.herranzmartin.project977r.services.ConsultasService;

public class ListaConsultas {

	private EntityManagerFactory emf = Persistence.createEntityManagerFactory("977R");
	private EntityManager em = emf.createEntityManager();

	
	public List<TblConsultaSQL> consultas = getAllFicheros();

	/**
	 * @return the consultas
	 */
	public List<TblConsultaSQL> getConsultas() {
		return consultas;
	}

	/**
	 * @param lasConsultas the consultas to set
	 */
	public void setConsultas(List<TblConsultaSQL> lasConsultas) {
		this.consultas = lasConsultas;
	}

	public List<TblConsultaSQL> getAllFicheros() {
		ConsultasService fs = new ConsultasService(em);
		List<TblConsultaSQL> list = fs.getAllItems();
		return list;
	}


}
