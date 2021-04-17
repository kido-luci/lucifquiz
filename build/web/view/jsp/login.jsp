<%--
  Created by IntelliJ IDEA.
  User: Admin
  Date: 9/10/2020
  Time: 9:49 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Lucif Quiz - Login</title>

    <!-- favicon -->
    <link rel="shortcut icon" type="image/png" href="assets/img/favicon.png">
    <!-- google font -->
    <link href="https://fonts.googleapis.com/css?family=Open+Sans:300,400,700" rel="stylesheet">
    <link href="https://fonts.googleapis.com/css?family=Poppins:400,700&display=swap" rel="stylesheet">
    <!-- fontawesome -->
    <link rel="stylesheet" href="assets/css/all.min.css">
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

    <script>
        window.history.forward();
    </script>
</head>

<c:choose>
    <c:when test="${not empty requestScope.MESSAGE}">
        <body onload="showSnackBar();" class="login-bg">
    </c:when>
    <c:otherwise>
        <body class="login-bg">
    </c:otherwise>
</c:choose>

<div class="login-container">

    <div class="container" style="margin-top: 15px">
        <div class="row">
            <div class="col-lg-8 offset-lg-2 text-center">
                <div class="breadcrumb-text">
                    <a href="${pageContext.request.contextPath}">
                        <p>SE141031</p>
                        <h1>LUCIF QUIZ</h1>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <div class="login-content">
        <h2 class="title login">Log In</h2>
        <form class="login-form" action="login" method="post">
            <label>
                <input type="text" name="txtEmail" placeholder="Email" value="${param.txtEmail}" autocomplete="new-password"/>
            </label>

            <label>
                <input type="password" name="txtPassword" placeholder="Password" value="" autocomplete="new-password"/>
            </label>

            <label>
                <button class="btn" type="submit">Log in with account</button>
            </label>

            <div class="divider">
                <span>or</span>
            </div>

            <label>
                <a href="https://www.facebook.com/dialog/oauth?client_id=420490785922918&redirect_uri=http://localhost:8084/lucifquiz/login-facebook&scope=email">
                    <button class="btn" type="button" style="background-color: #29e2d6a6!important;">
                        Log in with facebook
                    </button>
                </a>
            </label>

            <label>
                <a href="register?btAction=toRegisterPage" style="margin: 0 auto">Don't have an account? Register here</a>
            </label>
        </form>
    </div>
</div>

<!-- notification -->
<c:if test="${not empty requestScope.MESSAGE}">
    <div id="snackbar">${requestScope.MESSAGE}
        <button onclick="hideSnackBar()" class="hidden-button">Hide</button>
    </div>
</c:if>

<!-- main js -->
<script src="assets/js/main.js"></script>

</body>
</html>