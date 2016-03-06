(function() {
	angular.module('bruno').component('brShowUsername', {
		template: '{{$ctrl.user.provider}}:{{$ctrl.user.username}}',
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