<!DOCTYPE html>
<html>
<head>
    <title>CaptainFleet - Erreur</title>
    <meta name="layout" content="main">
    <g:if env="development"><asset:stylesheet src="errors.css"/></g:if>
</head>

<body>
<div class="container">
    <g:render template="/templates/flashMessage"/>
    <div class="row">
        <div class="col-md-12">
            <g:if env="development">
                <g:renderException exception="${exception}"/>
            </g:if>
            <g:else>
                <ul class="errors">
                    <li>An error has occurred</li>
                </ul>
            </g:else>
        </div>
    </div>
</div>
</body>
</html>
