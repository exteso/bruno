(function() {
	angular.module('bruno').component('brShowUsername', {
		template: '{{$ctrl.user.companyName}} ({{$ctrl.user.companyAddress}})',
		bindings: {
			userId:'='
		},
		controller: function(User) {
			var ctrl = this;
			
			User.findById(ctrl.userId).then(function(user) {
				ctrl.user = user;
			});
		}
	});
})();