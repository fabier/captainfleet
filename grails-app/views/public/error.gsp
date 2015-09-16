<!DOCTYPE html>
<html>
<head>
    <title>CaptainFleet - Erreur</title>
    <meta name="layout" content="main">
    <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container">
    <div class="row">
        <div class="col-md-12">
            <g:renderException exception="${exception}"/>
        </div>
    </div>
</div>
</body>
</html>
