(function () {
    var app = angular.module('new-retro', []);

    app.controller('NewRetroController', ['$location','$http', '$scope', 'userService','sprintService', 'root', function ($location,$http, $scope, userService,sprintService, root) {
	
	$scope.sprints = [];
    	
    $scope.updateSprints = function(){
	    $http.get(root+'/api/sprint/listAvailable/'+$scope.selectedTeam).success(function (sprints) {
	    	$scope.sprints = sprints
	    });
    };
        
    $scope.teams = [];
    $http.get(root + '/api/team/user/current').success(function (data) {
    	$scope.teams = data;
    });
 
	$scope.status = {
	  isopen: false
	};
	
	$scope.toggled = function(open) {
	  console.log('Dropdown is now: ', open);
	};
	
	$scope.toggleDropdown = function($event) {
	  $event.preventDefault();
	  $event.stopPropagation();
	  $scope.status.isopen = !$scope.status.isopen;
	};
	
	
	$scope.newRetro = function() {

	    $http.post(root + '/api/retro/create/'+$scope.selectedSprint+"/"+$scope.selectedTeam).success(function (retro) {
	    	$location.path('/Retro/'+retro.id+'/'+$scope.selectedTeam);
	    });
		
	};
	
	$scope.createEnabled = false;
	
	$scope.enableCreate = function(){
		if($scope.selectedTeam && $scope.selectedSprint){
			$scope.createEnabled = true;
		}
		else{
			$scope.createEnabeld = false;
		}
	}
	

}]);


})();