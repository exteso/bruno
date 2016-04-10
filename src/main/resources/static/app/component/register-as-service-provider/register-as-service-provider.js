(function() {
	
	angular.module('bruno').component('brRegisterAsServiceProvider', {
		templateUrl: 'app/component/register-as-service-provider/register-as-service-provider.html',
		controller: function(User) {
			var ctrl = this;
			
			
			ctrl.confirm = confirm;
			
			loadUser();
			
			function loadUser() {
				User.current().then(function(user) {
					ctrl.user = user;
				});
			}
			
			function confirm() {
				User.confirmRequestAsServiceProvider().then(loadUser)
			}
		}
	});
	
	
})();