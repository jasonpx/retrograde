(function () {
    angular.module('retrograde').factory('rewardService', ['$http', 'root', function ($http, root) {
        return {
            getRewardTypes: function () {
                var deferred = $.Deferred();

                // Get reward types from server
                $http.get(root + '/api/rewardtype').success(function (rewardTypes) {
                    deferred.resolve(rewardTypes);
                });

                return deferred.promise();
            },

            getRewardsForRetro: function(retroId) {
                var deferred = $.Deferred();

                // Get the rewards for the retro
                $http.get(root + '/api/reward/retro/' + retroId).success(function(rewards) {
                    deferred.resolve(rewards);
                });

                return deferred.promise();
            },

            giveReward: function(retroId, recipientId, rewardTypeId) {
                console.log('service hit');
                var deferred = $.Deferred();

                // Give the reward
                $http.post(root + '/api/reward/retro/' + retroId + "/" + rewardTypeId + "/" + recipientId).success(function() {
                    deferred.resolve();
                });
                return deferred.promise();
            }
        }
    }]);

})();