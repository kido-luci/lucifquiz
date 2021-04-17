package laptt.serlvet;

import laptt.account.AccountDTO;
import laptt.question.QuestionDAO;
import laptt.question.QuestionDTO;
import laptt.quiz.QuizDAO;
import laptt.quizQuestion.QuizQuestionDAO;
import laptt.utilities.DBUtilities;
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
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
import java.util.Date;
import java.util.HashMap;

@WebServlet(name = "CreateQuizTestServlet", urlPatterns = {"/CreateQuizTestServlet"})
public class CreateQuizTestServlet extends HttpServlet {

    private static final Logger logger = Logger.getLogger(CreateQuizTestServlet.class);

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();

        ServletContext servletContext = request.getServletContext();
        String url = servletContext.getInitParameter("student-quiz-jsp");

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
                    } catch (NumberFormatException | NullPointerException numberFormatException) {

                    }

                    if (numberOfQuestion < 1 || numberOfQuestion > questionDTOList.size()) {
                        numberOfQuestion = 1;
                    }

                    request.setAttribute("NUMBER_OF_QUESTION", numberOfQuestion);
                } else {
                    String idSubject = request.getParameter("txtIdSubject");

                    Random random = new Random();
                    int randomQuantity = random.nextInt(3) + 1;

                    QuestionDAO questionDAO = new QuestionDAO();
                    questionDAO.setQuestionDTOList(idSubject, randomQuantity);
                    questionDTOList = questionDAO.getQuestionDTOList();

                    if (questionDTOList.size() == 0) {
                        request.setAttribute("MESSAGE", "This subject have no question. Please try again later");

                        url = servletContext.getInitParameter("create-student-home");
                    } else {
                        httpSession = request.getSession(true);
                        httpSession.setAttribute("QUESTION_LIST", questionDTOList);

                        ZoneId zoneId = ZoneId.of("Asia/Ho_Chi_Minh");
                        LocalDateTime currentDateTime = LocalDateTime.now(zoneId);

                        int time = questionDTOList.size() * 2;

                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(Timestamp.valueOf(currentDateTime));
                        calendar.add(Calendar.MINUTE, time);
                        Date endQuizTime = calendar.getTime();
                        httpSession.setAttribute("END_QUIZ_TIME", endQuizTime);

                        request.setAttribute("NUMBER_OF_QUESTION", 1);

                        //database
                        String idQuiz = DBUtilities.takeNewId();

                        QuizDAO quizDAO = new QuizDAO();
                        quizDAO.insertQuizToDB(idQuiz, accountDTO.getEmail(), idSubject, questionDTOList.size());

                        QuizQuestionDAO quizQuestionDAO = new QuizQuestionDAO();
                        for (QuestionDTO questionDTO : questionDTOList) {
                            quizQuestionDAO.insertQuizQuestionToDB(idQuiz, questionDTO.getId(), questionDTO.getIdAnswerCorrect());
                        }

                        HashMap<String, String> quizQuestionHashMap = quizQuestionHashMap = new HashMap<>();
                        for (QuestionDTO questionDTO : questionDTOList) {
                            quizQuestionHashMap.put(questionDTO.getId(), "");
                        }

                        httpSession.setAttribute("QUIZ_QUESTION_HASH_MAP", quizQuestionHashMap);
                        httpSession.setAttribute("ID_QUIZ", idQuiz);
                    }
                }
            } else {
                url = request.getContextPath();
            }
        } catch (SQLException | ParseException | NamingException throwables) {
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
