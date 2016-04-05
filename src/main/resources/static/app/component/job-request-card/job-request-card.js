(function() {
	
	angular.module('bruno').component('brJobRequestCard', {
		templateUrl: 'app/component/job-request-card/job-request-card.html',
		bindings: {
			request: "=",
			userType: '@'
		},
		controller: function(JobRequest, $mdToast, $sce) {
			var ctrl = this;
			
			ctrl.makeBid = makeBid;
			ctrl.acceptBid = acceptBid;
			ctrl.completeJob = completeJob;
			ctrl.hasContentType = hasContentType;
			
			if(ctrl.userType === 'SERVICE_PROVIDER') {
				loadBid();
			} else if (ctrl.userType === 'USER') {
				loadAllBids();
			}
			
			trustVideoUrls();
			
			function trustVideoUrls() {
				for(var i = 0; i<ctrl.request.files.length;i++) {
					var f = ctrl.request.files[0];
					if(hasContentType('video/', f)) {
						f.source = [{src: $sce.trustAsResourceUrl('api/file/'+f.hash), type: f.contentType}];
					}
				}
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
					$mdToast.show($mdToast.simple().textContent('Offerta salvata').parent(document.getElementById("toast-container")));
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
			
			function hasContentType(type, file) {
				return file.contentType.indexOf(type) === 0;
			};
		}
	});
	
	
})();