(function() {
	
	angular.module('bruno').component('brUserHome', {
		templateUrl: 'app/component/user-home/user-home.html',
		controller: function($mdDialog, $state, JobRequest) {
			var ctrl = this;
			ctrl.openCallForJob = openCallForJob;
			ctrl.openRequest = openRequest;
			

			//
			
			loadAll();
			
			function loadAll() {
				JobRequest.findAll().then(function(data) {
					
					ctrl.requests = [];
					ctrl.requestsCompleted = [];
					ctrl.requestsAccepted = [];
					angular.forEach(data, function(v) {
						if(v.state === 'CLOSED') {
							ctrl.requestsCompleted.push(v);
						} else if (v.state === 'ASSIGNED') {
							ctrl.requestsAccepted.push(v);
						} else {
							ctrl.requests.push(v);
						}
					});
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
				
				ctrl.request = {files: []};
				
				ctrl.addNewFile = function(hash) {
					ctrl.request.files.push(hash);
				};
				
				ctrl.deleteNewFile = function(hash) {
					var idx = ctrl.request.files.indexOf(hash); 
					if(idx >= 0) {
						ctrl.request.files.splice(idx, 1);
					}
				};
				
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
			
			function openRequest(request) {
				$state.go('user-show-request', {requestId: request.id});
			}
		}
	});
	
	
})();