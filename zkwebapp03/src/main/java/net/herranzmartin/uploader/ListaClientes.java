package net.herranzmartin.uploader;

import java.util.List;

import javax.persistence.EntityManager;

import net.herranzmartin.listeners.EMF;
import net.herranzmartin.project977r.model.TblCucCif;
import net.herranzmartin.project977r.services.ClientesService;

import org.zkoss.zhtml.Messagebox;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Div;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Textbox;

public class ListaClientes extends GenericForwardComposer {

	private static final long serialVersionUID = 1L;
	private EntityManager em;
	private TblCucCif selectedCliente;

	@Wire
	private Textbox cliente_id;
	@Wire
	private Textbox cliente_tipodoc;
	@Wire
	private Textbox cliente_cif;
	@Wire
	private Textbox cliente_cuc;
	@Wire
	private Textbox cliente_nombre;
	@Wire
	private Div formAltaCliente;
	@Wire
	private Listbox listaClientes;


	public void doAfterCompose(Component comp) throws Exception {
		super.doAfterCompose(comp);

		List<TblCucCif> lista = getAllClientes();
		System.out.println("Tenemos la lista de clientes y hay " + lista.size()
				+ " registros!");
		self.setAttribute("clientes", lista);
	}

	/**
	 * @param lasClientes
	 *            the clientes to set
	 */
	public void setCliente(TblCucCif elCliente) {
		this.selectedCliente = elCliente;
	}

	/**
	 * @return the cliente
	 */
	public TblCucCif getSelectedCliente() {
		return selectedCliente;
	}

	public List<TblCucCif> getAllClientes() {
		em = EMF.createEntityManager();
		ClientesService fs = new ClientesService(em);
		List<TblCucCif> list = fs.getAllItems();
		return list;
	}

	public void onSelect$listaClientes(Event event) {
		System.out.println(listaClientes.getSelectedIndex());
		listaClientes.setVisible(false);
		formAltaCliente.setVisible(true);
	}

	public void onClick$btnClienteGuardarCambios(Event e) {
		TblCucCif cliente = new TblCucCif();
		cliente.setId(new Integer(cliente_id.getValue().trim()));
		cliente.setTipoDoc(cliente_tipodoc.getValue().trim());
		cliente.setCif(cliente_cif.getValue().trim());
		cliente.setCuc(cliente_cuc.getValue().trim());
		cliente.setNombreCliente(cliente_nombre.getValue().trim());
		System.out.println(cliente.toString());

		em = EMF.createEntityManager();
		ClientesService cs = new ClientesService(em);
		cs.save(cliente);
		Messagebox.show("Guardadas las modificaciones!");
	}

	public void onClick$btnVolver(Event e) {
		formAltaCliente.setVisible(false);
		listaClientes.setVisible(true);
	}

}
