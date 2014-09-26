(function () {
    angular.module('retrograde').factory('userProfileService', ['$http', 'root', function ($http, root) {
        return {
            updateUser: function (userId, username, first, last, password) {
                var deferred = $.Deferred();

                if (password == undefined) password = '';

                var postData = "{" +
                    "\"username\": \"" + username + "\"" +
                    ",\"first\": \"" + first + "\"" +
                    ",\"last\": \"" + last + "\"" +
                    ",\"password\": \"" + password + "\"" +
                    "}";

                console.log(postData);

                $http.post(root + '/api/user/update/' + userId, postData)
                    .success(function() {
                        deferred.resolve();
                    })
                    .error(function(data, status, headers, config) {
                        deferred.resolve(false);
                        console.log('Error: ' + status);
                    });

                return deferred.promise();
            }
        }
    }]);

})();