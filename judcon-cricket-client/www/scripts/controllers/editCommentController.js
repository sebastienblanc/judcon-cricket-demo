

angular.module('judconcricket').controller('EditCommentController', function($scope, $routeParams, $location, CommentResource , MatchResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.comment = new CommentResource(self.original);
            MatchResource.queryAll(function(items) {
                $scope.matchSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.score
                    };
                    if($scope.comment.match && item.id == $scope.comment.match.id) {
                        $scope.matchSelection = labelObject;
                        $scope.comment.match = wrappedObject;
                        self.original.match = $scope.comment.match;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Comments");
        };
        CommentResource.get({CommentId:$routeParams.CommentId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.comment);
    };

    $scope.save = function() {
        var successCallback = function(){
            $location.path("/Comments");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.comment.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Comments");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Comments");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.comment.$remove(successCallback, errorCallback);
    };
    
    $scope.$watch("matchSelection", function(selection) {
        if (typeof selection != 'undefined') {
            $scope.comment.match = {};
            $scope.comment.match.id = selection.value;
        }
    });
    
    $scope.get();
});