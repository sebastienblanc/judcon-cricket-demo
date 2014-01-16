angular.module('judconcricket').factory('MatchResource', function($resource){
    var resource = $resource('http://192.168.1.43:8080\:8080/judcon-cricket/rest/matchs/:MatchId',{MatchId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});