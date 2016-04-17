(function() {
	
	angular.module('bruno').component('brAdminHome', {
		templateUrl: 'app/component/admin-home/admin-home.html',
		controller: function(User, $filter, $mdDialog) {
			var ctrl = this;
			
			ctrl.displayMoreInfo = displayMoreInfo;
			
			loadRequestForServiceProvider();
			
			
			function loadRequestForServiceProvider() {
				User.findAll().then(function(users) {
					ctrl.requests = $filter('filter')(users, function(u) {
						return u.userRequestType === 'SERVICE_PROVIDER' && u.userRequestType !== u.userType;
					});
				});
			}
			
			

			function displayMoreInfo($event, user) {
				var confirm = $mdDialog.confirm()
					.title($filter('translate')('admin-home.acceptRequest'))
					.textContent($filter('translate')('admin-home.request', user))
					.targetEvent($event)
					.ok($filter('translate')('admin-home.accept'))
					.cancel($filter('translate')('admin-home.cancel'));
				$mdDialog.show(confirm).then(function() {
					User.acceptAsServiceProvider(user).then(loadRequestForServiceProvider);
				});
			}
		}
	});
	
	
})();