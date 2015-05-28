<!DOCTYPE html>
<html lang="en">
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Alertes</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAccount"/>
        </div>

        <div class="col-md-8">
            <legend>
                <i class="glyphicon glyphicon-bell"></i>
                &nbsp;Fiche alerte
            </legend>

            <div class="row">
                <table class="table table-hover small nomargin-left-right">
                    <thead>
                    <tr>
                        <th class="col-md-1">#</th>
                        <th class="col-md-2">Date</th>
                        <th class="col-md-2">Etat</th>
                    </tr>
                    </thead>
                    <tbody>
                    <g:each in="${deviceAlertLogs}" var="deviceAlertLog">
                        <tr class="clickable-row" data-href="#">
                            <td>${deviceAlertLog.id}</td>
                            <td>
                                <g:formatDate format="yyyy-MM-dd HH:mm" date="${deviceAlertLog.dateCreated}"/>
                            </td>
                            <td>
                                <g:formatBoolean boolean="${deviceAlertLog.isRaised}"/>
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="col-md-2">
            <g:render template="/alert/actions"/>
        </div>
    </div>
</div>
</body>
</html>
