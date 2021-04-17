package laptt.serlvet;

import laptt.account.AccountDAO;
import laptt.account.AccountDTO;
import laptt.utilities.SHA256Utilities;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import javax.naming.NamingException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/RegisterServlet"})
public class RegisterServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(RegisterServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        ServletContext servletContext = request.getServletContext();
        String url = servletContext.getInitParameter("register-jsp");

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

                if (btAction != null && btAction.equals("toRegisterPage")) {
                    url = servletContext.getInitParameter("register-jsp");
                } else {
                    String name = request.getParameter("txtName");
                    String email = request.getParameter("txtEmail");
                    String password = request.getParameter("txtPassword");
                    String confirmPassword = request.getParameter("txtConfirmPassword");
                    String errorMessage = "";

                    if (name.length() < 6 || name.length() > 30) {
                        errorMessage += "Name should be between 6-30 characters";
                    }

                    if (email.length() < 6 || email.length() > 30) {
                        if (errorMessage.isEmpty()) {
                            errorMessage += "Email should be between 6-30 characters";
                        } else {
                            errorMessage += ". Email should be between 6-30 characters";
                        }
                    } else {
                        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
                        if (!email.matches(regex)) {
                            if (errorMessage.isEmpty()) {
                                errorMessage += "Invalid email format";
                            } else {
                                errorMessage += ". Invalid email format";
                            }
                        }
                    }

                    if (password.length() < 6 || password.length() > 30) {
                        if (errorMessage.isEmpty()) {
                            errorMessage += "Password should be between 6-30 characters";
                        } else {
                            errorMessage += ". Password should be between 6-30 characters";
                        }
                    } else {
                        if (!confirmPassword.equals(password)) {
                            if (errorMessage.isEmpty()) {
                                errorMessage += "Confirm password doesn't match";
                            } else {
                                errorMessage += ". Confirm password doesn't match";
                            }
                        }
                    }

                    if (!errorMessage.isEmpty()) {
                        request.setAttribute("MESSAGE", errorMessage);
                    } else {
                        try {
                            String decodePassword = SHA256Utilities.decodeSHA256(password);

                            AccountDAO accountDAO = new AccountDAO();
                            boolean isInsert = accountDAO.insertAccountToDB(email, name, decodePassword);
                            if (isInsert) {
                                request.setAttribute("MESSAGE", "Registered successfully");
                            } else {
                                request.setAttribute("MESSAGE", "Registered failed.");
                            }
                        } catch (SQLException throwables) {
                            request.setAttribute("MESSAGE", "Registered failed. This username has been taken, please try another");
                        } catch (NoSuchAlgorithmException e) {
                            logger.error(e);
                        }
                    }
                }
            }
        } catch (NamingException e) {
            logger.error(e);
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
