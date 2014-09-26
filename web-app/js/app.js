(function() {

    var appName = 'Retrograde';
    var animationPlayed = false;

    // --------------

    var app = angular.module('retrograde',
        [
            'ui.bootstrap',
            'angularjs-gravatardirective',
            'last-retro',
            'team-panel',
            'feedback',
            'sprint-meter',
            'moods',
            'user',
            'rewards',
            'account-dropdown',
            'word-cloud',
            'retro-list',
            'ngRoute',
            'new-retro',
            'userProfile'
        ]);
    app.constant('root', '/retrograde');

    app.directive('lastRetro', function() { return { restrict: 'E', templateUrl: 'views/last-retro.html' }; });
    app.directive('teamPanel', function() { return { restrict: 'E', templateUrl: 'views/team-panel.html' }; });
    app.directive('sprintMeter', function() { return { restrict: 'E', templateUrl: 'views/retro-meter.html' }; });
    app.directive('moods', function() { return { restrict: 'E', templateUrl: 'views/moods.html' }; });
    app.directive('user', function() { return { restrict: 'E', templateUrl: 'views/user.html' }; });
    app.directive('rewards', function() { return { restrict: 'E', templateUrl: 'views/rewards.html' }; });
    app.directive('accountDropdown', function() { return { restrict: 'E', templateUrl: 'views/account-drop-down.html' }; });
    app.directive('wordCloud', function() { return { restrict: 'E', templateUrl: 'views/word-cloud.html' }; });
    app.directive('retroList', function() { return { restrict: 'E', templateUrl: 'views/retro-list.html' }; });
    app.directive('retro', function() { return { restrict: 'E', templateUrl: 'views/retro.html' }; });
    app.directive('userProfile', function() { return { restrict: 'E', templateUrl: 'views/user-profile.html' }; });


    app.directive('draggable', function() {
        return function(scope, element) {
            // this gives us the native JS object
            var el = element[0];

            el.draggable = true;

            el.addEventListener(
                'dragstart',
                function(e) {
                    e.dataTransfer.effectAllowed = 'move';
                    e.dataTransfer.setData('Text', this.id);
                    this.classList.add('drag');
                    return false;
                },
                false
            );

            el.addEventListener(
                'dragend',
                function(e) {
                    this.classList.remove('drag');
                    return false;
                },
                false
            );
        }
    });
    app.directive('droppable', function() {
        return {
            scope: {
                drop: '&' // parent
            },
            link: function(scope, element) {
                // again we need the native object
                var el = element[0];
                el.addEventListener(
                    'dragover',
                    function(e) {
                        e.dataTransfer.dropEffect = 'move';
                        // allows us to drop
                        if (e.preventDefault) e.preventDefault();
                        this.classList.add('over');
                        return false;
                    },
                    false
                );
                el.addEventListener(
                    'dragenter',
                    function(e) {
                        this.classList.add('over');
                        return false;
                    },
                    false
                );
                el.addEventListener(
                    'dragleave',
                    function(e) {
                        this.classList.remove('over');
                        return false;
                    },
                    false
                );
                el.addEventListener(
                    'drop',
                    function(e) {
                        // Stops some browsers from redirecting.
                        if (e.stopPropagation) e.stopPropagation();

                        this.classList.remove('over');
                        console.log(e);
                        var item = document.getElementById(e.dataTransfer.getData('Text'));
                        var droppedItem = this;
                        // call the drop passed drop function
                        scope.$apply(function(scope) {
                            var fn = scope.drop();
                            if ('undefined' !== typeof fn) {
                                fn(item, droppedItem.id);
                            }
                        });

                        return false;
                    },
                    false
                );
            }
        }
    });

    app.controller('IndexController', function($scope,$route, $routeParams, $location, retroService, notificationService) {
        $scope.appName = appName;
        $scope.$location = $location;
        $scope.$routeParams = $routeParams;
        $scope.retroStatus = false;

        $scope.go = function (path) {
            animationPlayed = false;
            $location.path(path);
        };
        $scope.animateTiles = function(){
            if(!animationPlayed){
                var sections = $('.section-wrapper');
                console.log(sections.length);
                for (var i = 0; i < sections.length; i++) {
                    var section = $(sections[i]);
                    section.css({
                        'margin-left': '1000px',
                        'margin-top': '1000px',
                        opacity: 0 //,
                        //transform: 'rotateY(80deg)',
                        //transform: 'rotateZ(20deg)',
                        //'-webkit-filter': 'blur(5px)'
                    });
                }
                animateTiles(0);
                function animateTiles(i) {
                    $(sections[i]).animate({
                        'margin-left': '0px',
                        'margin-top': '0px',
                        opacity: 1
                    }, 310, "swing", function () {
                        $(sections[i]).css({
                            //transform: 'rotateY(0)',
                            //'-webkit-filter': 'blur(0)'
                        });
                        animateTiles(i + 1);
                    });
                }
                animationPlayed = true;
            }
        };
        notificationService.listen("finishretro", function(message) {
            $scope.$apply(function() {
                $scope.retroStatus = true;
            });
        });

        if ($routeParams.retroId) {
            retroService.getStatus($routeParams.retroId).then(function(retroStatus) {
                $scope.retroStatus = retroStatus && retroStatus.status && retroStatus.status.name === "COMPLETE";
            });
        }
    });

    app.controller('MainController', function($scope, $route, $routeParams, $location, $injector, $window, retroService, userService) {
        $scope.$route = $route;
        $scope.$location = $location;
        $scope.$routeParams = $routeParams;

        $scope.go = function (path) {
            animationPlayed = false;
            $location.path(path);
        };

        $scope.finishRetro = function() {
            retroService.getModerator($routeParams.retroId).then(function(moderator) {
                userService.getUserContext().then(function(userContext) {
                    if (moderator && userContext && moderator.id === userContext.id) {
                        retroService.finishRetro($routeParams.retroId).then(function() {
                            //$window.location = '#/Retros/';
                        });
                    } else {
                        alert('Sorry, you have to be a moderator!');
                    }
                })
            });
        };

        $scope.exportRetro = function() {
            var win = window.open('/retrograde/api/retro/' + $routeParams.retroId + '/export', '_blank');
            win.focus();
        };

        $scope.$on('$routeChangeStart', function(next, current) {
            if (current.params.retroId) {
                retroService.getName(current.params.retroId).then(function(name) {
                    $scope.currentSprint = name;
                });
            } else {
                $scope.currentSprint = "";
            }
        });
    })
    .config(function($routeProvider) {
        $routeProvider
            .when('/', {
                templateUrl: 'views/retro-list.html',
                controller: 'IndexController'
            })
            .when('/Retros/', {
                templateUrl: 'views/retro-list.html',
                controller: 'IndexController'
            })
            .when('/Retro/:retroId/:teamId', {
                templateUrl: 'views/retro.html',
                controller: 'IndexController'
            })
            .when('/Profile/', {
                templateUrl: 'views/user-profile.html',
                controller: 'UserProfileController'
            });
    });
})();



