package laptt.serlvet;

import laptt.account.AccountDAO;
import laptt.account.AccountDTO;
import laptt.utilities.SHA256Utilities;
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
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

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
                String btAction = request.getParameter("btAction");
                if (btAction != null && btAction.equals("toLoginPage")) {
                    ServletContext servletContext = request.getServletContext();
                    url = servletContext.getInitParameter("login-jsp");
                } else {
                    String email = request.getParameter("txtEmail");
                    String password = request.getParameter("txtPassword");
                    String errorMessage = "";

                    if (email.isEmpty()) {
                        errorMessage += "Email can't be plank";
                    }

                    if (password.isEmpty()) {
                        if (errorMessage.isEmpty()) {
                            errorMessage += "Password can't be plank";
                        } else {
                            errorMessage += ". Password can't be plank";
                        }
                    }

                    if (!errorMessage.isEmpty()) {
                        request.setAttribute("MESSAGE", errorMessage);

                        ServletContext servletContext = request.getServletContext();
                        url = servletContext.getInitParameter("login-jsp");
                    } else {
                        String decodePassword = SHA256Utilities.decodeSHA256(password);

                        AccountDAO accountDAO = new AccountDAO();
                        accountDTO = accountDAO.getAccount(email, decodePassword);

                        if (accountDTO != null) {

                            httpSession = request.getSession(true);
                            httpSession.setAttribute("AUTH_USER", accountDTO);

                            request.setAttribute("MESSAGE", "Welcome " + accountDTO.getName());
                        } else {
                            errorMessage = "Login failed: username or password is incorrect";

                            request.setAttribute("MESSAGE", errorMessage);

                            ServletContext servletContext = request.getServletContext();
                            url = servletContext.getInitParameter("login-jsp");
                        }
                    }
                }
            }
        } catch (SQLException | NoSuchAlgorithmException | NullPointerException | NamingException throwables) {
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
