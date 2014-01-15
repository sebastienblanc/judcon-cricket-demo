angular.module('judconcricket').factory('MatchResource', function($resource){
    var resource = $resource('rest/matchs/:MatchId',{MatchId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});