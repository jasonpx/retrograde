(function () {
    angular.module('retrograde').factory('userService', ['$http', 'root', function ($http, root) {
        return {
            getUserContext: function () {
                var deferred = $.Deferred();

                // Get user information
                $http.get(root + '/api/user/current').success(function (userContext) {
                    deferred.resolve(userContext);
                });

                return deferred.promise();
            }
        }
    }]);

})();