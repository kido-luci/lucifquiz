package laptt.serlvet;

import com.restfb.types.User;
import laptt.account.AccountDAO;
import laptt.account.AccountDTO;
import laptt.utilities.RestFB;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "LoginFacebookServlet", urlPatterns = {"/LoginFacebookServlet"})
public class LoginFacebookServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = Logger.getLogger(LoginServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        String url = request.getContextPath();

        try {
            AccountDTO accountDTO = null;

            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                accountDTO = (AccountDTO)httpSession.getAttribute("AUTH_USER");
            }

            if (accountDTO != null) {
                url = request.getContextPath();
            } else {
                String code = request.getParameter("code");

                if (code == null || code.isEmpty()) {
                    RequestDispatcher dis = request.getRequestDispatcher("login.jsp");
                    dis.forward(request, response);
                } else {
                    String accessToken = RestFB.getToken(code);
                    User user = RestFB.getUserInfo(accessToken);

                    AccountDAO accountDAO = new AccountDAO();
                    accountDTO = accountDAO.getAccount(user.getId());

                    if (accountDTO != null) {

                        httpSession = request.getSession(true);
                        httpSession.setAttribute("AUTH_USER", accountDTO);

                        request.setAttribute("MESSAGE", "Welcome " + accountDTO.getName());
                    } else {
                        request.setAttribute("MESSAGE", "Login failed: your facebook account is deactivate");

                        ServletContext servletContext = request.getServletContext();
                        url = servletContext.getInitParameter("login-jsp");
                    }
                }
            }
        } catch (NullPointerException | SQLException | NamingException throwables) {
            logger.error(throwables);
        } finally {
            if (url.equals(request.getContextPath())) {
                response.sendRedirect(url);
            } else {
                RequestDispatcher requestDispatcher = request.getRequestDispatcher(url);
                requestDispatcher.forward(request, response);
            }

            out.close();
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
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
