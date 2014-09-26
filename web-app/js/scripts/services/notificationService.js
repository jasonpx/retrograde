(function () {
    angular.module('retrograde').factory('notificationService', ['$http', 'root', function ($http, root) {

        var socket = new SockJS(root + "/stomp");
        var client = Stomp.over(socket);
        var topics = [];
        var sprintSelectionEvents = [];

        client.connect({}, function() {
            // Callback when we are connected to server
        });

        var listenToTopic = function (topic, callback) {
            setTimeout(function() {
                if (client.connected) {
                    client.subscribe("/topic/" + topic, function(message) {
                        callback(message.body);
                    });
                } else {
                    listenToTopic(topic, callback);
                }
            }, 100);
        };

        return {
            listen: function (topic, callback) {
                // Socket
                listenToTopic(topic, callback);
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