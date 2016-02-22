(function() {
	
	angular.module('bruno').component('brUserHome', {
		templateUrl: 'app/component/user-home/user-home.html',
		controller: function($mdDialog, JobRequest) {
			var ctrl = this;
			ctrl.openCallForJob = openCallForJob;
			

			//
			
			loadAll();
			
			function loadAll() {
				JobRequest.findAll().then(function(data) {
					ctrl.requests = data;
				});
			}
			
			function openCallForJob() {
				$mdDialog.show({
					fullscreen: true,
					bindToController: true,
					controllerAs: 'newJobDialogController',
					templateUrl: 'app/component/user-home/new-job-dialog.html',
					controller: NewJobDialogController
				});
			}
			
			
			function NewJobDialogController($mdDialog) {
				var ctrl = this;
				
				ctrl.loadFaultTypes = function() {
					return JobRequest.failureType().then(function(faultTypes) {
						ctrl.faultTypes = faultTypes;
					});
				};
				
				ctrl.loadRequestTypes = function() {
					return JobRequest.requestType().then(function(requestTypes) {
						ctrl.requestTypes = requestTypes;
					});
				};
				
				
				ctrl.create = function(request) {
					JobRequest.create(request).then(function() {
						loadAll();
						$mdDialog.cancel();
					})
				};
				
				ctrl.cancel = function() {
					$mdDialog.cancel();
				};
			}
		}
	});
	
	
})();