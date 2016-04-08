(function() {
	
	angular.module('bruno').component('brHome', {
		template: '<md-progress-circular md-mode="indeterminate"></md-progress-circular>',
		controller: function($state, User) {
			User.currentCachedUser().then(function(user) {
				
				var typeToState = {
						CUSTOMER : 'user-home',
						SERVICE_PROVIDER : 'service-provider',
						ADMIN: 'admin-home'
				};
				
				$state.go(typeToState[user.userType]);
				
			}, function() {
				$state.go('login');
			});
		}
	});
	
})();