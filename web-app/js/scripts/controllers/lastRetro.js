(function () {
    var app = angular.module('last-retro', []);

    app.controller('LastRetroController', ['$http', '$scope', '$routeParams', 'notificationService', 'root', function ($http, $scope, $routeParams,notificationService, root) {
        var lastRetro = this;
        lastRetro.feedback = [];

        this.getData = function () {
            $http.get(root + '/api/feedback/lastRetroItems/'+$routeParams.retroId).success(function (data) {
                lastRetro.feedback = data;
            });
        };

        /*
        The way this method works relies on the data being pulled again after a change is made
         */
        this.toggleCompleted = function (feedbackId) {
            // get the item out of the list of feedbacks by id
            var item = lastRetro.feedback.filter(function(obj) {
                return obj.id == feedbackId
            });
            // get the current value of the completed flag, then ! it bc we're changing it
            var val = !item[0].completed;
            $http.post(root + '/api/feedback/completed/' + feedbackId + '/' + val);
            lastRetro.getData()
        };

        // refresh the data when something is changed
        var client = notificationService.listen("lastretro", function(message) {
            lastRetro.getData();
        });

        this.getData();

    }]);


})();