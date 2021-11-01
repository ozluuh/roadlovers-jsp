<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:_Layout title="Home">
    <jsp:body>
        <div class="text-center">
            <h1 class="display-4">Bem-vindo</h1>
            <p>Voc&ecirc; pode come&ccedil;ar visitando a seguinte se&ccedil;&atilde;o: <a href='<c:url value="/vehicles" />'>Ve&iacute;culos</a>.</p>
        </div>
    </jsp:body>
</layout:_Layout>
