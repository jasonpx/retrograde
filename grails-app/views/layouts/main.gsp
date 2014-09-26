<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en" dir="ltr">
<head>
    <link rel="stylesheet" type="text/css" href="${resource(dir:'css',file:'main.css')}" />
    <g:layoutHead />
    <g:javascript library="application" />
    <g:set var="onLoadContent" value="${((params.action=='edit'||params.action == 'create')?'setFocusOnFirstControl()':'')}" />
</head>
<body onload="${onLoadContent}">

<div id="spinner" class="spinner" style="display:none;">
    <img src="${resource(dir:'images',file:'spinner.gif')}" alt="${message(code:'spinner.alt',default:'Loading...')}" />
</div>

<g:layoutBody />

</body>
</html>