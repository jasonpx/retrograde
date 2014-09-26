(function () {
    angular.module('retrograde').factory('sprintService', ['$http', 'root', function ($http, root) {

        return {
            list: function () {
                var deferred = $.Deferred();

                $http.get(root + '/api/sprint/').success(function (list) {
                    deferred.resolve(list);
                });

                return deferred.promise();
            }
        }
    }]);

})();