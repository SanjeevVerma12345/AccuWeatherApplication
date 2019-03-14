<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <title>Entrego Weather Application</title>
    <link href="http://cdn.jsdelivr.net/webjars/bootstrap/3.3.4/css/bootstrap.min.css"
          th:href="@{/webjars/bootstrap/3.3.4/css/bootstrap.min.css}"
          rel="stylesheet" media="screen"/>
</head>
<body>

<form class="form-horizontal" name="weatherForm" action="/getLocationAndWeather" method="POST" modelAttribute="weatherForm">
    <fieldset>

        <!-- Form Name -->
        <legend>Entrego Weather Application</legend>

        <!-- Text input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="postalCode">Postal Code</label>
            <div class="col-md-4">
                <input id="postalCode" name="postalCode" type="text"
                       placeholder="Postal Code" class="form-control input-md" required="" maxlength="5" minlength="5">

            </div>
        </div>

        <!-- Button -->
        <div class="form-group">
            <input type="submit" id="submitButton" name="submitButton" class="btn btn-primary center-block">
        </div>

    </fieldset>
</form>
<#if weatherError ??>
    <span class="label label-danger center-block">${weatherError}</span>
</#if>
<#if weather??>
    <table >
    <div class="form-group center-block">
        <div class="row">
        <label class="col-md-4 control-label" for="stateName">State Name</label>
        <div class="col-md-4">
            ${postalCodeSearch.countryModel.localizedName}
        </div>
        </div>
        <div class="row">
            <label class="col-md-4 control-label" for="City Name">City Name</label>
            <div class="col-md-4">
                ${postalCodeSearch.localizedName}
            </div>
        </div>
        <div class="row">
            <label class="col-md-4 control-label" for="weather">Weather</label>
            <div class="col-md-4">
                ${weather.temperatureModel.imperialTemperatureModel.value} ${weather.temperatureModel.imperialTemperatureModel.unit}
            </div>
        </div>
    </div>
    </table>
</#if>
</body>
</html>