<footer class="footer text-small">
    <p>
    &copy;&nbsp;
    CaptainFleet&nbsp;<g:meta name="app.version"/>
    &nbsp;-&nbsp;
    2015
        <br/>
        <g:link controller="public" action="about" class="white nohover">
            A propos
        </g:link>
        &nbsp;-&nbsp;
        <g:link controller="public" action="legal" class="white nohover">
            Mentions légales
        </g:link>
        &nbsp;-&nbsp;
        <g:link controller="public" action="cgu" class="white nohover">
            CGU
        </g:link>
        &nbsp;-&nbsp;
        <g:link controller="public" action="cgv" class="white nohover">
            CGV
        </g:link>
        &nbsp;-&nbsp;
        <g:link controller="public" action="contact" class="white nohover">
            Contact
        </g:link>
        &nbsp;-&nbsp;
        <g:link controller="public" action="credits" class="white nohover">
            Credits
        </g:link>
        <sec:ifAllGranted roles="ROLE_ADMIN">
            &nbsp;-&nbsp;
            <g:link controller="admin" class="white nohover">
                Administration
            </g:link>
        </sec:ifAllGranted>
    </p>
</footer>