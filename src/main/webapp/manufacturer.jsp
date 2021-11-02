<%--
    Document   : manufacturer
    Created on : Nov 1, 2021, 9:55:48 PM
    Author     : ozluuh
--%>

<%@ page contentType="text/html" pageEncoding="UTF-8" %>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<layout:_Layout title="Fabricantes">

    <jsp:body>
        <c:url var="form" value="/manufacturer" />
        <h1 class="display-4">Fabricantes</h1>

        <div class="row">
            <div class="col-6">
                <form action='${form}/store' method="POST" autocomplete="off">
                    <div class="input-group">
                        <div class="form-floating">
                            <input name="txtManufacturerName" id="Name" placeholder="Nome da fabricante" class="form-control">
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
                    <caption>Total de Fabricantes cadastradas: 13</caption>
                    <thead>
                        <tr class="text-center">
                            <th scope="col">Nome</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:if test="${empty manufacturers}">
                            <tr class="text-center">
                                <td class="">Nenhuma fabricante cadastrada...</td>
                            </tr>
                        </c:if>
                        <c:forEach var="manufacturer" items="${manufacturers}">
                            <tr class="text-center">
                                <td class="text-start text-left">${manufacturer.description}</td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>
        </div>
    </jsp:body>

</layout:_Layout>
