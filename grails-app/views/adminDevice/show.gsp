<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin">
    <title>CaptainFleet - Boitier</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-md-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-md-10">
            <g:form action="update" id="${device.id}" class="form-horizontal">

                <div class="form-group">
                    <label for="sigfoxId" class="col-md-2 control-label">SigFoxId</label>

                    <div class="col-md-10">
                        <g:field type="text" name="sigfoxId" value="${device.sigfoxId}" class="form-control"
                                 disabled="disabled"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="name" class="col-md-2 control-label">Nom</label>

                    <div class="col-md-10">
                        <g:field type="text" name="name" value="${device.name}" class="form-control"/>
                    </div>
                </div>

                <div class="form-group">
                    <label for="code" class="col-md-2 control-label">Code</label>

                    <div class="col-md-10">
                        <g:field type="text" name="code" value="${device.code}" class="form-control"
                                 disabled="disabled"/>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-md-10 col-md-offset-2">
                        <button type="submit" class="btn btn-primary">
                            Enregistrer
                        </button>
                        <g:if test="${device.code == null}">
                            <g:link action="randomCode" id="${device.id}" class="btn btn-danger">
                                Générer un nouveau code aléatoire
                            </g:link>
                        </g:if>
                    </div>
                </div>
            </g:form>

            <table class="table table-hover small nomargin-left-right">
                <thead>
                <th>#</th>
                <th>Data</th>
                <th>Date</th>
                <th>Signal</th>
                <th>RSSI</th>
                </thead>
                <tbody>
                <g:each in="${results}" var="frame">
                    <tr class="clickable-row"
                        data-href="${createLink(controller: "adminFrame", action: "show", id: frame.id)}">
                        <td>${frame.id}</td>
                        <td>
                            <code>0x${frame.data.toUpperCase()}</code>
                        </td>
                        <td>
                            <g:formatDate format="yyyy-MM-dd HH:mm" date="${frame.dateCreated}"/>
                        </td>
                        <td>
                            ${frame.signal} dB
                        </td>
                        <td>
                            ${frame.rssi} dBm
                        </td>
                    </tr>
                </g:each>
                </tbody>
            </table>

            <g:if test="${totalCount > results.size()}">
                <div class="text-center">
                    <g:paginate next="&gt;" prev="&lt;" maxsteps="5" action="show" id="${device.id}"
                                total="${totalCount}"/>
                </div>
            </g:if>
        </div>
    </div>
</div>
</body>
</html>