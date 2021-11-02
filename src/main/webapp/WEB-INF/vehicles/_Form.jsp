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
            <option value="" label="Selecione a fabricante..." />
            <c:forEach var="manufacturer" items="${manufacturers}">
                <option value="${manufacturer.id}" label="${manufacturer.description}" />
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
            <option value="" label="Selecione a classe..." />
            <c:forEach var="classe" items="${classes}">
                <option value="${classe.id}" label="${classe.description}" />
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
