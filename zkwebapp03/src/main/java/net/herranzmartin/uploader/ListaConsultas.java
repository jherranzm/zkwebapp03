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
import org.zkoss.zul.Button;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

public class ListaConsultas extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	private EntityManager em = EMF.createEntityManager();
	private TblConsultaSQL selectedConsulta;
	private ListModelList<TblConsultaSQL> modelo;
	
	@Wire
	private Listbox listaConsultas;

	@Wire
	private Div formAltaConsultaSQL;
	@Wire
	private Textbox consulta_id;
	@Wire
	private Textbox consulta_nombre;
	@Wire
	private Textbox consulta_definicion;

	@Wire
	private Div formSearch;
	@Wire
	private Textbox txtSearch;
	@Wire
	private Button btnSearch;
	

	
	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		listaConsultas.setItemRenderer(new ListitemRenderer<TblConsultaSQL>() {
			@Override
			public void render(Listitem item, TblConsultaSQL data, int index)
					throws Exception {
				new Listcell(data.getNombre()).setParent(item);
				new Listcell(data.getDefinicion()).setParent(item);
				item.setValue(data);
			}
		});
		
		refreshList();
		
		listaConsultas.setVisible(true);
		formSearch.setVisible(true);
		formAltaConsultaSQL.setVisible(false);
	}



	/**
	 * 
	 */
	private void refreshList() {
		List<TblConsultaSQL> lista = getAllConsultas();
		System.out.println("Tenemos la lista de consultas y hay " + lista.size() + " registros!");
		modelo = new ListModelList<TblConsultaSQL>(lista);
		listaConsultas.setModel(modelo);
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
		
		//TblConsultaSQL consulta = (TblConsultaSQL) listaConsultas.getSelectedItem().;
		//consulta_id.setValue(new String())
		
		TblConsultaSQL consulta = (TblConsultaSQL) modelo.get(listaConsultas.getSelectedIndex());
		
		consulta_id.setValue((new Integer(consulta.getId())).toString());
		consulta_nombre.setValue(consulta.getNombre());
		consulta_definicion.setValue(consulta.getDefinicion());
		
		listaConsultas.setVisible(false);
		formAltaConsultaSQL.setVisible(true);
		formSearch.setVisible(false);
		
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
		
		refreshList();
		
		formAltaConsultaSQL.setVisible(false);
		listaConsultas.setVisible(true);
		formSearch.setVisible(true);
	}

	public void onClick$btnVolver(Event e){
		formAltaConsultaSQL.setVisible(false);
		listaConsultas.setVisible(true);
		formSearch.setVisible(true);
	}

	public List<TblConsultaSQL> getAllConsultas() {
		em = EMF.createEntityManager();
		ConsultasService fs = new ConsultasService(em);
		List<TblConsultaSQL> list = fs.getAllItems();
		return list;
	}

	public void onClick$btnSearch(Event e) {
		formAltaConsultaSQL.setVisible(false);
		formSearch.setVisible(true);
		listaConsultas.setVisible(true);
		
		String texto = txtSearch.getValue();

		em = EMF.createEntityManager();
		ConsultasService cs = new ConsultasService(em);
		
		List<TblConsultaSQL> lista = cs.findByName(texto);
		System.out.println("Tenemos la lista de consultas y hay " + lista.size()
				+ " registros!");
		modelo = new ListModelList<TblConsultaSQL>(lista);
		listaConsultas.setModel(modelo);
	}
}
