<div class="mb-2">
	<input name="idVehicle" id="Id" hidden />
    <div class="form-group">
        <label for="Model">Modelo</label>
        <input name="txtVehicleModel" id="Model" class="form-control" required />
    </div>

    <div class="form-group">
        <label for="Year">Ano</label>
        <input name="nbrVehicleYear" id="Year" class="form-control" min="1920" max="9999" />
    </div>

    <div class="form-group">
        <label for="Manufacturer">Fabricante</label>
        <select
        	name="slcVehicleManufacturer"
            id="Manufacturer"
            class="form-select"
            required
        >
            <option selected hidden>Selecione a fabricante</option>
        </select>
    </div>

    <div class="form-group">
        <label for="VehicleType">Classe</label>
        <select
        	name="slcVehicleType"
            id="VehicleType"
            class="form-select"
            required
        >
            <option selected hidden>Selecione a classe do veículo</option>
        </select>
    </div>

    <div class="form-group">
        <label for="Value">Valor</label>
        <input name="vlrVehicleValue" id="Value" class="form-control" step="any" min="1" required />
    </div>
</div>