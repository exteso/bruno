(function() {
	
	angular.module('bruno').factory('User', function ($http) {
		
		var cached = null;
		
		return {
            current: function () {
                var u = $http.get('api/user').then(function(data) {
                	return data.data;
                });
                cached = u;
                return u;
            },

            currentCachedUser: function () {
                if (cached == null) {
                    cached = this.current();
                }
                return cached;
            }
		};
	});
	
	
})();