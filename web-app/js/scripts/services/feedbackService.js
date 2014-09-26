(function () {
    angular.module('retrograde').factory('feedbackService', ['$http', 'root', function ($http, root) {
        var vote = function(feedbackId, bUpVote) {
            var deferred = $.Deferred();

            // Get mood types from server
            $http.post(root + '/api/feedback/vote/' + feedbackId + (bUpVote ? "/1" : "/-1")).success(function (data) {
                deferred.resolve(data);
            });

            return deferred.promise();
        };

        return {

            getAll: function (retroId) {
                return this.getFeedback(retroId, '');
            },

            getAllBySprint: function (sprintId) {
                return this.getFeedbackBySprint(sprintId, '');
            },

            getWell: function (retroId) {
                return this.getFeedback(retroId, 'well/');
            },

            getBad: function (retroId) {
                return this.getFeedback(retroId, 'bad/');
            },

            getChange: function (retroId) {
                return this.getFeedback(retroId, 'change/');
            },

            getFeedback: function (retroId, type) {
                var deferred = $.Deferred();

                // Get mood types from server
                var myThis = this;
                $http.get(root + '/api/feedback/' + type + retroId).success(function (data) {
                    deferred.resolve(myThis.calculateVotes(data));
                });

                return deferred.promise();
            },

            getFeedbackBySprint: function (sprintId, type) {
                var deferred = $.Deferred();

                // Get feedback from server
                var myThis = this;
                $http.get(root + '/api/feedback/sprint/' + sprintId + type).success(function (data) {
                    deferred.resolve(myThis.calculateVotes(data));
                });

                return deferred.promise();
            },

            calculateVotes: function(feedback) {
                for (var i = 0; feedback && i < feedback.length; ++i) {
                    var up = 0;
                    var down = 0;
                    for (var j = 0; j < feedback[i].votes.length; ++j) {
                        if (feedback[i].votes[j].value > 0) {
                            up++;
                        } else {
                            down++;
                        }
                    }

                    feedback[i].upVotes = up;
                    feedback[i].downVotes = down;
                }

                return feedback;
            },

            sendFeedback: function(retroId, feedbackType, feedbackMsg) {
                var deferred = $.Deferred();

                var postData = "{\"text\": \"" + feedbackMsg + "\"}";

                $http.post(root + '/api/feedback/' + feedbackType + '/' + retroId, postData)
                    .success(function (data) {
                        deferred.resolve(true);
                    })
                    .error(function(data, status, headers, config) {
                        deferred.resolve(false);
                        console.log('Error: ' + status);
                    });

                return deferred.promise();
            },

            markDiscussed: function(feedbackId, bCompleted) {
                var deferred = $.Deferred();

                // Get feedback from server
                var myThis = this;
                $http.post(root + '/api/feedback/discussed/' + feedbackId + "/" + bCompleted).success(function (data) {
                    deferred.resolve();
                });

                return deferred.promise();
            },

            upVote: function (feedbackId) {
                return vote(feedbackId, true);
            },

            downVote: function (feedbackId) {
                return vote(feedbackId, false);
            },

            getFeedbackWords: function(retroId, callback) {
                var txt = "";
                this.getAll(retroId).then(function(feedback) {
                    for (var i = 0; feedback && i < feedback.length; ++i) {
                        txt += feedback[i].text + " ";
                    }

                    callback(txt);
                });
            },

            getFeedbackWordsBySprint: function(sprintId, callback) {
                var txt = "";
                this.getAllBySprint(sprintId).then(function(feedback) {
                    for (var i = 0; feedback && i < feedback.length; ++i) {
                        txt += feedback[i].text + " ";
                    }

                    callback(txt);
                });
            }
        }
    }]);

})();