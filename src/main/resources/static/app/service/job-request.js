(function() {
	
	angular.module('bruno').factory('JobRequest', function ($http) {
		return {
			failureType: function() {
				return $http.get('api/job-request/failure-type').then(function(data) {return data.data});
			},
			
			requestType: function() {
				return $http.get('api/job-request/request-type').then(function(data) {return data.data});
			},
			
			create: function(request) {
				return $http.post('api/job-request', request);
			},
			
			findAll: function() {
				return $http.get('api/job-request/list').then(function(data) {return data.data});
			}
		};
	});
	
})();