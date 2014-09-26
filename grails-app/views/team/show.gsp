<%@ page import="com.amplify.agile.Team" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'team.label', default: 'Team')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-team" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                           default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-team" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list team">

        <g:if test="${teamInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label">
                    <g:message code="team.name.label" default="Name"/>
                </span>
                <span class="property-value" aria-labelledby="name-label">
                    <g:fieldValue bean="${teamInstance}" field="name"/>
                </span>
            </li>
        </g:if>

        <g:if test="${teamInstance?.users}">
            <li class="fieldcontain">
                <span id="users-label" class="property-label">
                    <g:message code="team.users.label" default="Users"/>
                </span>
                <g:each in="${teamInstance.users}" var="u">
                    <span class="property-value" aria-labelledby="users-label">
                        <g:link controller="user" action="show" id="${u.id}">${u?.username}</g:link>
                    </span>
                </g:each>
            </li>
        </g:if>

    </ol>
    <g:form url="[resource: teamInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${teamInstance}"><g:message code="default.button.edit.label"
                                                                                     default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
