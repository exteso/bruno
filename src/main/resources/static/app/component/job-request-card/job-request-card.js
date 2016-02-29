(function() {
	
	angular.module('bruno').component('brJobRequestCard', {
		templateUrl: 'app/component/job-request-card/job-request-card.html',
		bindings: {
			request: "=",
			userType: '@'
		},
		controller: function($mdDialog) {
			var ctrl = this;
			var request = ctrl.request;
			//ctrl.openForJobEdit = openCallForJob;


		}
	});
	
	
})();