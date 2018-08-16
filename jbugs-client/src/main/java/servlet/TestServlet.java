package servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import ro.msg.edu.jbugs.usermanagement.business.control.UserManagement;
import ro.msg.edu.jbugs.usermanagement.business.exception.BuisnissException;
import ro.msg.edu.jbugs.usermanagement.persistance.dao.PermissionManagement;
import ro.msg.edu.jbugs.usermanagement.persistance.dao.UserManagementImpl;
import ro.msg.edu.jbugs.usermanagement.persistance.entity.Permission;
import ro.msg.edu.jbugs.usermanagement.persistance.entity.Role;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTO;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@WebServlet(urlPatterns = { "/TestServlet" })
public class TestServlet extends HttpServlet {


	@EJB
	private PermissionManagement permissionManagement;

	@EJB
	private UserManagementImpl userManagement;

	@EJB
    private UserManagement userManagementBean;

    private static final Logger logger = LogManager.getLogger(TestServlet.class);

	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	protected void processRequest(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {


		Role r1 = new Role("type1");
		Role r2 = new Role("type2");
		Permission p1 = new Permission("type1","desc1");
		Permission p2 = new Permission("type2","desc2");

		permissionManagement.addPermission(p1);

        testCreateUser();

        UserDTO dto1 = new UserDTO();
        dto1.setFirstName("Ion");
        dto1.setLastName("Ion");
        dto1.setPassword("passwd");
        dto1.setEmail("tralalaf@msggroup.com");
        dto1.setPhoneNumber("0431211444");

        UserDTO persistentuserdto = null;
        try {
            persistentuserdto = userManagementBean.createUser(dto1);
        } catch (BuisnissException e) {
            logger.log(Level.ERROR,"Didnt work");
        }

        testDeactivate();

        testActivate();

        List<UserDTO> usrs = testGetAllUsers();

		response.setContentType("text/html;charset=UTF-8");
		try (PrintWriter out = response.getWriter()) {
		    out.println(persistentuserdto);
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Test EJB Bean New</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<table>");

		    for (int i = 0; i< usrs.size(); i++) {
                out.println("<tr>");
                out.println("<td>"+usrs.get(i).getUsername()+"</td>");
                out.println("<td>"+usrs.get(i).getPassword()+"</td>");
                out.println("</tr>");
            }
            out.println("</table>");
            out.println("</body>");
            out.println("</html>");

//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Test EJB Bean New</title>");
//                out.println("</head>");
//                out.println("<body>");
//                out.println(userManagement.getAllUsers());
//                out.println("</body>");
//                out.println("</html>");
		}


		//permissionManagement.addPermission(p1);
		//permissionManagement.addPermission(p2);


		//permissionManagement.addPermission(p1);
		//permissionManagement.createPermissionForRole(r1,p1);
	}

	protected void testCreateUser() {

        UserDTO dto1 = new UserDTO();
        dto1.setFirstName("Ion");
        dto1.setLastName("Ion");
        dto1.setPassword("passwd");
        dto1.setEmail("asdaf1@msggroup.com");
        dto1.setPhoneNumber("0745896564");

        UserDTO persistentuserdto = null;
        try {
            persistentuserdto = userManagementBean.createUser(dto1);
        } catch (BuisnissException e) {
            logger.log(Level.ERROR,"Didnt work");
        }


        UserDTO dto2 = new UserDTO();
        dto2.setFirstName("Ion");
        dto2.setLastName("Ion");
        dto2.setPassword("passwd");
        dto2.setEmail("asdaaaf2@msggroup.com");
        dto2.setPhoneNumber("0745896561");

        try {
            userManagementBean.createUser(dto2);
        } catch (BuisnissException e) {
            logger.log(Level.ERROR,"Didnt work");
        }

        UserDTO dto3 = new UserDTO();
        dto3.setFirstName("Ion");
        dto3.setLastName("Ionica");
        dto3.setPassword("passwd");
        dto3.setEmail("asdabaf3@msggroup.com");
        dto3.setPhoneNumber("0745896563");


        try {
            userManagementBean.createUser(dto3);
        } catch (BuisnissException e) {
            logger.log(Level.ERROR,"Didnt work");
        }

        UserDTO dto4 = new UserDTO();
        dto4.setFirstName("John");
        dto4.setLastName("Doe");
        dto4.setPassword("passwd");
        dto4.setEmail("forth@msggroup.com");
        dto4.setPhoneNumber("0123432192");

        try {
            userManagementBean.createUser(dto4);
        } catch (BuisnissException e) {
            logger.log(Level.ERROR,"Didnt work");
        }
    }

    protected void testDeactivate() {

        UserDTO dto = new UserDTO();
        dto.setFirstName("Bela");
        dto.setLastName("Lugosi");
        dto.setPassword("dracula");
        dto.setEmail("dracula@msggroup.com");
        dto.setPhoneNumber("0745911890");

        try {
            UserDTO persistentuserdto = userManagementBean.createUser(dto);
            userManagementBean.deactivateUser(persistentuserdto.getUsername());
        } catch (BuisnissException e) {
            logger.log(Level.ERROR,"Didnt work");
        }
    }

    protected void testActivate() {

        UserDTO dto = new UserDTO();
        dto.setFirstName("Gerard");
        dto.setLastName("Butler");
        dto.setPassword("machinegun");
        dto.setEmail("machinegun@msggroup.com");
        dto.setPhoneNumber("0745911830");

        try {
            UserDTO persistentuserdto = userManagementBean.createUser(dto);
            userManagementBean.deactivateUser(persistentuserdto.getUsername()); //this works 100% i tested it
            userManagementBean.activateUser(persistentuserdto.getUsername());
        } catch (BuisnissException e) {
            logger.log(Level.ERROR,"Didnt work");
        }
    }

    protected List<UserDTO> testGetAllUsers() {
        List<UserDTO> users = userManagementBean.getAllUsers();
        return users;
    }

	// <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on
	// the + sign on the left to edit the code.">
	/**
	 * Handles the HTTP <code>GET</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 *
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 * @throws ServletException
	 *             if a servlet-specific error occurs
	 * @throws IOException
	 *             if an I/O error occurs
	 */
	@Override
	protected void doPost(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}

	/**
	 * Returns a short description of the servlet.
	 *
	 * @return a String containing servlet description
	 */
	@Override
	public String getServletInfo() {
		return "Short description";
	}// </editor-fold>
}


/*
@Singleton
@Startup
class TestingClass{

	@PostConstruct
	public void printBeforeTime(){
		System.out.println("BEFORE:: " + java.time.LocalTime.now());
	}

	@PreDestroy
	public void printAfterTime(){
		System.out.println("AFTER:: " + java.time.LocalTime.now());
	}


}
*/