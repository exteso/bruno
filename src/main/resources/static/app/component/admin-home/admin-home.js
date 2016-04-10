(function() {
	
	angular.module('bruno').component('brAdminHome', {
		templateUrl: 'app/component/admin-home/admin-home.html',
		controller: function(User, $filter) {
			var ctrl = this;
			
			ctrl.display = display;
			
			
			User.findAll().then(function(users) {
				ctrl.requests = $filter('filter')(users, function(u) {
					return u.userRequestType === 'SERVICE_PROVIDER' && u.userRequestType !== u.userType;
				});
			});
			
			
			function display(user) {
				var v = $filter('filter')([user.firstname, user.lastname, user.email], function(val) {
					return val != null;
				});
				
				return v.length > 0 ? v.join(' ') : user.username;
			}
		}
	});
	
	
})();