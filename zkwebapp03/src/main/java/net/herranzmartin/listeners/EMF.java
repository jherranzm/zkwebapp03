package net.herranzmartin.listeners;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import net.herranzmartin.project977r.model.TblCucCif;
import net.herranzmartin.project977r.services.ClientesService;

/**
 * Application Lifecycle Listener implementation class EmfServletContextListener
 *
 */
public class EMF implements ServletContextListener {

    private static EntityManagerFactory emf;

    /**
     * Default constructor. 
     */
    public EMF() {
        // TODO Auto-generated constructor stub
    }

    @Override
    public void contextInitialized(ServletContextEvent event) {
    	
    	System.out.println("Contexto inicializándose...");
        emf = Persistence.createEntityManagerFactory("977R");
        EntityManager em = emf.createEntityManager();
		ClientesService fs = new ClientesService(em);
		List<TblCucCif> list = fs.getAllItems();
		System.out.println("List.size():" + list.size());
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        emf.close();
        emf = null;
        System.out.println("Contexto eliminado!!");
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("977R");
            throw new IllegalStateException("El contexto no se ha inicializado aún...");
        }
        
        return emf.createEntityManager();
    }
	
}
