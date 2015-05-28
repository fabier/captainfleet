<head>
    <meta name='layout' content='main'/>
    <title>CaptainFleet - Accès refusé</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container">
    <div class="row">
        <div class="col-md-8 center-block">
            <div class="alert alert-danger">
                <div class='errors'>
                    <g:message code="springSecurity.denied.message"/>
                </div>
            </div>
        </div>

        <div class="col-md-2"></div>
    </div>
</div>
</body>
