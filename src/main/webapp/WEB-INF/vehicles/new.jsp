<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:_Layout title="Cadastro">

	<jsp:attribute name="scripts">
		<script src='<c:url value="/js/site.js" />'></script>
	</jsp:attribute>

	<jsp:body>
		<c:url var="form" value="/vehicles" />
		<h1>Cadastro de Veículos</h1>
		<form action='${form}/store' method="POST" autocomplete="off">
		
		    <!-- FORM -->
		    <jsp:include page="_Form.jsp" />
		
		    <div class="d-flex justify-content-end">
		        <div class="btn-group">
		            <button class="btn btn-primary" type="submit">Cadastrar</button>
		            <a class="btn btn-outline-danger" href='${form}'>Voltar</a>
		        </div>
		    </div>
		</form>
	</jsp:body>

</layout:_Layout>