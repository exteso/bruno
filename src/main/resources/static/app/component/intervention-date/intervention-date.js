(function() {
	
	angular.module('bruno').component('brInterventionDate', {
		template: "{{$ctrl.bid.selectedDate | date: 'dd.MM.yyyy'}}",
		bindings: {
			request: '='
		},
		controller: function(JobRequest) {
			
			var ctrl = this;
			
			JobRequest.findAcceptedBid(ctrl.request.id).then(function(data) {
				ctrl.bid = data;
			})
		}
	});
	
	
})();