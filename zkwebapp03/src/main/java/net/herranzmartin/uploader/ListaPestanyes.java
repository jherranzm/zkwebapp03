package net.herranzmartin.uploader;

import java.util.List;

import javax.persistence.EntityManager;

import net.herranzmartin.listeners.EMF;
import net.herranzmartin.project977r.model.TblPestanya;
import net.herranzmartin.project977r.services.PestanyesService;

public class ListaPestanyes {

	private EntityManager em = EMF.createEntityManager();

	
	public List<TblPestanya> pestanyes = getAllPestanyes();

	/**
	 * @return the consultas
	 */
	public List<TblPestanya> getPestanyes() {
		return pestanyes;
	}

	/**
	 * @param lasPestanyes the consultas to set
	 */
	public void setConsultas(List<TblPestanya> lasPestanyes) {
		this.pestanyes = lasPestanyes;
	}

	public List<TblPestanya> getAllPestanyes() {
		PestanyesService fs = new PestanyesService(em);
		List<TblPestanya> list = fs.getAllItems();
		return list;
	}


}
