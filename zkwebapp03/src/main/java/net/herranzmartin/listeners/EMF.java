package net.herranzmartin.listeners;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

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
    }

    @Override
    public void contextDestroyed(ServletContextEvent event) {
        emf.close();
    }

    public static EntityManager createEntityManager() {
        if (emf == null) {
            throw new IllegalStateException("Context is not initialized yet.");
        }
//        System.out.println("Tenemos EMF...");
        return emf.createEntityManager();
    }
	
}
