(function() {
	
	angular.module('bruno').component('brBidCount', {
		template: '<md-chips ng-if="$ctrl.bidsCount > 0" class="br-chip-counter"><md-chip>{{$ctrl.bidsCount}}</md-chip></md-chips>',
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