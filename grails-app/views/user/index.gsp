
<%@ page import="com.amplify.agile.User" %>
<!DOCTYPE html>
<html>
	<head>
		<meta name="layout" content="main">
		<g:set var="entityName" value="${message(code: 'user.label', default: 'User')}" />
		<title><g:message code="default.list.label" args="[entityName]" /></title>
	</head>
	<body>
		<a href="#list-user" class="skip" tabindex="-1"><g:message code="default.link.skip.label" default="Skip to content&hellip;"/></a>
		<div class="nav" role="navigation">
			<ul>
				<li><g:link class="create" action="create"><g:message code="default.new.label" args="[entityName]" /></g:link></li>
			</ul>
		</div>
		<div id="list-user" class="content scaffold-list" role="main">
			<h1><g:message code="default.list.label" args="[entityName]" /></h1>
			<g:if test="${flash.message}">
				<div class="message" role="status">${flash.message}</div>
			</g:if>
			<table>
			<thead>
					<tr>
                        <g:sortableColumn property="username" title="${message(code: 'user.username', default: 'Username')}" />

                        <g:sortableColumn property="first" title="${message(code: 'user.first.label', default: 'First')}" />

                        <g:sortableColumn property="last" title="${message(code: 'user.last.label', default: 'Last')}" />

                        <g:sortableColumn property="enabled" title="${message(code: 'user.enabled.label', default: 'Enabled')}" />

                        <g:sortableColumn property="accountLocked" title="${message(code: 'user.accountLocked.label', default: 'Account Locked')}" />

                        <g:sortableColumn property="accountExpired" title="${message(code: 'user.accountExpired.label', default: 'Account Expired')}" />

					</tr>
				</thead>
				<tbody>
				<g:each in="${userInstanceList}" status="i" var="userInstance">
					<tr class="${(i % 2) == 0 ? 'even' : 'odd'}">

                        <td>
                            <g:link action="show" id="${userInstance.id}">
                                ${fieldValue(bean: userInstance, field: "username")}
                            </g:link>
                        </td>

                        <td>${fieldValue(bean: userInstance, field: "first")}</td>

                        <td>${fieldValue(bean: userInstance, field: "last")}</td>

                        <td><g:formatBoolean boolean="${userInstance.enabled}" /></td>
					
						<td><g:formatBoolean boolean="${userInstance.accountLocked}" /></td>

                        <td>${fieldValue(bean: userInstance, field: "accountExpired")}</td>
					
					</tr>
				</g:each>
				</tbody>
			</table>
			<div class="pagination">
				<g:paginate total="${userInstanceCount ?: 0}" />
			</div>
		</div>
	</body>
</html>
