<footer class="footer text-small">
    <p>
    &copy;&nbsp;
    CaptainFleet
    &nbsp;-&nbsp;
    2015
    &nbsp;-&nbsp;
    v.<g:meta name="app.version"/>
        <br/>
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