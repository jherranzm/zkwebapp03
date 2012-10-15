package net.herranzmartin.uploader;

import java.util.List;

import javax.persistence.EntityManager;

import net.herranzmartin.listeners.EMF;
import net.herranzmartin.project977r.model.TblConsultaSQL;
import net.herranzmartin.project977r.model.TblPestanya;
import net.herranzmartin.project977r.services.ConsultasService;
import net.herranzmartin.project977r.services.PestanyesService;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Div;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;
import org.zkoss.zul.Textbox;

public class ListaPestanyes extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;

	private EntityManager em = EMF.createEntityManager();
	private ListModelList<TblPestanya> modelo;
	private ListModelList<TblConsultaSQL> modeloCons;

	@Wire
	private Listbox listaPestanyes;
	@Wire
	private Combobox pestanya_consulta;

	@Wire
	private Div formAltaPestanya;
	@Wire
	private Textbox pestanya_id, pestanya_nombre, pestanya_rango, pestanya_numFilaInicial;

	@Wire
	private Div formSearch;
	@Wire
	private Textbox txtSearch;
	@Wire
	private Button btnSearch;
	
	public List<TblPestanya> pestanyes = getAllPestanyes();

	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);
		
		listaPestanyes.setItemRenderer(new ListitemRenderer<TblPestanya>() {
			@Override
			public void render(Listitem item, TblPestanya data, int index)
					throws Exception {
				new Listcell(data.getNombre()).setParent(item);
				new Listcell(data.getRango()).setParent(item);
				new Listcell((new Integer(data.getNumFilaInicial())).toString()).setParent(item);
				//new Listcell((new Integer(data.getTblConsultasSql().getId())).toString()).setParent(item);
				new Listcell(data.getTblConsultasSql().getNombre()).setParent(item);
				item.setValue(data);
			}
		});
		
		refreshList();
		
		listaPestanyes.setVisible(true);
		formSearch.setVisible(true);
		formAltaPestanya.setVisible(false);
	}
	
	/**
	 * 
	 */
	private void refreshList() {
		List<TblPestanya> lista = getAllPestanyes();
		System.out.println("Tenemos la lista de pestanyas y hay " + lista.size() + " registros!");
		modelo = new ListModelList<TblPestanya>(lista);
		listaPestanyes.setModel(modelo);
	}
	
	/**
	 * 
	 */
	public void onClick$btnVolver(Event e){
		formAltaPestanya.setVisible(false);
		listaPestanyes.setVisible(true);
		formSearch.setVisible(true);
	}
	
	/**
	 * 
	 */
	public List<TblPestanya> getAllPestanyes() {
		em = EMF.createEntityManager();
		PestanyesService fs = new PestanyesService(em);
		List<TblPestanya> list = fs.getAllItems();
		return list;
	}

	
	public void onSelect$listaPestanyes(Event event){
		System.out.println(listaPestanyes.getSelectedIndex());
		
		TblPestanya pestanya = (TblPestanya) modelo.get(listaPestanyes.getSelectedIndex());
		
		pestanya_id.setValue((new Integer(pestanya.getId())).toString());
		pestanya_nombre.setValue(pestanya.getNombre());
		pestanya_rango.setValue(pestanya.getRango());
		pestanya_numFilaInicial.setValue((new Integer(pestanya.getNumFilaInicial())).toString());
		
		//pestanya_consulta_id.setValue(pestanya.getTblConsultasSql().getNombre());
		
		em = EMF.createEntityManager();
		ConsultasService fs = new ConsultasService(em);
		List<TblConsultaSQL> listConsultas = fs.getAllItems();
		System.out.println("onSelect$listaPestanyes:listConsultas:" + listConsultas.size());
		
//		modeloCons = new ListModelList<TblConsultaSQL>(listConsultas);
//		pestanya_consulta.setModel(modeloCons);
		
		for(TblConsultaSQL consulta : listConsultas){
			Comboitem ci = new Comboitem();
			ci.setValue(consulta);
			ci.setLabel(consulta.getNombre());
			System.out.println(consulta.getId() + ":" + consulta.getNombre());
			pestanya_consulta.appendChild(ci);
			if(pestanya.getTblConsultasSql().getId() == consulta.getId()){
				pestanya_consulta.setSelectedItem(ci);
			}
		}
		
		listaPestanyes.setVisible(false);
		formAltaPestanya.setVisible(true);
		formSearch.setVisible(false);
		
	}

	
	public void onClick$btnSearch(Event e) {
		formAltaPestanya.setVisible(false);
		formSearch.setVisible(true);
		listaPestanyes.setVisible(true);
		
		String texto = txtSearch.getValue();

		em = EMF.createEntityManager();
		PestanyesService cs = new PestanyesService(em);
		
		List<TblPestanya> lista = cs.findByName(texto);
		System.out.println("Tenemos la lista de pestañas y hay " + lista.size()
				+ " registros!");
		modelo = new ListModelList<TblPestanya>(lista);
		listaPestanyes.setModel(modelo);
	}
	
	
	
	public void onClick$btnPestanyaGuardarCambios(Event e){
		System.out.println(pestanya_consulta.getSelectedIndex());
		System.out.println(pestanya_consulta.getValue().toString());
		
		Comboitem ci = pestanya_consulta.getSelectedItem();
		if (ci != null){
			TblConsultaSQL consulta = (TblConsultaSQL) ci.getValue();
			System.out.println(consulta.toString());
			TblPestanya pestanya = new TblPestanya();
			pestanya.setId(new Integer(pestanya_id.getValue().trim()));
			pestanya.setNombre(pestanya_nombre.getValue().trim());
			pestanya.setRango(pestanya_rango.getValue().trim());
			pestanya.setTblConsultasSql(consulta);
			System.out.println(pestanya.toString());
			
			em = EMF.createEntityManager();
			PestanyesService cs = new PestanyesService(em);
				cs.save(pestanya);
			Messagebox.show("Guardadas las modificaciones!", "Gestión de Pestañas", Messagebox.OK, Messagebox.EXCLAMATION);
		}else{
			Messagebox.show("Error: no se ha informado de la consulta o ésta NO es válida!", 
					"Gestión de Pestañas", 
					Messagebox.CANCEL, Messagebox.EXCLAMATION);
		}
		
		
		refreshList();
		
		formAltaPestanya.setVisible(false);
		listaPestanyes.setVisible(true);
		formSearch.setVisible(true);
	}
	

}
