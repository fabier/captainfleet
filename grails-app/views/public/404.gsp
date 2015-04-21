<!DOCTYPE html>
<html>
<head>
    <title>Erreur 404</title>
    <meta name="layout" content="main">
    <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
</head>

<body>
<div class="container">
    <g:render template="/templates/flashMessage"/>
    <div class="row">
        <div class="col-sm-12">
            <div class="alert alert-warning">
                Cette page n'existe pas.
            </div>
        </div>
    </div>
</div>
</body>
</html>
