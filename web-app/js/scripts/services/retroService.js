(function () {
    angular.module('retrograde').factory('retroService', ['$http', 'root', function ($http, root) {

        return {
            finishRetro: function (retroId) {
                var deferred = $.Deferred();

                $http.post(root + '/api/retro/' + retroId).success(function () {
                    deferred.resolve();
                });

                return deferred.promise();
            },

            createRetro: function (sprintId) {
                var deferred = $.Deferred();

                $http.put(root + '/api/retro/' + sprintId).success(function (retroId) {
                    deferred.resolve(retroId);
                });

                return deferred.promise();
            },

            getModerator: function (retroId) {
                var deferred = $.Deferred();

                $http.get(root + '/api/retro/moderator/' + retroId).success(function (user) {
                    deferred.resolve(user);
                });

                return deferred.promise();
            },

            getStatus: function (retroId) {
                var deferred = $.Deferred();

                $http.get(root + '/api/retro/' + retroId + '/status').success(function (status) {
                    deferred.resolve(status);
                });

                return deferred.promise();
            },

            getName: function (retroId) {
                var deferred = $.Deferred();

                $http.get(root + '/api/retro/' + retroId + "/name").success(function (name) {
                    deferred.resolve(name);
                });

                return deferred.promise();
            }
        }
    }]);

})();