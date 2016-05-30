(function() {
	
	angular.module('bruno').component('brToolbar', {
		templateUrl: 'app/component/toolbar/toolbar.html',
		controller: function(User, $state, $window, $cookies, $translate, $http) {
			var ctrl = this;
			
			ctrl.$state = $state;
			
			User.current().then(function(user) {
				ctrl.user = user;
				ctrl.currentLang = user.locale;
				
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
			
			ctrl.changeLang = function(lang) {
				$http.get('/api/translations?lang='+lang).then(function() {
					ctrl.currentLang = lang;
					$translate.use(lang);
				});
			}
		}
	});
	
	
})();