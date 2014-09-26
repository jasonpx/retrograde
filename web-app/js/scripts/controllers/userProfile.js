(function () {
    var app = angular.module('userProfile', []);

    app.controller('UserProfileController', ['$http', 'userService', 'userProfileService', function ($http, userService, userProfileService) {

        var controller = this;
        controller.user = [];

        this.getData = function () {
            userService.getUserContext().then(function(userContext) {
                controller.user = userContext;
            });
        };

        this.updateUserProfile = function () {

            if (controller.newPassword != controller.newPassword_verify) {
                controller.message = 'Passwords did not match, please try again.';
            }
            else
            {
                userProfileService.updateUser(controller.user.id,
                    controller.user.username,
                    controller.user.first,
                    controller.user.last,
                    controller.newPassword_verify);
                controller.message = 'User profile updated';
            }
        };

        this.getData();

    }]);


})();