<%@ page import="com.amplify.agile.Team" %>



<div class="fieldcontain ${hasErrors(bean: teamInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="team.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${teamInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: teamInstance, field: 'users', 'error')} ">
    <label for="users">
        <g:message code="team.users.label" default="Users"/>

    </label>
    <g:textField name="oldUsers" value="${teamInstance?.users*.id}"></g:textField>
    <g:select name="users"
              from="${com.amplify.agile.User.list()}"
              multiple="multiple"
              optionKey="id"
              optionValue="username"
              size="5"
              value="${teamInstance?.users*.id}" class="many-to-many">
        </g:select>

</div>

