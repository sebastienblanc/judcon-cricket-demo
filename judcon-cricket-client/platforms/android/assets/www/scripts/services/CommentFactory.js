angular.module('judconcricket').factory('CommentResource', function($resource){
    var resource = $resource('http://192.168.1.43:8080\:8080/judcon-cricket/rest/comments/:CommentId',{CommentId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});