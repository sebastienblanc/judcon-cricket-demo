'use strict';

angular.module('judconcricket',['ngResource'])
  .config(['$routeProvider', function($routeProvider) {
    $routeProvider
      .when('/',{templateUrl:'views/landing.html',controller:'LandingPageController'})
      .when('/Comments',{templateUrl:'views/Comment/search.html',controller:'SearchCommentController'})
      .when('/Comments/new',{templateUrl:'views/Comment/detail.html',controller:'NewCommentController'})
      .when('/Comments/edit/:CommentId',{templateUrl:'views/Comment/detail.html',controller:'EditCommentController'})
      .when('/Matchs',{templateUrl:'views/Match/search.html',controller:'SearchMatchController'})
      .when('/Matchs/new',{templateUrl:'views/Match/detail.html',controller:'NewMatchController'})
      .when('/Matchs/edit/:MatchId',{templateUrl:'views/Match/detail.html',controller:'EditMatchController'})
      .otherwise({
        redirectTo: '/'
      });
  }])
  .controller('LandingPageController', function LandingPageController() {
  })
  .controller('NavController', function NavController($scope, $location) {
    $scope.matchesRoute = function(route) {
        var path = $location.path();
        return (path === ("/" + route) || path.indexOf("/" + route + "/") == 0);
    };
  });
