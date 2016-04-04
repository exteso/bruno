(function() {
	
	angular.module('bruno').component('brToolbar', {
		templateUrl: 'app/component/toolbar/toolbar.html',
		controller: function(User, $state, $window) {
			var ctrl = this;
			
			ctrl.$state = $state;
			
			User.currentCachedUser().then(function(user) {
				ctrl.user = user;
				
				if(user.firstname || user.lastname) {
					ctrl.username = user.firstname + (user.firstname && user.lastname ? ' ' : '') + (user.lastname ? user.lastname : '');
				} else {
					ctrl.username = user.email || (user.provider + ':' + user.username);
				}
			}, function() { //error
				$state.go('login');
			});
			
			ctrl.logout = function() {
				$window.location.href = "/logout";
			}
		}
	});
	
	
})();