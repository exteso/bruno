(function() {
	
	var module = angular.module('bruno', ['ui.router', 'ngMaterial', "com.2fdevs.videogular", "ngSanitize"]);
	
	
	module.config(function ($stateProvider, $urlRouterProvider) {
		$stateProvider.state('home', {
			url: '/',
			template: '<br-home></br-home>'
		})
		.state('user-home', {
			url: '/user',
			template: '<br-user-home></br-user-home>'
		})
		.state('user-show-request', {
			url: '/user/:requestId',
			template: '<br-user-show-request request-id="requestId"></br-user-show-request>',
			controller: function($scope, $stateParams) {
				$scope.requestId = $stateParams.requestId;
			}
		})
		.state('register-as-service-provider', {
			url: '/register-as-service-provider',
			template: '<br-register-as-service-provider></br-register-as-service-provider>'
		})
		.state('service-provider', {
			url: '/service-provider',
			template: '<br-service-provider></br-service-provider>'
		})
		.state('service-provider-show-request', {
			url: '/service-provider/:requestId',
			template: '<br-service-provider-show-request request-id="requestId"></br-service-provider-show-request>',
			controller: function($scope, $stateParams) {
				$scope.requestId = $stateParams.requestId;
			}
		})
		.state('admin-home', {
			url: '/admin',
			template: 'admin home placeholder'
		})
		.state('login', {
			url: '/login',
			template: '<br-login></br-login>'
		});
		
		$urlRouterProvider.otherwise('/');
	});
	
})();