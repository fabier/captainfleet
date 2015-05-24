<footer class="footer">
    <p>
    &copy;&nbsp;
    CaptainFleet
    &nbsp;-&nbsp;
    2015
    &nbsp;-&nbsp;
    <g:link controller="public" action="credits" class="white nohover">
        Credits
    </g:link>
    &nbsp;-&nbsp;
    <g:link controller="public" action="about" class="white nohover">
        A propos
    </g:link>
    &nbsp;-&nbsp;
    <g:link controller="public" action="contact" class="white nohover">
        Contact
    </g:link>
    &nbsp;-&nbsp;
    v<g:meta name="app.version"/>
    &nbsp;-&nbsp;
    <g:link url="http://grails.org" class="white nohover">
        Built with Grails <g:meta name="app.grails.version"/>
    </g:link>
    <sec:ifAllGranted roles="ROLE_ADMIN">
        &nbsp;-&nbsp;
        <g:link controller="admin" class="white nohover">
            Administration
        </g:link>
    </sec:ifAllGranted>
    </p>
</footer>