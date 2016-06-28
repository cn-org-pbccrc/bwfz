package org.packet.packetsimulation.web.listener;

import java.io.IOException;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.packet.packetsimulation.facade.DictTypeFacade;
import org.packet.packetsimulation.facade.impl.DictTypeFacadeImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

/**
 * Servlet implementation class DictStartServlet
 */
public class DictStartServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	   @Inject
		private DictTypeFacade dictTypeFacade;// = new DictTypeFacadeImpl();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DictStartServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    public void init(){
    	// 由于spring不负责servlet，因此需要用工具类先获取spring的beanfactory
    	   //dictTypeFacade.loadDictType();
        ServletContext sc = this.getServletContext();
        ApplicationContext ac =  WebApplicationContextUtils.getRequiredWebApplicationContext(sc);
        dictTypeFacade = ac.getBean(DictTypeFacade.class);
        dictTypeFacade.loadDictType();
//        for (int i =0;i<100000;i++){
//        	System.out.println("okkkkkk!!!");
//        	try {
//				Thread.currentThread().sleep(3000);
//			} catch (InterruptedException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
