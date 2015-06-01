<g:if test="${flash?.error}">
    <g:render template="/templates/flash/error"/>
</g:if>
<g:elseif test="${flash?.warning}">
    <g:render template="/templates/flash/warning"/>
</g:elseif>
<g:elseif test="${flash?.success}">
    <g:render template="/templates/flash/success"/>
</g:elseif>
<g:elseif test="${flash?.message}">
    <g:render template="/templates/flash/message"/>
</g:elseif>