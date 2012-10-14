package net.herranzmartin.uploader;

import java.util.List;

import javax.persistence.EntityManager;

import net.herranzmartin.listeners.EMF;
import net.herranzmartin.project977r.model.TblConsultaSQL;
import net.herranzmartin.project977r.services.ConsultasService;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class ListaConsultas extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	private EntityManager em = EMF.createEntityManager();
	private TblConsultaSQL selectedConsulta;
	
	@Wire
	private Listbox listaConsultas;
	@Wire
	private Textbox consulta_id;
	@Wire
	private Textbox consulta_nombre;
	@Wire
	private Textbox consulta_definicion;
	@Wire
	private Div formAltaConsultaSQL;
	

	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		List<TblConsultaSQL> lista = getAllConsultas();
		System.out.println("Tenemos la lista de consultas y hay " + lista.size() + " registros!");
		self.setAttribute("consultas", lista);
	}
	
	

	/**
	 * @param lasConsultas
	 *            the consultas to set
	 */
	public void setConsulta(TblConsultaSQL laConsulta) {
		this.selectedConsulta = laConsulta;
	}

	/**
	 * @return the consulta
	 */
	public TblConsultaSQL getSelectedConsulta() {
		return selectedConsulta;
	}

	public void onSelect$listaConsultas(Event event){
		System.out.println(listaConsultas.getSelectedIndex());
		listaConsultas.setVisible(false);
		formAltaConsultaSQL.setVisible(true);
		
	}

	public void onClick$btnConsultaGuardarCambios(Event e){
		TblConsultaSQL consulta = new TblConsultaSQL();
			consulta.setId(new Integer(consulta_id.getValue().trim()));
			consulta.setNombre(consulta_nombre.getValue().trim());
			consulta.setDefinicion(consulta_definicion.getValue().trim());
			System.out.println(consulta.toString());
			
		em = EMF.createEntityManager();
		ConsultasService cs = new ConsultasService(em);
			cs.save(consulta);
		Messagebox.show("Guardadas las modificaciones!");
	}

	public void onClick$btnVolver(Event e){
		formAltaConsultaSQL.setVisible(false);
		listaConsultas.setVisible(true);
	}

	public List<TblConsultaSQL> getAllConsultas() {
		em = EMF.createEntityManager();
		ConsultasService fs = new ConsultasService(em);
		List<TblConsultaSQL> list = fs.getAllItems();
		return list;
	}

}
