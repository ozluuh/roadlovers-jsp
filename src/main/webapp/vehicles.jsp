<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="layout" tagdir="/WEB-INF/tags/template" %>
<%@ taglib prefix="component" tagdir="/WEB-INF/tags/components" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="cf" uri="/WEB-INF/functions.tld" %>

<layout:_Layout title="Vehicles">

	<jsp:attribute name="scripts">
		<script>
			document.addEventListener("DOMContentLoaded", function(event) {
				document.querySelectorAll('.vehicle-remove').forEach(elmnt => {
					elmnt.addEventListener("click", event => {
						const button = elmnt;
						
						const vehId = elmnt.getAttribute("data-vehicle-id");
						const vehModel = elmnt.getAttribute("data-vehicle-model");
						const vehManufacturer = elmnt.getAttribute("data-vehicle-manufacturer");
	
						document.querySelector("#vehicleId").value = parseInt(vehId);
						document.querySelector("#vehicleModelToRemove").innerText = vehManufacturer + " " + vehModel;
					});
				});
			});
		</script>
	</jsp:attribute>

	<jsp:body>
		<h1>Veículos Cadastrados</h1>
		
		<c:if test="${not empty message}">
			<component:alert message="${message}" type="${severity}" />
			<c:remove var="message" scope="session" />
		</c:if>

		<div class="d-flex justify-content-between align-items-center mb-2">
			<div class="w--75">
				<form method="GET">
					<div class="input-group">
						<label for="year" class="input-group-text">Pesquise pelo ano</label>
						<input
							type="number"
							name="nbrYear"
							id="year"
							min="1920"
							max="9999"
							class="form-control"
							placeholder="Ano do veículo"
						/>
						<button class="btn btn-outline-primary" title="Pesquisar" type="submit">
							<i class="bi bi-search"></i>
							<span class="ms-2 ml-2 d-none d-md-inline">Buscar</span>
						</button>
					</div>
				</form>
			</div>
			<a class="btn btn-outline-secondary" href='<c:url value="/vehicles/new" />' title="Cadastrar novo veículo">
				<i class="bi bi-plus-lg"></i>
				<span class="ms-2 text-capitalize"><span class="d-none d-md-inline">Cadastrar</span> veículo</span>
			</a>
		</div>

		<div class="table-responsive-md">
			<table class="table table-striped table-hover table-sm table-bordered table-primary">
				<caption>
					Total de veículos&nbsp;
					<c:choose>
						<c:when test="${filter}">encontrados</c:when>
						<c:otherwise>cadastrados</c:otherwise>
					</c:choose> 
					&nbsp;em nossa base:&nbsp;
					<c:out value="${vehiclesList.size()}" /> 
				</caption>
				<thead class="thead-dark">
					<tr class="text-center">
						<th scope="col">#</th>
						<th scope="col">Ano</th>
						<th scope="col">Fabricante</th>
						<th scope="col">Modelo</th>
						<th scope="col">Classe</th>
						<th scope="col">Valor</th>
						<th scope="col">Dt. Registro</th>
						<th scope="col">Ações</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="vehicle" items="${vehiclesList}" varStatus="current">
						<tr class="text-center">
							<th scope="row">
								<div class="text-end">
									${current.count}
								</div>
							</th>
							<td>${vehicle.year}</td>
							<td>${vehicle.manufacturer.description}</td>
							<td>${vehicle.model}</td>
							<td>${vehicle.classe.description}</td>
							<td>
								<div class="d-flex justify-content-between">
									<span>R$</span>
									<span>${cf:formatCurrency(vehicle.value, "#,##0.00")}</span>
								</div>
							</td>
							<td>
								<div>
									${cf:formatLocalDateTime(vehicle.createdAt, "dd/MM/yyyy HH:mm")}
								</div>
							</td>
							<td>
								<div class="d-flex justify-content-center">
									<div class="btn-group btn-group-sm" role="group" aria-label="Ações">
										<a href='<c:url value="/vehicles/edit?id=${vehicle.id}" />' class="btn btn-outline-info" title="Editar">
											<i class="bi bi-pencil"></i>
											<span class="visually-hidden">Editar registro</span>
										</a>
		
										<button 
											class="btn btn-outline-danger vehicle-remove"
											title="Excluir"
											data-bs-toggle="modal"
											data-bs-target="#removeVehicleModal"
											data-vehicle-id="${vehicle.id}"
											data-vehicle-model="${vehicle.model}"
											data-vehicle-manufacturer="${vehicle.manufacturer.description}"
										>
											<i class="bi bi-trash"></i>
											<span class="visually-hidden">Remover registro</span>
										</button>
									</div>
								</div>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>

		<!-- Modal -->
		<div
			class="modal fade"
			id="removeVehicleModal"
			data-keyboard="false"
			tabindex="-1"
			aria-labelledby="removeVehicleModalLabel"
			aria-hidden="true"
		>
			<div class="modal-dialog">
				<div class="modal-content bg-dark">
					<div class="modal-header">
						<h5 class="modal-title" id="removeVehicleModalLabel">Você tem total certeza?</h5>
						<button type="button" class="btn-close bg-danger" data-bs-dismiss="modal" aria-label="Close">
							<span aria-hidden="true"></span>
						</button>
					</div>

					<div class="modal-body">
						<p>
							Essa ação não poderá ser revertida. Isso fará com que o veículo
							<span id="vehicleModelToRemove" class="font-weight-bold text-danger"></span>
							 e todos os seus dados sejam completamente removidos.
						</p>
					</div>

					<div class="modal-footer justify-content-center">
						<form action='<c:url value="/vehicles/delete" />' method="POST" class="w-100">
							<input type="hidden" name="vehicleId" id="vehicleId" />
							<button type="submit" class="btn btn-outline-danger w-100 font-weight-bold">
								Entendo as consequências, remova o registro do veículo
							</button>
						</form>
					</div>
				</div>
			</div>
		</div>
	</jsp:body>
</layout:_Layout>