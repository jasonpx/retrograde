(function () {
    angular.module('rewards', []).controller('Rewards', ['$http', 'rewardService', 'userService', '$scope', '$routeParams',
        function ($http, rewardService, userService, $scope, $routeParams) {
        var rewardsCtrl = this;
        var retroId = Number($routeParams.retroId);
        var unlock = "POOP";
        var unlockIndex = 0;
        $scope.showHiddenRewards = false;

        $scope.getData = function () {
            rewardService.getRewardTypes().then(function(rewardTypes) {
                // Get rewards
                rewardService.getRewardsForRetro(retroId).then(function(rewards) {
                   // Display rewards...
                    userService.getUserContext().then(function(userContext) {
                        for (var i = 0; i < rewards.length; ++i) {
                            if (rewards[i].senderId == userContext.id) {
                                // Reward is from current user. Remove from list of possible rewards
                                for (var j = 0; j < rewardTypes.length; ++j) {
                                    if (rewardTypes[j].id == rewards[i].rewardTypeId) {
                                        //rewardTypes.splice(j, 1);
                                        rewardTypes[j].isUsed = true;
                                    }
                                }
                            }
                        }
                        rewardsCtrl.rewardTypes = rewardTypes;
                    });
                });
            });
        };

        $scope.checkForEasterEgg = function(event) {
            if (unlockIndex < unlock.length && unlock[unlockIndex] == String.fromCharCode(event.which)) {
                unlockIndex++;
            }

            if(event.which === 13) {
                if (unlockIndex == unlock.length) {
                    $scope.showHiddenRewards = true;
                }

                unlockIndex = 0;
            }
        };

        $scope.giveReward = function (rewardTypeId, recipientId) {
            rewardService.giveReward(retroId, recipientId, rewardTypeId).then(function() {
                $scope.getData();
            });
        };


        $scope.getData();

    }]);

})();