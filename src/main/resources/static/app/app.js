(function() {
	
	var module = angular.module('bruno', ['ui.router', 'ngMaterial']);
	
	
	module.config(function ($stateProvider, $urlRouterProvider) {
		$stateProvider.state('home', {
			url: '/',
			template: '<br-home></br-home>'
		})
		.state('user-home', {
			url: '/user',
			template: '<br-user-home></br-user-home>'
		})
		.state('service-provider', {
			url: '/service-provider',
			template: '<br-service-provider></br-service-provider>'
		})
		.state('login', {
			url: '/login',
			template: '<br-login></br-login>'
		});
		
		$urlRouterProvider.otherwise('/');
	});
	
})();