<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin">
    <title>CaptainFleet - Trame</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container-fluid margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <g:render template="/templates/breadcrumb">
                <g:render template="/templates/breadcrumb/device"/>
                <li>
                    <g:link controller="adminDevice" action="show" id="${device.id}">
                        Device ${device.sigfoxId}
                    </g:link>
                </li>
                <li class="active">Frame ${frame.id}</li>
            </g:render>

            <g:render template="/frame/tabs" model="[frames: frames, frame: frame]"/>
        </div>
    </div>
</div>
</body>
</html>