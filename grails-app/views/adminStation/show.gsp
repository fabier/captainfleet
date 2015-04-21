<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="admin">
    <title>Station</title>
</head>

<body>

<div class="container margin-top-20">
    <div class="row">
        <div class="col-sm-2">
            <g:render template="/templates/lateralMenuAdmin"/>
        </div>

        <div class="col-sm-10">
            <g:render template="/templates/flashMessage"/>

            <g:render template="/templates/breadcrumb">
                <g:render template="/templates/breadcrumb/station"/>
                <li class="active">#${station.id} : ${station.sigfoxId}</li>
            </g:render>

            <table class="table table-hover small nomargin">
                <thead>
                <th>#</th>
                <th>Name</th>
                <th>Date</th>
                <th>Signal</th>
                <th>RSSI</th>
                </thead>
                <tbody>
                <g:each in="${frames}" var="frame">
                    <tr>
                        <td>${station.id}</td>
                        <td>
                            ${station.name}
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
        </div>
    </div>

    <div class="row">
        <div class="col-sm-6 center-block">
            <nav class="text-center">
                <g:paginate next="&gt;" prev="&lt;" maxsteps="5" controller="adminDevice" id="${device.id}" action="show"
                            total="${totalCount}"/>
            </nav>
        </div>
    </div>
</div>
</body>
</html>