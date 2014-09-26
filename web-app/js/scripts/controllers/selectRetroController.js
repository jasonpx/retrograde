(function () {
    var app = angular.module('retro-list', []);

    app.controller('RetroListController', ['$http', '$scope', '$injector', 'sprintService', 'moodService', 'notificationService', 'root',
        function ($http, $scope, $injector, sprintService, moodService, notificationService, root) {

    	var controller = this;
    	controller.user = [];
    	controller.retros = [];
        $scope.sprints = [];

        this.getData = function () {
            
        	$http.get(root + '/api/user/current').success(function (userData) {
                controller.user = userData;
            });

            $scope.listRetros();
            $scope.listSprints();
        };

        $scope.listRetros = function () {
            $http.get(root + '/api/retro/currentUser').success(function (retroData) {
                controller.retros = retroData;
            });
        };

        $scope.listSprints = function() {
            sprintService.list().then(function(list) {
                $scope.sprints = list;
                $scope.sprintModel = $scope.sprints[0].id;
                $scope.sprintSelectionChanged();
            });
        };

        $scope.sprintSelectionChanged = function() {
            notificationService.sprintSelectionChanged($scope.sprintModel);
        };

        this.getData();

        notificationService.listen("newretro", function() {
            $scope.listRetros();
        });
        
        $scope.retroSelected = function(retroId) {
        	$location.path('home/'+retroId);
        };
    }]);


})();