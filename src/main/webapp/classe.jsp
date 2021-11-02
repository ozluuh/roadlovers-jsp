<%--
    Document   : classe
    Created on : Nov 1, 2021, 9:57:35 PM
    Author     : ozluuh
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:_Layout title="Classes">

    <jsp:body>
        <c:url var="form" value="/classe" />
        <h1 class="display-4">Classes</h1>

        <c:if test="${not empty message}">
            <component:alert message="${message}" type="${severity}" />
            <c:remove var="message" scope="session" />
        </c:if>

        <div class="row">
            <div class="col-6">
                <form action='${form}/store' method="POST" autocomplete="off">
                    <div class="input-group">
                        <div class="form-floating">
                            <input name="txtClasseName" id="Name" placeholder="e.g. TUNER, LUXO..." class="form-control">
                            <label for="Name">Nome</label>
                        </div>

                        <button type="submit" class="btn btn-outline-primary d-flex">
                            <i class="bi bi-save"></i>
                            <span class="d-none d-md-block ms-2 ml-2">Salvar</span>
                        </button>
                    </div>
                </form>
            </div>
            <div class="col-6">
                <table class="table table-striped">
                    <caption>Total de Classes cadastradas: ${classes.size()}</caption>
                    <thead>
                        <tr class="text-center">
                            <th scope="col">Nome</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty classes}">
                            <tr class="text-center">
                                <td class="">Nenhuma classe cadastrada...</td>
                            </tr>
                        </c:if>
                        <c:forEach var="classe" items="${classes}">
                            <tr class="text-center">
                                <td class="text-start text-left">${classe.description}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </jsp:body>

</layout:_Layout>
