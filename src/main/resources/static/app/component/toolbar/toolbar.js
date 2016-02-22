(function() {
	
	angular.module('bruno').component('brToolbar', {
		templateUrl: 'app/component/toolbar/toolbar.html',
		controller: function(User, $state) {
			var ctrl = this;
			User.currentCachedUser().then(function(user) {
				ctrl.user = user;
			}, function() { //error
				$state.go('login');
			});
		}
	});
	
	
})();