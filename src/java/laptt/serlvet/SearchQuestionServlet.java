package laptt.serlvet;

import laptt.account.AccountDTO;
import laptt.question.QuestionDAO;
import laptt.subject.SubjectDAO;
import laptt.subject.SubjectDTO;
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
import java.text.ParseException;
import java.util.List;


@WebServlet(name = "SearchQuestionServlet", urlPatterns = {"/SearchQuestionServlet"})
public class SearchQuestionServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SearchQuestionServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        ServletContext servletContext = request.getServletContext();
        String url = servletContext.getInitParameter("admin-home-jsp");

        try {
            AccountDTO accountDTO = null;
            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                accountDTO = (AccountDTO)httpSession.getAttribute("AUTH_USER");
            }

            if (accountDTO != null && accountDTO.getRole().equals("admin")) {
                SubjectDAO subjectDAO = new SubjectDAO();
                subjectDAO.setSubjectDTOList();
                List<SubjectDTO> subjectDTOList = subjectDAO.getSubjectDTOList();
                request.setAttribute("SUBJECT_LIST", subjectDTOList);

                String searchContent = request.getParameter("txtSearchValue");
                String subject = request.getParameter("txtSubject");
                String status = request.getParameter("txtStatusSearch");

                searchContent = searchContent == null ? "%%" : "%" + searchContent + "%";

                if (subject == null || subject.isEmpty()) {
                    subject = subjectDTOList.get(0).getId();
                }

                if (status == null || status.isEmpty()) {
                    status = "Activate";
                }

                QuestionDAO questionDAO = new QuestionDAO();
                questionDAO.setQuestionDTOList(searchContent, subject, status);
                request.setAttribute("QUESTION_LIST", questionDAO.getQuestionDTOList());
            } else {
                url = request.getContextPath();
            }
        } catch (SQLException | NamingException | ParseException throwables) {
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
