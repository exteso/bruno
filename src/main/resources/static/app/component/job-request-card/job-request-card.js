(function() {
	
	angular.module('bruno').component('brJobRequestCard', {
		templateUrl: 'app/component/job-request-card/job-request-card.html',
		bindings: {
			request: "=",
			userType: '@'
		},
		controller: function(JobRequest) {
			var ctrl = this;
			
			ctrl.makeBid = makeBid;
			
			if(ctrl.userType === 'SERVICE_PROVIDER') {
				loadBid();
			}
			
			
			function loadBid() {
				JobRequest.findBid(ctrl.request.id).then(function(bid) {
					ctrl.bid = {
							cost : bid.price,
							date: bid.selectedDate ? new Date(bid.selectedDate) : null
					};
				});
			} 
			
			function makeBid(bid) {
				JobRequest.makeBid(ctrl.request.id, bid).then(function() {
					loadBid();
				});
			}
		}
	});
	
	
})();