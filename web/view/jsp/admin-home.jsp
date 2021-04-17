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
    <title>Lucif Quiz - Admin Home</title>

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
                                <div class="search-bar">
                                    <div class="search-bar-tablecell">
                                        <form action="search-question" method="post" style="display: flex">

                                            <h3 style="font-size: 15px;color: #a8c6d4;margin: auto 0">Subject</h3>

                                            <select name="txtSubject" size="1" class="combobox" style="margin: auto 28px auto 1px; height: 70%;">
                                                <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                                                    <c:choose>
                                                        <c:when test="${subject.id == param.txtSubject}">
                                                            <option selected value="${subject.id}">${subject.name}</option>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <option value="${subject.id}">${subject.name}</option>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </c:forEach>
                                            </select>

                                            <h3 style="font-size: 15px;color: #a8c6d4;margin: auto 0">Status</h3>

                                            <select name="txtStatusSearch" size="1" class="combobox" style="margin: auto 28px auto 1px; height: 70%;">
                                                <c:choose>
                                                    <c:when test="${'Activate' == param.txtStatusSearch}">
                                                        <option selected>Activate</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option>Activate</option>
                                                    </c:otherwise>
                                                </c:choose>
                                                <c:choose>
                                                    <c:when test="${'Deactivate' == param.txtStatusSearch}">
                                                        <option selected>Deactivate</option>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <option>Deactivate</option>
                                                    </c:otherwise>
                                                </c:choose>
                                            </select>

                                            <input type="text" placeholder="Search question content" name="txtSearchValue" value="${param.txtSearchValue}">
                                            <button class="search-bar-icon" type="submit" value="searchItem" name="btAction"><i class="fa fa-search fa-1x"></i></button>
                                        </form>
                                    </div>
                                </div>
                            </li>
                            <li>
                                <div class="account-header">
                                    <a>${sessionScope.AUTH_USER.name}</a>
                                    <ul class="sub-menu">
                                        <li><a href="create-new-question?btAction=toCreateNewQuestionPage">Create New Question</a></li>
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

        <c:forEach var="question" items="${requestScope.QUESTION_LIST}">
            <div class="login-content" style="border-radius: 18px!important;border: 2px solid #F28123!important;padding-bottom: 30px!important;background-color: #1f1e1dbf!important;">
                <h2 class="title login" style="font-size: 10px!important; letter-spacing: 2px!important;margin: 10px 0!important;">Create date : ${question.createDate}</h2>
                <h2 class="title login" style="font-size: 10px!important; letter-spacing: 2px!important;margin: 10px 0!important;">ID : ${question.id}</h2>
                <form class="login-form" action="update-question" method="post">
                    <input type="hidden" name="txtIdQuestion" value="${question.id}">
                    <input type="hidden" name="txtSearchValue" value="${param.txtSearchValue}">
                    <input type="hidden" name="txtSubject" value="${param.Subject}">
                    <input type="hidden" name="txtStatusSearch" value="${param.txtStatusSearch}">

                    <label>
                        <input class="question-content" type="text" name="txtQuestionContent" placeholder="Question Content" value="${question.questionContent}" autocomplete="new-password"/>
                    </label>

                    <c:set var="answerList" value="${question.answerDTOList}"/>
                    <c:set var="answerCorrect" value="Answer 1"/>

                    <label>
                        <input type="text" name="txtAnswer1" placeholder="Answer 1" value="${answerList.get(0).content}" autocomplete="new-password"/>
                        <input type="hidden" name="txtIdAnswer1" value="${answerList.get(0).id}">
                    </label>

                    <label>
                        <input type="text" name="txtAnswer2" placeholder="Answer 2" value="${answerList.get(1).content}" autocomplete="new-password"/>
                        <input type="hidden" name="txtIdAnswer2" value="${answerList.get(1).id}">
                        <c:if test="${answerList.get(1).id == question.idAnswerCorrect}">
                            <c:set var="answerCorrect" value="Answer 2"/>
                        </c:if>
                    </label>

                    <label>
                        <input type="text" name="txtAnswer3" placeholder="Answer 3" value="${answerList.get(2).content}" autocomplete="new-password"/>
                        <input type="hidden" name="txtIdAnswer3" value="${answerList.get(2).id}">
                        <c:if test="${answerList.get(2).id == question.idAnswerCorrect}">
                            <c:set var="answerCorrect" value="Answer 3"/>
                        </c:if>
                    </label>

                    <label>
                        <input type="text" name="txtAnswer4" placeholder="Answer 4" value="${answerList.get(3).content}" autocomplete="new-password"/>
                        <input type="hidden" name="txtIdAnswer4" value="${answerList.get(3).id}">
                        <c:if test="${answerList.get(3).id == question.idAnswerCorrect}">
                            <c:set var="answerCorrect" value="Answer 4"/>
                        </c:if>
                    </label>

                    <div style="display: flex">
                        <h3 style="margin-left: 186px; font-size: 18px;color: #a8c6d4">Subject</h3>
                        <h3 style="margin-left: 48px; font-size: 18px;color: #a8c6d4">Correct Answer</h3>
                        <h3 style="margin-left: 48px; font-size: 18px;color: #a8c6d4">Status</h3>
                    </div>


                    <div style="display: flex">
                        <select name="txtIdSubject" size="1" class="combobox" style="margin-left: auto; margin-right: 25px;">
                            <c:forEach var="subject" items="${requestScope.SUBJECT_LIST}">
                                <c:choose>
                                    <c:when test="${subject.id == question.idSubject}">
                                        <option selected value="${subject.id}">${subject.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${subject.id}">${subject.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </select>

                        <select name="txtCorrectAnswer" size="1" class="combobox" style="margin-right: 25px; margin-left: 25px;">
                            <c:choose>
                                <c:when test="${'Answer 1' == answerCorrect}">
                                    <option selected value="${answerList.get(0).id}">Answer 1</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${answerList.get(0).id}">Answer 1</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'Answer 2' == answerCorrect}">
                                    <option selected value="${answerList.get(1).id}">Answer 2</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${answerList.get(1).id}">Answer 2</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'Answer 3' == answerCorrect}">
                                    <option selected value="${answerList.get(2).id}">Answer 3</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${answerList.get(2).id}">Answer 3</option>
                                </c:otherwise>
                            </c:choose>
                            <c:choose>
                                <c:when test="${'Answer 4' == answerCorrect}">
                                    <option selected value="${answerList.get(3).id}">Answer 4</option>
                                </c:when>
                                <c:otherwise>
                                    <option value="${answerList.get(3).id}">Answer 4</option>
                                </c:otherwise>
                            </c:choose>
                        </select>

                        <select name="txtStatus" size="1" class="combobox" style="margin-right: auto; margin-left: 25px;">
                            <c:choose>
                                <c:when test="${'Activate' == question.status}">
                                    <option selected>Activate</option>
                                    <option>Deactivate</option>
                                </c:when>
                                <c:otherwise>
                                    <option>Activate</option>
                                    <option selected>Deactivate</option>
                                </c:otherwise>
                            </c:choose>
                        </select>
                    </div>

                    <label>
                        <button class="btn" type="submit" style="margin-top: 28px!important;background-color: #f97e00a6!important;">Update Question</button>
                    </label>
                </form>
            </div>
        </c:forEach>
    </div>

        <!-- paging -->
<%--        <div class="row">--%>
<%--            <div class="col-lg-12 text-center">--%>
<%--                <div class="pagination-wrap">--%>
<%--                    <ul>--%>
<%--                        <c:set var="shoppingPage" value="${requestScope.ShoppingPage}"/>--%>
<%--                        <c:choose>--%>
<%--                            <c:when test="${shoppingPage > 1}">--%>
<%--                                <li><a href="DispatchServlet?btAction=searchItem&minPriceInput=${param.minPriceInput}&txtShoppingItemCategorySearch=${param.txtShoppingItemCategorySearch}&maxPriceInput=${param.maxPriceInput}&txtSearchValue=${param.txtSearchValue}&page=${shoppingPage - 1}">Prev</a></li>--%>
<%--                            </c:when>--%>
<%--                            <c:otherwise>--%>
<%--                                <li><a href="DispatchServlet?btAction=searchItem&minPriceInput=${param.minPriceInput}&txtShoppingItemCategorySearch=${param.txtShoppingItemCategorySearch}&maxPriceInput=${param.maxPriceInput}&txtSearchValue=${param.txtSearchValue}&page=1">Prev</a></li>--%>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>
<%--                        <c:forEach var="i" begin="1" end="${requestScope.QuantityOfShoppingPage}">--%>
<%--                            <c:choose>--%>
<%--                                <c:when test="${shoppingPage == i}">--%>
<%--                                    <li><a class="active" href="DispatchServlet?btAction=searchItem&minPriceInput=${param.minPriceInput}&txtShoppingItemCategorySearch=${param.txtShoppingItemCategorySearch}&maxPriceInput=${param.maxPriceInput}&txtSearchValue=${param.txtSearchValue}&page=${i}">${i}</a></li>--%>
<%--                                </c:when>--%>
<%--                                <c:otherwise>--%>
<%--                                    <li><a href="DispatchServlet?btAction=searchItem&minPriceInput=${param.minPriceInput}&txtShoppingItemCategorySearch=${param.txtShoppingItemCategorySearch}&maxPriceInput=${param.maxPriceInput}&txtSearchValue=${param.txtSearchValue}&page=${i}">${i}</a></li>--%>
<%--                                </c:otherwise>--%>
<%--                            </c:choose>--%>
<%--                        </c:forEach>--%>
<%--                        <c:choose>--%>
<%--                            <c:when test="${shoppingPage < requestScope.QuantityOfShoppingPage}">--%>
<%--                                <li><a href="DispatchServlet?btAction=searchItem&minPriceInput=${param.minPriceInput}&txtShoppingItemCategorySearch=${param.txtShoppingItemCategorySearch}&maxPriceInput=${param.maxPriceInput}&txtSearchValue=${param.txtSearchValue}&page=${shoppingPage + 1}">Prev</a></li>--%>
<%--                            </c:when>--%>
<%--                            <c:otherwise>--%>
<%--                                <li><a href="DispatchServlet?btAction=searchItem&minPriceInput=${param.minPriceInput}&txtShoppingItemCategorySearch=${param.txtShoppingItemCategorySearch}&maxPriceInput=${param.maxPriceInput}&txtSearchValue=${param.txtSearchValue}&page=${requestScope.QuantityOfShoppingPage}">Prev</a></li>--%>
<%--                            </c:otherwise>--%>
<%--                        </c:choose>--%>
<%--                    </ul>--%>
<%--                </div>--%>
<%--            </div>--%>
<%--        </div>--%>
<%--    </div>--%>
    <!-- paging -->

<!-- end products -->

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
</script>
</html>