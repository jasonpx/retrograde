(function () {
    angular.module('retrograde').factory('moodService', ['$http', 'root', function ($http, root) {
        var sprintSelectionEvents = [];

        return {
            getMoodTypes: function () {
                var deferred = $.Deferred();

                // Get mood types from server
                $http.get(root + '/api/moodtype').success(function (moodTypes) {
                    deferred.resolve(moodTypes);
                });

                return deferred.promise();
            },

            getMoodsForRetro: function(retroId) {
                var deferred = $.Deferred();

                // Get the moods for the retro
                $http.get(root + '/api/mood/retro/' + retroId).success(function(moods) {
                    deferred.resolve(moods);
                });

                return deferred.promise();
            },

            getMoodsForSprint: function(sprintId) {
                var deferred = $.Deferred();

                // Get the moods for the sprint (across all teams)
                $http.get(root + '/api/mood/sprint/' + sprintId).success(function(moods) {
                    deferred.resolve(moods);
                });

                return deferred.promise();
            },

            voteMoodForRetro: function(retroId, moodTypeId) {
                var deferred = $.Deferred();

                // Save the mood for the retro
                $http.post(root + '/api/mood/retro/' + retroId + '/' + moodTypeId).success(function() {
                    deferred.resolve();
                });

                return deferred.promise();
            },

            registerForSprintSelection: function(callback) {
                sprintSelectionEvents.push(callback);
            },

            sprintSelectionChanged: function(sprintId) {
                for (var i = 0; i < sprintSelectionEvents.length; ++i) {
                    sprintSelectionEvents[i](sprintId);
                }
            }
        }
    }]);

})();