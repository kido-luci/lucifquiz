<%--
    Document   : shopping
    Created on : Jan 13, 2021, 4:34:01 AM
    Author     : lapl1
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="Responsive Bootstrap4 Shop Template, Created by Imran Hossain from https://imransdesign.com/">

    <!-- title -->
    <title>Lucif Quiz - Quiz Test</title>

    <!-- favicon -->
    <link rel="shortcut icon" type="image/png" href="assets/img/favicon.png">
    <!-- google font -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Poppins:400,700&display=swap" rel="stylesheet">
    <!-- fontawesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
    <!-- bootstrap -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <!-- owl carousel -->
    <link rel="stylesheet" href="assets/css/owl.carousel.css">
    <!-- magnific popup -->
    <link rel="stylesheet" href="assets/css/magnific-popup.css">
    <!-- animate css -->
    <link rel="stylesheet" href="assets/css/animate.css">
    <!-- mean menu css -->
    <link rel="stylesheet" href="assets/css/meanmenu.min.css">
    <!-- main style -->
    <link rel="stylesheet" href="assets/css/main.css">
    <!-- responsive -->
    <link rel="stylesheet" href="assets/css/responsive.css">

</head>

<c:choose>
<c:when test="${not empty requestScope.MESSAGE}">
<body onload="load(); showSnackBar();">
</c:when>
<c:otherwise>
<body onload="load();">
</c:otherwise>
</c:choose>

<!--PreLoader-->
<div class="loader">
    <div class="loader-inner">
        <div class="circle"></div>
    </div>
</div>
<!--PreLoader Ends-->

<!-- header -->
<div class="top-header-area" id="sticker">
    <div class="container">
        <div class="row">
            <div class="col-lg-12 col-sm-12 text-center">
                <div class="main-menu-wrap">
                    <!-- logo -->
                    <%--                    <div class="site-logo">--%>
                    <%--                        <a href="${pageContext.request.contextPath}">--%>
                    <%--                            <img src="assets/img/logo.png" alt="">--%>
                    <%--                        </a>--%>
                    <%--                    </div>--%>
                    <!-- logo -->

                    <!-- menu start -->
                    <nav class="main-menu">
                        <ul>
                            <li>
                                <div class="account-header">
                                    <a>${sessionScope.AUTH_USER.name}</a>
                                    <ul class="sub-menu">
                                        <li><a href="history">History</a></li>
                                        <li><a href="logout">Log Out</a></li>
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </nav>
                    <!-- menu end -->
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end header -->

<!-- breadcrumb-section -->
<div class="breadcrumb-section breadcrumb-bg">
    <div class="container">
        <div class="row">
            <div class="col-lg-8 offset-lg-2 text-center">
                <div class="breadcrumb-text">
                    <p>SE141031</p>
                    <h1>Lucif Quiz</h1>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end breadcrumb section -->

<div class="login-bg" style="width: 760px!important;border-radius: 18px!important;margin: auto!important;">
    <div class="login-content" style="border-radius: 18px!important;border: 2px solid #F28123!important;padding-bottom: 30px!important;background-color: #1f1e1dbf!important;">
        <c:set var="numberOfQuestion" value="${requestScope.NUMBER_OF_QUESTION}"/>
        <c:set var="question" value="${sessionScope.QUESTION_LIST.get(numberOfQuestion - 1)}"/>
        <h2 class="title login" style="font-size: 10px!important; letter-spacing: 2px!important;margin: 10px 0!important;">QUIZ TEST - QUESTION ${numberOfQuestion} OF ${sessionScope.QUESTION_LIST.size()}</h2>
        <form class="login-form">
            <label>
                <div class="question-content" style="display: block;width: 363px;padding: 12px 15px;border-radius: 15px;line-height: 14px;font-size: 12px; outline: none;margin-left: auto;margin-right: auto;margin-bottom: 8px;margin-top: 8px;">Question: ${question.questionContent}</div>
            </label>

            <c:forEach var="answer" items="${question.answerDTOList}">
                <label>
                    <c:choose>
                        <c:when test="${sessionScope.QUIZ_QUESTION_HASH_MAP.get(question.id) == answer.id}">
                            <a href="submit-answer?txtIdAnswerChosen=${answer.id}&txtIdQuestion=${question.id}&numberOfQuestion=${numberOfQuestion}">
                                <button class="btn" type="button" style="text-align: left!important;background-color: #2a9887a6!important;">
                                        ${answer.content}
                                </button>
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="submit-answer?txtIdAnswerChosen=${answer.id}&txtIdQuestion=${question.id}&numberOfQuestion=${numberOfQuestion}">
                                <button class="btn" type="button" style="text-align: left!important;background-color: #2f1109a6!important;">
                                        ${answer.content}
                                </button>
                            </a>
                        </c:otherwise>
                    </c:choose>
                </label>
            </c:forEach>

            <input type="hidden" id="txtEndQuizTime" value="${sessionScope.END_QUIZ_TIME}">
            <h2 class="title login"  style="font-size: 10px!important; letter-spacing: 2px!important;margin: 10px 0!important;" id="timeCountDown"></h2>

            <div style="display: flex;margin: 0 55px">
                <label>
                    <c:set var="previousQuestion" value="${numberOfQuestion}"/>
                    <c:if test="${numberOfQuestion > 1}">
                        <c:set var="previousQuestion" value="${numberOfQuestion - 1}"/>
                    </c:if>
                    <a href="student-quiz?numberOfQuestion=${previousQuestion}">
                        <button class="btn" type="button" style="margin-top: 28px!important;background-color: #e27229a6!important;width: 170px!important;">Previous  Question</button>
                    </a>
                </label>
                <label>
                    <a href="submit-quiz" id="submitQuiz">
                        <button class="btn" type="button" style="margin-top: 28px!important;background-color: #296879d1!important;width: 170px!important;">Submit Quiz</button>
                    </a>
                </label>
                <label>
                    <c:set var="nextQuestion" value="${numberOfQuestion}"/>
                    <c:if test="${numberOfQuestion < sessionScope.QUESTION_LIST.size()}">
                        <c:set var="nextQuestion" value="${numberOfQuestion + 1}"/>
                    </c:if>
                    <a href="student-quiz?numberOfQuestion=${nextQuestion}">
                        <button type="button" class="btn" style="margin-top: 28px!important;background-color: #e27229a6!important;width: 170px!important;">Next Question</button>
                    </a>
                </label>
            </div>
        </form>
    </div>
