(function() {
	
	angular.module('bruno').factory('JobRequest', function ($http) {
		return {
			failureType: function() {
				return $http.get('api/job-request/failure-type').then(function(data) {return data.data});
			},
			
			requestType: function() {
				return $http.get('api/job-request/request-type').then(function(data) {return data.data});
			},
			
			companyType: function() {
				return $http.get('api/job-request/company-type').then(function(data) {return data.data});
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
			
			findAllCompletedForServiceProvider: function() {
				return $http.get('api/job-request/list-completed-for-service-provider').then(function(data) {return data.data});
			},
			
			makeBid: function(id, bid) {
				return $http.post('api/job-request/'+id+'/bid', bid);
			},
			
			findBid: function(id) {
				return $http.get('api/job-request/'+id+'/bid').then(function(data) {return data.data;});
			},
			
			findAcceptedBid: function(id) {
				return this.findAllBids(id).then(function(list) {
					for(var i = 0; i < list.length;i++) {
						if(list[i].accepted) {
							return list[i];
						}
					}
				})
			},
			
			findAllBids: function(id) {
				return $http.get('api/job-request/'+id+'/bid-list').then(function(data) {return data.data;});
			},
			
			acceptBid: function(bid) {
				return $http.post('api/job-request/'+bid.requestId+'/bid/'+bid.userId+'/accept');
			},
			
			completeJob : function(bid) {
				return $http.post('api/job-request/'+bid.requestId+'/bid/'+bid.userId+'/complete');
			},
			
			deleteFile : function(requestId, hash) {
				return $http.delete('api/job-request/'+requestId+'/file/'+hash);
			},
			addFile: function(requestId, hash) {
				return $http.post('api/job-request/'+requestId+'/file/'+hash);
			}
		};
	});
	
})();