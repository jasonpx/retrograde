(function () {
    var app = angular.module('team-panel', []);

    app.controller('TeamController', ['$http', 'rewardService', 'notificationService', '$scope', '$routeParams', 'root',
        function ($http, rewardService, notificationService, $scope, $routeParams, root) {
        $scope.users = [];
        var retroId = Number($routeParams.retroId);
        var teamId = Number($routeParams.teamId);
        $scope.team = null;


        $scope.getData = function () {
            rewardService.getRewardsForRetro(retroId).then(function (rewards) {
                $scope.updateRewards(rewards);
            });
        };

        $scope.updateRewards = function (rewards) {
            // Get team
            if (!$scope.team) {
                $http.get(root + '/api/user/team/' + teamId).success(function (data) {
                    $scope.team = data;
                    $scope.updateTeamRewards(rewards);
                });
            } else {
                // Simply update rewards
                $scope.updateTeamRewards(rewards);
            }
        };

        $scope.updateTeamRewards = function(rewards) {
            for (var i = 0; i < $scope.team.length; ++i) {
                $scope.team[i].rewards = $scope.getUserRewards(rewards, $scope.team[i].id);
            }

            $scope.users = $scope.team;
        };

        $scope.getUserRewards = function(allRewards, userId) {
            var rewards = [];
            for (var i = 0; i < allRewards.length; ++i) {
                if (allRewards[i].recipientId == userId) {
                    rewards.push(allRewards[i]);
                }
            }

            return rewards;
        };
        $scope.giveReward = function (rewardTypeId, recipientId) {
            rewardService.giveReward(retroId, recipientId, rewardTypeId).then(function() {
                // Update should come from notification
            });
        };

        $scope.handleDrop = function(source, recipId) {
            var rewardId = source.id;
            $scope.giveReward(rewardId, recipId);
            $(source).hide();
            $(source).find('.dragImage').hide();
            return false;
        };

        notificationService.listen("reward", function(message) {
            var data = JSON.parse(atob(message.replace(/"/g, '')));
            $scope.$apply(function() {
                $scope.updateRewards(data);
            });
        });

        $scope.getData();

    }]);

})();