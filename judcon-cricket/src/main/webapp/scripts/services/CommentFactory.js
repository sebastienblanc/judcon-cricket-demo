angular.module('judconcricket').factory('CommentResource', function($resource){
    var resource = $resource('rest/comments/:CommentId',{CommentId:'@id'},{'queryAll':{method:'GET',isArray:true},'query':{method:'GET',isArray:false},'update':{method:'PUT'}});
    return resource;
});