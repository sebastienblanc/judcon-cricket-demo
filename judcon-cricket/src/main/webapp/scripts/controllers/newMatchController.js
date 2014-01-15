
angular.module('judconcricket').controller('NewMatchController', function ($scope, $location, locationParser, MatchResource , CommentResource) {
    $scope.disabled = false;
    $scope.$location = $location;
    $scope.match = $scope.match || {};
    
    $scope.commentsList = CommentResource.queryAll(function(items){
        $scope.commentsSelectionList = $.map(items, function(item) {
            return ( {
                value : item.id,
                text : item.title
            });
        });
    });
    
    $scope.$watch("commentsSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.match.comments = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.match.comments.push(collectionItem);
            });
        }
    });

    $scope.save = function() {
        var successCallback = function(data,responseHeaders){
            var id = locationParser(responseHeaders);
            $location.path('/Matchs');
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError = true;
        };
        MatchResource.save($scope.match, successCallback, errorCallback);
    };
    
    $scope.cancel = function() {
        $location.path("/Matchs");
    };
});