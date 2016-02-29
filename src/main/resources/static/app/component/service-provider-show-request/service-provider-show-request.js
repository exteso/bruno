(function() {
	
	angular.module('bruno').component('brServiceProviderShowRequest', {
		templateUrl: 'app/component/service-provider-show-request/service-provider-show-request.html',
		bindings: {
			requestId:'='
		},
		controller: function(JobRequest) {
			var ctrl = this;
			
			JobRequest.findById(ctrl.requestId).then(function(request) {
				ctrl.request = request;
			})
		}
			
	});
	
	
})();