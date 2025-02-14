<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <title><g:layoutTitle default="CaptainFleet"/></title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <link rel="apple-touch-icon" sizes="57x57" href="${assetPath(src: 'favicons/apple-icon-57x57.png')}">
    <link rel="apple-touch-icon" sizes="60x60" href="${assetPath(src: 'favicons/apple-icon-60x60.png')}">
    <link rel="apple-touch-icon" sizes="72x72" href="${assetPath(src: 'favicons/apple-icon-72x72.png')}">
    <link rel="apple-touch-icon" sizes="76x76" href="${assetPath(src: 'favicons/apple-icon-76x76.png')}">
    <link rel="apple-touch-icon" sizes="114x114" href="${assetPath(src: 'favicons/apple-icon-114x114.png')}">
    <link rel="apple-touch-icon" sizes="120x120" href="${assetPath(src: 'favicons/apple-icon-120x120.png')}">
    <link rel="apple-touch-icon" sizes="144x144" href="${assetPath(src: 'favicons/apple-icon-144x144.png')}">
    <link rel="apple-touch-icon" sizes="152x152" href="${assetPath(src: 'favicons/apple-icon-152x152.png')}">
    <link rel="apple-touch-icon" sizes="180x180" href="${assetPath(src: 'favicons/apple-icon-180x180.png')}">
    <link rel="icon" type="image/png" sizes="192x192" href="${assetPath(src: 'favicons/android-icon-192x192.png')}">
    <link rel="icon" type="image/png" sizes="32x32" href="${assetPath(src: 'favicons/favicon-32x32.png')}">
    <link rel="icon" type="image/png" sizes="96x96" href="${assetPath(src: 'favicons/favicon-96x96.png')}">
    <link rel="icon" type="image/png" sizes="16x16" href="${assetPath(src: 'favicons/favicon-16x16.png')}">
    <link rel="manifest" href="${assetPath(src: 'favicons/manifest.json')}">
    <meta name="msapplication-TileColor" content="#ffffff">
    <meta name="msapplication-TileImage" content="/ms-icon-144x144.png">
    <meta name="theme-color" content="#ffffff">

    <asset:stylesheet src="application"/>
    <asset:javascript src="application"/>
    <g:layoutHead/>
    <ga:trackPageview/>
</head>

<body>

<g:render template="/templates/header"/>

<div class="wrapper">
    <g:layoutBody/>
</div>

<g:render template="/templates/footer"/>

<asset:deferredScripts/>
</body>
</html>