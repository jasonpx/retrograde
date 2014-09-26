(function () {

    angular.module('moods', []).controller('Moods', ['$http', 'moodService', 'notificationService', 'userService', '$scope', '$routeParams',
        function ($http, moodService, notificationService, userService, $scope, $routeParams) {
        var moodCtrl = this;
        var retroId = Number($routeParams.retroId);

        // List of all possible mood types
        moodCtrl.moodTypes = [];

        var curSprintId = null;

        // List of current "voted" moods in retro
        var votedMoods = [];

        $scope.getData = function () {
            $scope.getMoodTypes().then(function() {
                if ($routeParams.retroId) {
                    // Get data for retro
                    moodService.getMoodsForRetro(retroId).then(function(moods) {
                        $scope.updateVotedMoods(moods);
                    });
                } else if (curSprintId) {
                    // Get data for sprint (all teams)
                    moodService.getMoodsForSprint(curSprintId).then(function(moods) {
                        $scope.updateVotedMoods(moods);
                    });
                }
            });
        };

        $scope.updateVotedMoods = function(moods) {
            votedMoods = moods;
            $scope.myVoteMoodTypeId(function(myVoteMoodTypeId) {
                for (var i = 0; i < moodCtrl.moodTypes.length; ++i) {
                    moodCtrl.moodTypes[i].nbVotes = $scope.nbVotesForMoodType(moodCtrl.moodTypes[i].id);
                    moodCtrl.moodTypes[i].mine = myVoteMoodTypeId === moodCtrl.moodTypes[i].id;
                }
            });
        };

        $scope.getMoodTypes = function() {
            var deferred = $.Deferred();

            moodService.getMoodTypes().then(function(moodTypes) {
                moodCtrl.moodTypes = moodTypes;
                deferred.resolve();
            });

            return deferred.promise();
        };

        $scope.nbVotesForMoodType = function(moodTypeId) {
            var nbVote = 0;
            for (var i = 0; i < votedMoods.length; ++i) {
                if (votedMoods[i].moodTypeId == moodTypeId) {
                    nbVote++;
                }
            }

            return nbVote;
        };

        $scope.myVoteMoodTypeId = function(callback) {
            userService.getUserContext().then(function(userContext) {
                for (var i = 0; i < votedMoods.length; ++i) {
                    if (votedMoods[i].userId == userContext.id) {
                        callback(votedMoods[i].moodTypeId);
                        return;
                    }
                }

                callback(-1);
                return -1;
            });
        };

        $scope.moodClicked = function(moodId) {
            moodService.voteMoodForRetro(retroId, moodId).then(function() {
                // Data will be refreshed through notification
            });
        };

        notificationService.listen("mood", function(message) {
            var data = JSON.parse(atob(message.replace(/"/g, '')));
            $scope.updateVotedMoods(data);
        });

        notificationService.registerForSprintSelection(function(sprintId) {
            curSprintId = sprintId;
            $scope.getData();
        });

        $scope.getData();

    }]);

})();