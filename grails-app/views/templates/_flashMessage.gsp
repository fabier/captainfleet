<g:if test="${flash?.message}">
    <g:render template="/templates/flash/message"/>
</g:if>
<g:if test="${flash?.warning}">
    <g:render template="/templates/flash/warning"/>
</g:if>
<g:if test="${flash?.error}">
    <g:render template="/templates/flash/error"/>
</g:if>