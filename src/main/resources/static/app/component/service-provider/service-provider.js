(function() {
	
	angular.module('bruno').component('brServiceProvider', {
		templateUrl: 'app/component/service-provider/service-provider.html',
		controller: function(JobRequest, $state) {
			var ctrl = this;
			
			ctrl.openRequest = openRequest;
			
			//
			
			loadAll();
			
			function loadAll() {
				JobRequest.findAllForServiceProvider().then(function(data) {
					ctrl.requests = data;
				});
				
				JobRequest.findAllAcceptedForServiceProvider().then(function(data) {
					ctrl.acceptedRequests = data; 
				});
				
				JobRequest.findAllCompletedForServiceProvider().then(function(data) {
					ctrl.completedRequests = data;
				});
			}
			
			
			function openRequest(request) {
				$state.go('service-provider-show-request', {requestId: request.id});
			}
		}
			
	});
	
	
})();