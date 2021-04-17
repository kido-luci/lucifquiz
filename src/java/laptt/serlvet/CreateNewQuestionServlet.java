package laptt.serlvet;

import laptt.account.AccountDTO;
import laptt.question.QuestionDAO;
import laptt.subject.SubjectDAO;
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

@WebServlet(name = "CreateNewQuestionServlet", urlPatterns = {"/CreateNewQuestionServlet"})
public class CreateNewQuestionServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CreateNewQuestionServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        ServletContext servletContext = request.getServletContext();
        String url = servletContext.getInitParameter("create-new-question-jsp");

        try {
            AccountDTO accountDTO = null;

            HttpSession httpSession = request.getSession(false);
            if (httpSession != null) {
                accountDTO = (AccountDTO)httpSession.getAttribute("AUTH_USER");
            }

            if (accountDTO != null && accountDTO.getRole().equals("admin")) {
                SubjectDAO subjectDAO = new SubjectDAO();
                subjectDAO.setSubjectDTOList();
                request.setAttribute("SUBJECT_LIST", subjectDAO.getSubjectDTOList());

                String btAction = request.getParameter("btAction");
                if (btAction == null || !btAction.equals("toCreateNewQuestionPage")) {
                    String questionContent = request.getParameter("txtQuestionContent");
                    String answer1 = request.getParameter("txtAnswer1");
                    String answer2 = request.getParameter("txtAnswer2");
                    String answer3 = request.getParameter("txtAnswer3");
                    String answer4 = request.getParameter("txtAnswer4");
                    String idSubject = request.getParameter("txtSubject");
                    String correctAnswer = request.getParameter("txtCorrectAnswer");

                    if (questionContent.isEmpty() || answer1.isEmpty() || answer2.isEmpty() || answer3.isEmpty() || answer4.isEmpty() || idSubject.isEmpty() || correctAnswer.isEmpty()) {
                        request.setAttribute("MESSAGE", "Question created failed! Question content and answer can't be blank");
                    } else {
                        QuestionDAO questionDAO = new QuestionDAO();
                        boolean isInsert = questionDAO.insertQuestionToDB(questionContent, answer1, answer2, answer3, answer4, idSubject, correctAnswer);
                        if (isInsert) {
                            request.setAttribute("MESSAGE", "Question created successfully!");
                        } else {
                            request.setAttribute("MESSAGE", "Question created failed!");
                        }
                    }
                }
            } else {
                url = request.getContextPath();
            }
        } catch (SQLException | NamingException throwables) {
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
