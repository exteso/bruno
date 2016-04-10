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
            },
            
            findById: function(id) {
            	return $http.get('api/user/'+id).then(function(data) {
                	return data.data;
                });
            },
            
            confirmRequestAsServiceProvider: function() {
            	return $http.post('api/user/request-as-service-provider');
            },
            
            findAll : function() {
            	return $http.get('api/admin/user-list').then(function(data) {
                	return data.data;
                });
            }
		};
	});
	
	
})();