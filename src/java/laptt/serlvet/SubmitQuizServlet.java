package laptt.serlvet;

import laptt.account.AccountDTO;
import laptt.question.QuestionDTO;
import laptt.quiz.QuizDAO;
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
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "SubmitQuizServlet", urlPatterns = {"/SubmitQuizServlet"})
public class SubmitQuizServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(SubmitQuizServlet.class);

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
                    String idQuiz = httpSession.getAttribute("ID_QUIZ").toString();

                    ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
                    LocalDateTime currentDateTime = LocalDateTime.now(zoneId);
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(Timestamp.valueOf(currentDateTime));
                    Date submitTime = calendar.getTime();
                    request.setAttribute("SUBMIT_TIME", submitTime);

                    int numberAnswerCorrect = 0;

                    HashMap<String, String> quizQuestionHashMap = (HashMap<String, String>) httpSession.getAttribute("QUIZ_QUESTION_HASH_MAP");
                    for (QuestionDTO questionDTO : questionDTOList) {
                        String answer = quizQuestionHashMap.get(questionDTO.getId());
                        if (questionDTO.getIdAnswerCorrect().equals(answer)) {
                            numberAnswerCorrect++;
                        }
                    }

                    QuizDAO quizDAO = new QuizDAO();
                    quizDAO.updateQuizToDB(idQuiz, accountDTO.getEmail(), numberAnswerCorrect);

                    request.setAttribute("NUMBER_ANSWER_CORRECT", numberAnswerCorrect);
                    request.setAttribute("NUMBER_OF_QUESTION", questionDTOList.size());

                    httpSession.removeAttribute("QUESTION_LIST");
                    httpSession.removeAttribute("QUIZ_QUESTION_HASH_MAP");
                    httpSession.removeAttribute("ID_QUIZ");
                    httpSession.removeAttribute("END_QUIZ_TIME");

                    ServletContext servletContext = request.getServletContext();
                    url = servletContext.getInitParameter("quiz-result-jsp");
                }
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
