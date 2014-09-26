<%@ page import="com.amplify.agile.Sprint" %>
<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main">
    <g:set var="entityName" value="${message(code: 'sprint.label', default: 'Sprint')}"/>
    <title><g:message code="default.list.label" args="[entityName]"/></title>
</head>

<body>
<a href="#list-sprint" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                             default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
       <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-sprint" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <g:sortableColumn property="name" title="${message(code: 'sprint.name.label', default: 'Name')}"/>

            <g:sortableColumn property="startDate"
                              title="${message(code: 'sprint.startDate.label', default: 'Start Date')}"
            style="text-align: right;"/>
            <g:sortableColumn property="endDate" title="${message(code: 'sprint.endDate.label', default: 'End Date')}"
                              style="text-align: right;"/>

        </tr>
        </thead>
        <tbody>
        <g:each in="${sprintInstanceList}" status="i" var="sprintInstance">
            <tr class="${(i % 2) == 0 ? 'even' : 'odd'}">
                <td><g:link action="show"
                            id="${sprintInstance.id}">${fieldValue(bean: sprintInstance, field: "name")}</g:link></td>

                <td style="text-align: right;"><g:formatDate format="M-d-yyyy" date="${sprintInstance.startDate}"/></td>
                <td style="text-align: right;"><g:formatDate format="M-d-yyyy" date="${sprintInstance.endDate}"/></td>



            </tr>
        </g:each>
        </tbody>
    </table>

    <div class="pagination">
        <g:paginate total="${sprintInstanceCount ?: 0}"/>
    </div>
</div>
</body>
</html>
