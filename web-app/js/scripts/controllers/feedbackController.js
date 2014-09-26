(function () {
    angular.module('feedback', [])
        .directive('feedback', function() {
            return {
                restrict: 'E',
                templateUrl: 'views/feedback.html',
                scope: {},
                controller: function ($scope, $element, $attrs, $http, feedbackService, notificationService, $routeParams, userService, retroService) {
                    $scope.feedback = [];
                    var retroId = Number($routeParams.retroId);
                    var userContext = null;
                    var moderator = null;

                    $scope.sendFeedback = function () {
                        feedbackService.sendFeedback(retroId, $attrs.type, $scope.newEntry).then(function (success) {
                            $scope.newEntry = '';
                        });
                    };

                    $scope.amIOwner = function(feed) {
                        return feed.user.id == userContext.id;
                    };

                    $scope.myVote = function(feed, value) {
                        for(var i = 0; i < feed.votes.length; i++) {
                            if(userContext.id == feed.votes[i].user.id) {
                                return feed.votes[i].value == value;
                            }
                        }
                        return false;
                    };

                    $scope.checkForSend = function(event) {
                        if(event.which === 13) {
                            event.preventDefault();
                            $scope.sendFeedback();
                        }
                    };

                    $scope.upVote = function(feedbackId) {
                        feedbackService.upVote(feedbackId);
                    };

                    $scope.downVote = function(feedbackId) {
                        feedbackService.downVote(feedbackId);
                    };

                    $scope.markDiscussed = function(feedbackId) {
                        if (userContext && moderator && userContext.id == moderator.id) {
                            feedbackService.markDiscussed(feedbackId, true);
                        }
                    };

                    $scope.init = function () {
                        userService.getUserContext().then(function(context) {
                            userContext = context;
                            retroService.getModerator(retroId).then(function(mod) {
                                moderator = mod;
                                $scope.getData();
                            });
                        });
                    };

                    $scope.getData= function() {
                        if ($attrs.type === "well") {
                            feedbackService.getWell(retroId).then(function (data) {
                                $scope.feedback = data;
                            });
                        } else if ($attrs.type === "bad") {
                            feedbackService.getBad(retroId).then(function (data) {
                                $scope.feedback = data;
                            });
                        } else if ($attrs.type === "change") {
                            feedbackService.getChange(retroId).then(function (data) {
                                $scope.feedback = data;
                            });
                        }
                    };

                    notificationService.listen($attrs.type, function(message) {
                        $scope.getData();
                    });

                    $scope.init();
                }
            };})
})();
