<!DOCTYPE html>
<html ng-app="store">
<head>
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'js', file: '/bootstrap/css/bootstrap.min.css')}"/>
    <link rel="stylesheet" type="text/css"
          href="${resource(dir: 'js', file: '/bootstrap/css/bootstrap-theme.min.css')}">
    <link rel="stylesheet" type="text/css"
          href="${resource(dir: 'js', file: '/font-awesome/css/font-awesome.min.css')}"/>
    <link rel="stylesheet" type="text/css" href="http://fonts.googleapis.com/css?family=Lobster">
    <link rel="stylesheet" type="text/css" href="${resource(dir: 'js', file: '../css/application.css')}">
    <script type="text/javascript" src="${resource(dir: 'js/jquery', file: 'jquery-1.11.1.min.js')}"></script>
    <script type="text/javascript" src="${resource(dir: 'js', file: '/bootstrap/js/bootstrap.min.js')}"></script>
    <title>Retrograde</title>
</head>

<body>

<form action='${postUrl}' method='POST' id='loginForm' class='cssform' autocomplete='off' style="margin-bottom: 0px;">
    <div id="topHeader">
        <div id="logo" onclick="javascript:location.href = '/retrograde/';">Retrograde</div>

        <div class="navItem" style="min-width: 430px;">
            <g:if test="${params.controller != 'promo'}">

                <div class="pull-left" style="text-align: left;">
                    <label for='username'><g:message code="springSecurity.login.username.label"/>:</label><br>
                    <input type='text' class='text_ form-control' style="width: 130%;" name='j_username' id='username'/>
                </div>

                <div class="pull-left">
                    &nbsp;&nbsp;&nbsp;
                </div>

                <div class="pull-left">
                    <label for='password'><g:message code="springSecurity.login.password.label"/>:</label><br>
                    <input type='password' class='text_ form-control' style="width: 70%; float: right;"
                           name='j_password'
                           id='password'/>
                </div>

                <div class="pull-left">
                    &nbsp;&nbsp;&nbsp;
                </div>

                <div class="pull-left" style="margin-top: 25px;">
                    <input type='submit' id="submit" class="btn btn-default"
                           value='${message(code: "springSecurity.login.button")}'/>
                </div>

                <div class="clearfix">
                    <g:if test='${flash.message}'>
                        <div class='login_message'>${flash.message}</div>
                    </g:if>
                </div>

            </g:if>
            <g:else>
                <div class="pull-right" style="margin-top: 20px;">
                    <input type='button' class="btn btn-default"
                           value='Sign In'
                           onclick="javascript:location.href = '${createLink(controller: 'login', view: 'auth')}';"/>
                </div>
            </g:else>
        </div>
    </div>
</form>


<div class="heroImage" style="background-image: url('${resource(dir: 'images', file: 'promo_office.jpg')}');">
    <div id="caption">
        <p class="title">Retrospectives</p>

        <p class="tagLine">Exposing the things you wish you knew 3 weeks ago.</p>
    </div>
</div>

<div class="intro">
    <div class="introContainer container-fluid">
        <div class="row">
            <div class="col-xs-6">
                <div class="text">
                    <h1>An interactive team retro</h1>

                    <h2>that is fun, engaging and informative.</h2>

                    <p>There's so much to do that it's not unusual for a team to finish a project and immediately start on a new
                    one, leaving little time to reflect and improve.</p>

                    <p></p>
                </div>
            </div>
            <div class="col-xs-6">
                <div class="image">
                    <img src="${resource(dir: 'images', file: 'screenshot_retro1.png')}" width="100%"/>
                </div>
            </div>


        </div>

    </div>
</div>

<div class="testimonies" style="text-align: center;">
    <div style="margin-top: 20px; margin-bottom: 75px; color: #707070; font-size: 0.9em; letter-spacing: 2px; text-align: center; text-transform: uppercase;">
        Created during Amplify hack sprint Sept 2014
    </div>

    <div style="width: 500px; margin-bottom: 35px;" class="center">
        <div style="color: orange; font-size: 1.9em;">
            "You can't wait for inspiration,<br> you have to go after it with a club"
        </div>
    </div>

    <div style="margin-bottom: 35px;">
        <cite>- Jack London, American Author</cite>
    </div>
</div>

<div class="customers">
    <div style="width: 500px; text-align: center;" class="center">
        Created By:<br>

        <div style="color: #bbbbbb; font-variant: small-caps;">
            jason priest, brian bull, frank benoit, emily friese, and jonathan berry
        </div>
    </div>
</div>