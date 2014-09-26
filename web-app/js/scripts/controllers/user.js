(function () {
    var app = angular.module('user', []);

    app.controller('UserController', ['$http', 'userService', function ($http, userService) {

    	var controller = this;
    	controller.user = [];
        this.getData = function () {
            userService.getUserContext().then(function(userContext) {
                controller.user = userContext;
            });
        };

        this.getData();

    }]);


})();