(function() {
	
	angular.module('bruno').component('brBidCount', {
		template: "{{$ctrl.bidsCount}}",
		bindings: {
			request: '='
		},
		controller: function(JobRequest) {
			
			var ctrl = this;
			
			ctrl.bidsCount = 0;
			
			JobRequest.findAllBids(ctrl.request.id).then(function(data) {
				ctrl.bidsCount = data.length;
			})
		}
	});
	
	
})();