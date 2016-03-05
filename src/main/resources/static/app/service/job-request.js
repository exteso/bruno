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
			},
			
			findById: function(id) {
				return $http.get('api/job-request/'+id).then(function(data) {return data.data});
			},
			
			findAllForServiceProvider: function() {
				return $http.get('api/job-request/list-for-service-provider').then(function(data) {return data.data});
			},
			
			findAllAcceptedForServiceProvider: function() {
				return $http.get('api/job-request/list-accepted-for-service-provider').then(function(data) {return data.data});
			},
			
			makeBid: function(id, bid) {
				return $http.post('api/job-request/'+id+'/bid', bid);
			},
			
			findBid: function(id) {
				return $http.get('api/job-request/'+id+'/bid').then(function(data) {return data.data;});
			},
			
			findAllBids: function(id) {
				return $http.get('api/job-request/'+id+'/bid-list').then(function(data) {return data.data;});
			},
			
			acceptBid: function(bid) {
				return $http.post('api/job-request/'+bid.requestId+'/bid/'+bid.userId+'/accept');
			}
		};
	});
	
})();