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
			ctrl.acceptBid = acceptBid;
			ctrl.completeJob = completeJob;
			
			
			
			if(ctrl.userType === 'SERVICE_PROVIDER') {
				loadBid();
			} else if (ctrl.userType === 'USER') {
				loadAllBids();
			}
			
			
			function loadBid() {
				JobRequest.findBid(ctrl.request.id).then(function(bid) {
					ctrl.bid = {
							cost : bid.price,
							date: bid.selectedDate ? new Date(bid.selectedDate) : null
					};
					
					ctrl.userBid = bid;
				});
			}
			
			function loadAllBids() {
				JobRequest.findAllBids(ctrl.request.id).then(function(bids) {
					ctrl.bids = bids;
				});
			}
			
			function makeBid(bid) {
				JobRequest.makeBid(ctrl.request.id, bid).then(function() {
					loadBid();
				});
			}
			
			function acceptBid(bid) {
				JobRequest.acceptBid(bid).then(function() {
					loadAllBids();
				});
			}
			
			function completeJob(bid) {
				JobRequest.completeJob(bid).then(function() {
					JobRequest.findById(ctrl.request.id).then(function(req) {
						ctrl.request = req;
					})
				});
			};
		}
	});
	
	
})();