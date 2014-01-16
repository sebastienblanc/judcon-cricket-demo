
angular.module('judconcricket').controller('NewCommentController', function ($scope, $location, locationParser, CommentResource , MatchResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.comment = $scope.comment || {};
    
    $scope.matchList = MatchResource.queryAll(function(items){
        $scope.matchSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.score
            });
        });
    });
    
    $scope.$watch("matchSelection", function(selection) {
        if ( typeof selection != 'undefined') {
            $scope.comment.match = {};
            $scope.comment.match.id = selection.value;
        }
    });

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Comments');
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        CommentResource.save($scope.comment, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Comments");
    };
});