</div>

<!-- logo carousel -->
<div class="logo-carousel-section">
    <div class="container">
        <div class="row">
            <div class="col-lg-12">
                <div class="logo-carousel-inner">
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/1.png" alt="">
                    </div>
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/2.png" alt="">
                    </div>
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/3.png" alt="">
                    </div>
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/4.png" alt="">
                    </div>
                    <div class="single-logo-item">
                        <img src="assets/img/company-logos/5.png" alt="">
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<!-- end logo carousel -->

<!-- notification -->
<c:if test="${not empty requestScope.MESSAGE}">
    <div id="snackbar">${requestScope.MESSAGE}
        <button onclick="hideSnackBar()" class="hidden-button">Hide</button>
    </div>
</c:if>

<!-- jquery -->
<script src="assets/js/jquery-1.11.3.min.js"></script>
<!-- bootstrap -->
<script src="assets/bootstrap/js/bootstrap.min.js"></script>
<!-- count down -->
<script src="assets/js/jquery.countdown.js"></script>
<!-- isotope -->
<script src="assets/js/jquery.isotope-3.0.6.min.js"></script>
<!-- waypoints -->
<script src="assets/js/waypoints.js"></script>
<!-- owl carousel -->
<script src="assets/js/owl.carousel.min.js"></script>
<!-- magnific popup -->
<script src="assets/js/jquery.magnific-popup.min.js"></script>
<!-- mean menu -->
<script src="assets/js/jquery.meanmenu.min.js"></script>
<!-- sticker js -->
<script src="assets/js/sticker.js"></script>
<!-- main js -->
<script src="assets/js/main.js"></script>

</body>
<script>
    function savePosition(){
        sessionStorage.setItem("position", window.scrollY);
    }
    window.onbeforeunload = savePosition;

    function load(){
        window.scrollTo(0, parseFloat(sessionStorage.getItem("position")));
    }

    //Change time zone
    var stringCountDownDate = document.getElementById("txtEndQuizTime").value;
    var countDownDate = new Date(stringCountDownDate.replace("GMT", "")).getTime();

    var x = setInterval(function() {

        // Get today's date and time
        var now = new Date().getTime();

        // Find the distance between now and the count down date
        var distance = countDownDate - now;

        // Time calculations for days, hours, minutes and seconds
        var hours = Math.floor((distance % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));
        var minutes = Math.floor((distance % (1000 * 60 * 60)) / (1000 * 60));
        var seconds = Math.floor((distance % (1000 * 60)) / 1000);

        // Output the result in an element with id="demo"
        document.getElementById("timeCountDown").innerHTML = "TIME - " + hours + "h:" + minutes + "m:" + seconds + "s";

        // If the count down is over, write some text
        if (distance < 0) {
            clearInterval(x);
            document.getElementById("timeCountDown").innerHTML = "EXPIRED";
            location.replace("${pageContext.request.contextPath}/submit-quiz");
        }
    }, 1000);
</script>
</html>