<%@ page import="com.amplify.agile.Sprint" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'sprint.label', default: 'Sprint')}"/>
    <title><g:message code="default.show.label" args="[entityName]"/></title>
</head>

<body>
<a href="#show-sprint" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><g:link class="list" action="index"><g:message code="default.list.label" args="[entityName]"/></g:link></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="show-sprint" class="content scaffold-show" role="main">
    <h1><g:message code="default.show.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <ol class="property-list sprint">

        <g:if test="${sprintInstance?.name}">
            <li class="fieldcontain">
                <span id="name-label" class="property-label"><g:message code="sprint.name.label" default="Name"/></span>

                <span class="property-value" aria-labelledby="name-label"><g:fieldValue bean="${sprintInstance}"
                                                                                        field="name"/></span>

            </li>
        </g:if>

        <g:if test="${sprintInstance?.startDate}">
            <li class="fieldcontain">
                <span id="startDate-label" class="property-label"><g:message code="sprint.startDate.label"
                                                                             default="Start Date"/></span>

                <span class="property-value" aria-labelledby="startDate-label"><g:formatDate format="M-d-yyyy"
                        date="${sprintInstance?.startDate}"/></span>

            </li>
        </g:if>

        <g:if test="${sprintInstance?.endDate}">
            <li class="fieldcontain">
                <span id="endDate-label" class="property-label"><g:message code="sprint.endDate.label"
                                                                           default="End Date"/></span>

                <span class="property-value" aria-labelledby="endDate-label"><g:formatDate format="M-d-yyyy"
                                                                                           date="${sprintInstance?.endDate}"/></span>

            </li>
        </g:if>

    </ol>
    <g:form url="[resource: sprintInstance, action: 'delete']" method="DELETE">
        <fieldset class="buttons">
            <g:link class="edit" action="edit" resource="${sprintInstance}"><g:message code="default.button.edit.label"
                                                                                       default="Edit"/></g:link>
            <g:actionSubmit class="delete" action="delete"
                            value="${message(code: 'default.button.delete.label', default: 'Delete')}"
                            onclick="return confirm('${message(code: 'default.button.delete.confirm.message', default: 'Are you sure?')}');"/>
        </fieldset>
    </g:form>
</div>
</body>
</html>
