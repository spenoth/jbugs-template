package servlet;

import com.fasterxml.jackson.databind.ObjectMapper;
import jwt.JwtManager;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import ro.msg.edu.jbugs.usermanagement.business.control.UserManagement;
import ro.msg.edu.jbugs.usermanagement.business.dto.UserDTO;
import ro.msg.edu.jbugs.usermanagement.business.exception.BuisnissException;
import ro.msg.edu.jbugs.usermanagement.persistance.dao.PermissionManagement;
import ro.msg.edu.jbugs.usermanagement.persistance.dao.UserManagementImpl;

import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.logging.Logger;

@WebServlet(name = "Authorization",
        urlPatterns = {"/authorize"})
public class Authorization extends HttpServlet {

    @EJB
    private PermissionManagement permissionManagement;

    @EJB
    private UserManagementImpl userManagement;

    @EJB
    private UserManagement userManagementBean;


    private static final org.apache.logging.log4j.Logger logger = LogManager.getLogger(Authorization.class);


    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        UserDTO dto = null;
        try {
            dto = userManagementBean.login(username, password);
        } catch (BuisnissException e) {
            logger.log(Level.ERROR, "Login failed. Exceptioncode:" + e.getExceptionCode());
            response.sendError(401);
            return;
        }

        if (dto == null) {
            response.sendError(401);
            logger.log(Level.ERROR, "Login failed. dto is null");
            return;
        }

        String tokany = JwtManager.getInstance().createToken(username);
        response.getWriter().println("{ \"token\": \"" + tokany + "\" }");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Logger l = Logger.getLogger("Authorizer");
        Enumeration<String> headerNames = request.getHeaderNames();
        System.out.println("Headers received with the request: ");
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            System.out.println(headerName + ": " + request.getHeader(headerName));
        }
        out.println("Everything OK!");

    }

}
