(function() {
	
	angular.module('bruno').component('brHome', {
		templateUrl: 'app/component/home/home.html',
		controller: function($state, User) {
			User.currentCachedUser().then(function(user) {
				$state.go('user-home');
			}, function() {
				$state.go('login');
			});
		}
	});
	
})();