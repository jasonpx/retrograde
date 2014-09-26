(function () {
    var app = angular.module('word-cloud', []);

    app.controller('WordCloudController', ['$window', '$http', '$scope', 'feedbackService', 'notificationService', '$routeParams',
        function ($window, $http, $scope, feedbackService, notificationService, $routeParams) {

        var retroId = Number($routeParams.retroId);
        var curSprintId = null;
        var fill = d3.scale.category20c();
        var
            w = $('#word-cloud').parent().innerWidth(),
            h = $('#word-cloud').parent().innerHeight();

        var words = [],
            max,
            scale = 1,
            complete = 0,
            tags,
            fontSize,
            maxLength = 20,
            statusText = d3.select("#status");


            $scope.draw = function(data, bounds) {
                statusText.style("display", "none");
                scale = bounds ? Math.min(
                    w / Math.abs(bounds[1].x - w / 2),
                    w / Math.abs(bounds[0].x - w / 2),
                    h / Math.abs(bounds[1].y - h / 2),
                    h / Math.abs(bounds[0].y - h / 2)) / 2 : 1;
                words = data;
                var text = vis.selectAll("text")
                    .data(words, function(d) { return d.text.toLowerCase(); });
                text.transition()
                    .duration(1000)
                    .attr("transform", function(d) { return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")"; })
                    .style("font-size", function(d) { return d.size + "px"; });
                text.enter().append("text")
                    .attr("text-anchor", "middle")
                    .attr("transform", function(d) { return "translate(" + [d.x, d.y] + ")rotate(" + d.rotate + ")"; })
                    .style("font-size", function(d) { return d.size + "px"; })
                    .on("click", function(d) {
                        load(d.text);
                    })
                    .style("opacity", 1e-6)
                    .transition()
                    .duration(1000)
                    .style("opacity", 1);
                text.style("font-family", function(d) { return d.font; })
                    .style("fill", function(d) { return fill(d.text.toLowerCase()); })
                    .text(function(d) { return d.text; });
                var exitGroup = background.append("g")
                    .attr("transform", vis.attr("transform"));
                var exitGroupNode = exitGroup.node();
                text.exit().each(function() {
                    exitGroupNode.appendChild(this);
                });
                exitGroup.transition()
                    .duration(1000)
                    .style("opacity", 1e-6)
                    .remove();
                vis.transition()
                    .delay(1000)
                    .duration(750)
                    .attr("transform", "translate(" + [w >> 1, h >> 1] + ")scale(" + scale + ")");
            };


            $scope.progress = function(d) {
            };

        var layout = d3.layout.cloud()
            .timeInterval(10)
            .size([w, h])
            .fontSize(function(d) { return fontSize(+d.value); })
            .text(function(d) { return d.key; })
            .on("word", $scope.progress)
            .on("end", $scope.draw);

        var svg = d3.select("#cloud").append("svg")
            .attr("width", w)
            .attr("height", h);

        var background = svg.append("g"),
            vis = svg.append("g")
            .attr("transform", "translate(" + [w >> 1, h >> 1] + ")");

        var stopWords = /^(i|me|my|myself|we|us|our|ours|ourselves|get|you|your|yours|yourself|yourselves|he|him|his|himself|she|her|hers|herself|it|its|itself|they|them|their|theirs|themselves|what|which|who|whom|whose|this|that|these|those|am|is|are|was|were|be|been|being|have|has|had|having|do|does|did|doing|will|would|should|can|could|ought|i'm|you're|he's|she's|it's|we're|they're|i've|you've|we've|they've|i'd|you'd|he'd|she'd|we'd|they'd|i'll|you'll|he'll|she'll|we'll|they'll|isn't|aren't|wasn't|weren't|hasn't|haven't|hadn't|doesn't|don't|didn't|won't|wouldn't|shan't|shouldn't|can't|cannot|couldn't|mustn't|let's|that's|who's|what's|here's|there's|when's|where's|why's|how's|a|an|the|and|but|if|or|because|as|until|while|of|at|by|for|with|about|against|between|into|through|during|before|after|above|below|to|from|up|upon|down|in|out|on|off|over|under|again|further|then|once|here|there|when|where|why|how|all|any|both|each|few|more|most|other|some|such|no|nor|not|only|own|same|so|than|too|very|say|says|said|shall)$/,
            wordSeparators = /[\s\u3031-\u3035\u309b\u309c\u30a0\u30fc\uff70]+/g,
            discard = /^(@|https?:|\/\/)/;

        $scope.init = function() {
            w = $('#word-cloud').parent().innerWidth();
            h = $('#word-cloud').parent().innerHeight();
            layout.size([w, h]);

            $scope.loadText();

            if ($routeParams.retroId) {
                var topicHandler = function (message) {
                    var data = JSON.parse(message);
                    if (data && data.content && data.content.type === "new_feedback") {
                        $scope.loadText();
                    }
                };

                notificationService.listen("well", topicHandler);
                notificationService.listen("bad", topicHandler);
                notificationService.listen("change", topicHandler);
            }
        };

        $scope.loadText = function() {
            if ($routeParams.retroId) {
                // Get words from retro feedback
                feedbackService.getFeedbackWords(retroId, function(text) {
                    $scope.parseText(text);
                });
            } else {
                // Get words from all retros in sprint
                feedbackService.getFeedbackWordsBySprint(curSprintId, function(text) {
                    $scope.parseText(text);
                });
            }
        };

        $scope.parseText = function(text) {
            tags = {};
            var cases = {};
            text.split(wordSeparators).forEach(function(word) {
                if (discard.test(word)) return;
                if (stopWords.test(word.toLowerCase())) return;
                word = word.substr(0, maxLength);
                cases[word.toLowerCase()] = word;
                tags[word = word.toLowerCase()] = (tags[word] || 0) + 1;
            });
            tags = d3.entries(tags).sort(function(a, b) { return b.value - a.value; });
            tags.forEach(function(d) { d.key = cases[d.key]; });
            $scope.generate();
        };


        $scope.generate = function() {
            layout
                .font("Impact")
                .spiral("archimedean");
            fontSize = d3.scale["log"]().range([14, 40]);
            if (tags.length) fontSize.domain([+tags[tags.length - 1].value || 1, +tags[0].value]);
            complete = 0;
            statusText.style("display", null);
            words = [];
            layout.stop().words(tags.slice(0, max = Math.min(tags.length, 10))).start();
        };

        if (!$routeParams.retroId) {
            notificationService.registerForSprintSelection(function(sprintId) {
                curSprintId = sprintId;
                $scope.init();
            });
        } else {
            // Delay loading in retro window because of the fancy flying controls
            //$window.setTimeout($scope.init(), 100);
            $scope.init();
        }

    }]);

})();