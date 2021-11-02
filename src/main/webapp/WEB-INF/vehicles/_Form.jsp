<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="mb-2">
    <input
        name="idVehicle"
        id="Id"
        value="${vehicle.id}"
        hidden
        />
    <div class="form-group">
        <label for="Model">Modelo</label>
        <input
            name="txtVehicleModel"
            id="Model"
            class="form-control"
            placeholder="Informe o modelo do ve&iacute;culo..."
            value="${vehicle.model}"
            required
            />
    </div>

    <div class="form-group">
        <label for="Year">Ano</label>
        <input
            name="nbrVehicleYear"
            id="Year"
            class="form-control"
            min="1920"
            max="9999"
            placeholder="Informe o ano do ve&iacute;culo..."
            value="${vehicle.year}"
            />
    </div>

    <div class="form-group">
        <label for="Manufacturer">Fabricante</label>
        <select
            name="slcVehicleManufacturer"
            id="Manufacturer"
            class="custom-select"
            required
        >
            <c:if test="${empty vehicle.manufacturer.id}">
                <option hidden selected label="Selecione a fabricante..." />
            </c:if>

            <c:forEach var="manufacturer" items="${manufacturers}">
                <c:choose>
                    <c:when test="${not empty vehicle.manufacturer.id and vehicle.manufacturer.id eq manufacturer.id}">
                        <option selected value="${manufacturer.id}" label="${manufacturer.description}" />
                    </c:when>
                    <c:otherwise>
                        <option value="${manufacturer.id}" label="${manufacturer.description}" />
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="VehicleType">Classe</label>
        <select
            name="slcVehicleType"
            id="VehicleType"
            class="custom-select"
            required
        >
            <c:if test="${empty vehicle.classe.id}">
                <option hidden selected label="Selecione a classe..." />
            </c:if>

            <c:forEach var="classe" items="${classes}">
                <c:choose>
                    <c:when test="${not empty vehicle.classe.id and vehicle.classe.id eq classe.id}">
                        <option selected value="${classe.id}" label="${classe.description}" />
                    </c:when>
                    <c:otherwise>
                        <option value="${classe.id}" label="${classe.description}" />
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </select>
    </div>

    <div class="form-group">
        <label for="Value">Valor</label>
        <input
            name="vlrVehicleValue"
            id="Value"
            class="form-control hidden-arrow"
            step="any"
            min="1"
            placeholder="Informe o valor do ve&iacute;culo..."
            pattern="^\d+\.?\d{2}$"
            value="${vehicle.value}"
            required
            />
    </div>
</div>
