(function () {
    var app = angular.module('sprint-meter', []);

    app.controller('SprintMeterController', ['$http', '$scope', '$routeParams', 'notificationService', 'root', function ($http, $scope, $routeParams, notificationService, root) {
        var controller = this;
        controller.score = -1;
        controller.user = -1;
        var curSprintId = null;

        this.getData = function () {
            if ($routeParams.retroId) {
                // Get meter data for retro
                $http.get(root + '/api/meter/retro/' + $routeParams.retroId).success(function (data) {
                    controller.score = Math.round(data.score);
                    if (!data.user) {
                        data.user = 50;
                    }
                    controller.user = Math.round(data.user);
                });
            } else if (curSprintId) {
                // Get meter data for sprint (across teams)
                $http.get(root + '/api/meter/sprint/' + curSprintId).success(function (data) {
                    controller.score = Math.round(data.score);
                    controller.user = Math.round(data.score);
                    controller.isLocked = true;
                });
            }
        };

        this.sendSelection = function () {
            if ($routeParams.retroId) {
                $("#saveMessage").fadeIn(100);
                $http.post(root + '/api/meter/retro/' + $routeParams.retroId + '/' + controller.user, null)
                    .success(function (data) {
                        $("#saveMessage").fadeOut(1000);
                    })
                    .error(function(data, status, headers, config) {
                        console.log(status);
                    });
            }
        };


        if ($routeParams.retroId) {
            var client = notificationService.listen("meter", function(message) {
                $scope.$apply(function() {
                    controller.score = Math.round(Number(message));
                });
            });
        }

        notificationService.registerForSprintSelection(function(sprintId) {
            curSprintId = sprintId;
            controller.getData();
        });

        this.getData();

    }]);


})();