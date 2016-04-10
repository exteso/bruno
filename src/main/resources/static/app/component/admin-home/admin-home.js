(function() {
	
	angular.module('bruno').component('brAdminHome', {
		templateUrl: 'app/component/admin-home/admin-home.html',
		controller: function(User, $filter, $mdDialog) {
			var ctrl = this;
			
			ctrl.display = display;
			
			ctrl.displayMoreInfo = displayMoreInfo;
			
			loadRequestForServiceProvider();
			
			
			function loadRequestForServiceProvider() {
				User.findAll().then(function(users) {
					ctrl.requests = $filter('filter')(users, function(u) {
						return u.userRequestType === 'SERVICE_PROVIDER' && u.userRequestType !== u.userType;
					});
				});
			}
			
			
			function display(user) {
				var v = $filter('filter')([user.firstname, user.lastname, user.email], function(val) {
					return val != null;
				});
				return v.length > 0 ? v.join(' ') : user.username;
			}
			
			
			function displayMoreInfo($event, user) {
				var confirm = $mdDialog.confirm()
					.title('Accept user request')
					.textContent('Accept service provider request for user: ' + display(user))
					.targetEvent($event)
					.ok('Accept')
					.cancel('Cancel');
				$mdDialog.show(confirm).then(function() {
					User.acceptAsServiceProvider(user).then(loadRequestForServiceProvider);
				});
			}
		}
	});
	
	
})();