(function() {
	
	angular.module('bruno').component('brUserShowRequest', {
		templateUrl: 'app/component/user-show-request/user-show-request.html',
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