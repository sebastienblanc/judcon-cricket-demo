

angular.module('judconcricket').controller('EditMatchController', function($scope, $routeParams, $location, MatchResource , CommentResource) {
    var self = this;
    $scope.disabled = false;
    $scope.$location = $location;
    
    $scope.get = function() {
        var successCallback = function(data){
            self.original = data;
            $scope.match = new MatchResource(self.original);
            CommentResource.queryAll(function(items) {
                $scope.commentsSelectionList = $.map(items, function(item) {
                    var wrappedObject = {
                        id : item.id
                    };
                    var labelObject = {
                        value : item.id,
                        text : item.title
                    };
                    if($scope.match.comments){
                        $.each($scope.match.comments, function(idx, element) {
                            if(item.id == element.id) {
                                $scope.commentsSelection.push(labelObject);
                                $scope.match.comments.push(wrappedObject);
                            }
                        });
                        self.original.comments = $scope.match.comments;
                    }
                    return labelObject;
                });
            });
        };
        var errorCallback = function() {
            $location.path("/Matchs");
        };
        MatchResource.get({MatchId:$routeParams.MatchId}, successCallback, errorCallback);
    };

    $scope.isClean = function() {
        return angular.equals(self.original, $scope.match);
    };

    $scope.save = function() {
        var successCallback = function(){
            $location.path("/Matchs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        };
        $scope.match.$update(successCallback, errorCallback);
    };

    $scope.cancel = function() {
        $location.path("/Matchs");
    };

    $scope.remove = function() {
        var successCallback = function() {
            $location.path("/Matchs");
            $scope.displayError = false;
        };
        var errorCallback = function() {
            $scope.displayError=true;
        }; 
        $scope.match.$remove(successCallback, errorCallback);
    };
    
    $scope.commentsSelection = $scope.commentsSelection || [];
    $scope.$watch("commentsSelection", function(selection) {
        if (typeof selection != 'undefined' && $scope.match) {
            $scope.match.comments = [];
            $.each(selection, function(idx,selectedItem) {
                var collectionItem = {};
                collectionItem.id = selectedItem.value;
                $scope.match.comments.push(collectionItem);
            });
        }
    });
    
    $scope.get();
});