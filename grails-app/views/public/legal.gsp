<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <meta name="layout" content="main"/>
    <title>CaptainFleet - Mentions légales</title>
</head>

<body>

<g:render template="/templates/flashMessage"/>

<div class="container">
    <div class="row">
        <div class="col-md-8 center-block">

            <div class="alert alert-info">
                <p class="text-justify">
                    <span class="text-large">Mentions légales</span>
                </p>
            </div>

            <p class="text-justify">
                <span class="captain-fleet">CaptainFleet.com</span> est un site internet édité par
                <span class="captain-fleet">CaptainFleet</span>, société en cours de formation, domiciliée
            37 rue Corneille, 31100 Toulouse - France. Le responsable de la publication est Pierre Fabier.
            </p>

            <p class="text-justify">
                L’hébergeur du site internet est 1&1, SARL au capital de 100,000 €,
                domiciliée 7 place de la Gare, BP70109, 57201 Sarreguemines Cedex.
            </p>

            <p class="text-justify">
                <span class="captain-fleet">CaptainFleet</span>
                offre à ses visiteurs, sur son site internet accessible à l’adresse
                <a href="http://www.captainfleet.com">http://www.captainfleet.com</a>,
            l’accès aux informations liées à son offre commerciale ainsi qu’à un espace réservé aux clients de ses solutions.
            </p>

            <p class="text-justify">
                Nous vous invitons à consulter les
                <g:link controller="public" action="cgu">conditions générales d’utilisation (CGU)</g:link>
                et les
                <g:link controller="public" action="cgv">conditions générales de vente (CGV)</g:link>
                des services et solutions
                <span class="captain-fleet">CaptainFleet</span>, destinés aux professionnels.
            </p>

            <p class="text-justify">
                En application de la loi 78-17 du 6 janvier 1978,
                il est rappelé que les données nominatives qui sont demandés à l'utilisateur des services du site internet sont nécessaires au fonctionnement
                et à la fourniture de services de la part de
                <span class="captain-fleet">CaptainFleet</span>.
            Le numéro de déclaration simplifiée CNIL est en cours d’obtention.
            </p>

            <p class="text-justify">
                En application de la loi 78-17 du 6 janvier 1978, vous disposez,
                conformément aux règlementations nationales et européennes en vigueur,
                d'un droit d'accès permanent, de modification, de rectification
                et d'opposition s'agissant des informations le concernant.
            </p>

            <p class="text-justify">
                Pour toute demande ou requête, merci d'utiliser le
                <g:link controller="public" action="contact">formulaire de contact</g:link>.
                %{--ou nous contacter par mail à--}%
                %{--<i class="glyphicon glyphicon-envelope"></i>contact(-a-)captainfleet(point)com--}%
                %{--ou par téléphone au +33(0)6.17.XX.XX.19.--}%
            </p>
        </div>
    </div>
</div>
</body>
</html>