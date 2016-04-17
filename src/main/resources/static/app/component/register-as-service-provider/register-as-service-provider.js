(function() {
	
	angular.module('bruno').component('brRegisterAsServiceProvider', {
		templateUrl: 'app/component/register-as-service-provider/register-as-service-provider.html',
		controller: function(User, JobRequest) {
			var ctrl = this;
			
			
			ctrl.confirm = confirm;
			
			JobRequest.companyType().then(function(ct) {
				ctrl.fieldOfWorks = ct;
			});
			
			loadUser();
			
			function loadUser() {
				User.current().then(function(user) {
					ctrl.user = user;
				});
			}
			
			function confirm() {
				User.confirmRequestAsServiceProvider(ctrl.request).then(loadUser)
			}
		}
	});
	
	
})();