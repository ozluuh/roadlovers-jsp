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
            placeholder="Informe o modelo do veículo..."
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
            placeholder="Informe o ano do veículo..."
            value="${vehicle.year}"
        />
    </div>

    <div class="form-group">
        <label for="Manufacturer">Fabricante</label>
        <input
            type="text"
            name="slcVehicleManufacturer"
            id="Manufacturer"
            class="form-control"
            placeholder="Informe a fabricante do veículo..."
            list="manufacturers"
            value="${vehicle.manufacturer}"
            required
        />
        <datalist id="manufacturers" class="collapse">
            <c:forEach var="manufacturer" items="${manufacturers}">
                <option value="${manufacturer}" label="${manufacturer.description}" />
            </c:forEach>
        </datalist>
    </div>

    <div class="form-group">
        <label for="VehicleType">Classe</label>
        <input
            name="slcVehicleType"
            id="VehicleType"
            class="form-control"
            placeholder="Informe a classe do veículo..."
            value="${vehicle.classe}"
            list="classes"
            required
        />
        <datalist id="classes" class="collapse">
            <c:forEach var="classe" items="${classes}">
                <option value="${classe}" label="${classe.description}" />
            </c:forEach>
        </datalist>
    </div>

    <div class="form-group">
        <label for="Value">Valor</label>
        <input
            name="vlrVehicleValue"
            id="Value"
            class="form-control hidden-arrow"
            step="any"
            min="1"
            placeholder="Informe o valor do veículo..."
            pattern="^\d+(\.|,)?\d{2}$"
            value="${vehicle.value}"
            required
        />
    </div>
</div>
