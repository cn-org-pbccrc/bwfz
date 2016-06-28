package org.packet.packetsimulation.web.listener;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.packet.packetsimulation.facade.DictTypeFacade;
import org.packet.packetsimulation.facade.impl.DictTypeFacadeImpl;

/**
 * Application Lifecycle Listener implementation class DictStartListener
 *
 */
public class DictStartListener implements ServletContextListener {

	//@Inject
	private DictTypeFacade dictTypeFacade = new DictTypeFacadeImpl();
    /**
     * Default constructor. 
     */
    public DictStartListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	dictTypeFacade.loadDictType();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    }
	
}
