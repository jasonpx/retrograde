<%@ page import="com.amplify.agile.Sprint" %>

<div class="fieldcontain ${hasErrors(bean: sprintInstance, field: 'name', 'error')} required">
    <label for="name">
        <g:message code="sprint.name.label" default="Name"/>
        <span class="required-indicator">*</span>
    </label>
    <g:textField name="name" required="" value="${sprintInstance?.name}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: sprintInstance, field: 'startDate', 'error')} required">
    <label for="startDate">
        <g:message code="sprint.startDate.label" default="Start Date"/>
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="startDate" precision="day" value="${sprintInstance?.startDate}"/>

</div>

<div class="fieldcontain ${hasErrors(bean: sprintInstance, field: 'endDate', 'error')} required">
    <label for="endDate">
        <g:message code="sprint.endDate.label" default="End Date"/>
        <span class="required-indicator">*</span>
    </label>
    <g:datePicker name="endDate" precision="day" value="${sprintInstance?.endDate}"/>

</div>
