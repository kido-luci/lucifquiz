package laptt.serlvet;

import laptt.account.AccountDTO;
import laptt.question.QuestionDTO;
import laptt.quizQuestion.QuizQuestionDAO;
import org.apache.log4j.Logger;

import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "SubmitAnswerServlet", urlPatterns = {"/SubmitAnswerServlet"})
public class SubmitAnswerServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SubmitAnswerServlet.class);

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

            if (accountDTO != null && accountDTO.getRole().equals("student")) {
                List<QuestionDTO> questionDTOList = (List<QuestionDTO>) httpSession.getAttribute("QUESTION_LIST");
                if (questionDTOList != null) {
                    int numberOfQuestion = 1;

                    try {
                        numberOfQuestion = Integer.parseInt(request.getParameter("numberOfQuestion"));
                    } catch (NumberFormatException | NullPointerException e) {
                        logger.error(e);
                    }

                    if (numberOfQuestion < 1 || numberOfQuestion > questionDTOList.size()) {
                        numberOfQuestion = 1;
                    }

                    String idQuestion = request.getParameter("txtIdQuestion");
                    String idAnswerChosen = request.getParameter("txtIdAnswerChosen");
                    String idQuiz = httpSession.getAttribute("ID_QUIZ").toString();

                    QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO();
                    quizQuestionDAO.updateQuizToDB(idQuiz, idQuestion, idAnswerChosen);

                    HashMap<String, String> quizQuestionHashMap = (HashMap<String, String>) httpSession.getAttribute("QUIZ_QUESTION_HASH_MAP");
                    if (quizQuestionHashMap == null) {
                        quizQuestionHashMap = new HashMap<>();

                        for (QuestionDTO questionDTO : questionDTOList) {
                            quizQuestionHashMap.put(questionDTO.getId(), "");
                        }
                    }

                    quizQuestionHashMap.put(idQuestion, idAnswerChosen);
                    httpSession.setAttribute("QUIZ_QUESTION_HASH_MAP", quizQuestionHashMap);

                    url = "student-quiz?numberOfQuestion=" + numberOfQuestion;
                }
            }
        } catch (SQLException | NamingException throwables) {
            logger.error(throwables);
        } finally {
            response.sendRedirect(url);

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
