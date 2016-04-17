(function() {
	
	angular.module('bruno').component('brFileUpload', {
		template: '<input class="ng-hide" id="input-file-id" multiple type="file" accept="video/mp4,video/x-m4v,video/*,image/jpeg,image/png">'+
					'<md-button class="md-primary md-raised"><label style="display:block" for="input-file-id" translate>file-upload.select-images-or-video</label></md-button>'+
					'<ul ng-if="!$ctrl.hideFiles"><li ng-repeat="file in $ctrl.files">{{file.file.name}}: {{file.status}} <md-button ng-click="$ctrl.removeFile($index)"><span translate>file-upload.remove-file</span></md-button></li></ul>',
		bindings: {
			uploading: '=',
			onAddNewFile: '&',
			onDeleteNewFile: '&',
			hideFiles: '='
		},
		controller: function($element, $scope) {
			var ctrl = this;
			
			ctrl.files = [];
			
			ctrl.removeFile = function(index) {
				var file = ctrl.files[index];
				try {
					file.xhr.onreadystatechange = null;
					file.xhr.abort();
				} catch(e) {}
				ctrl.files.splice(index, 1);
				if(file.hash) {
					ctrl.onDeleteNewFile()(file.hash);
				}
				updateUploadingStatus();
			};
			
			ctrl.$postLink = function() {
				$element.find("input")[0].addEventListener('change', function(){
					var elem = this;
					$scope.$applyAsync(function() {
						for(var i = 0; i<elem.files.length; i++){
							var file =  elem.files[i];
							var fileWrapper = {file: file, status: 'UPLOADING'};
							ctrl.files.push(fileWrapper);
							updateUploadingStatus();
							uploadFile(fileWrapper);
						}
					});
				});
			};
			
			function updateUploadingStatus() {
				for(var i = 0; i < ctrl.files; i++) {
					if(ctrl.files[i].status === 'UPLOADING') {
						ctrl.uploading = true;
						return;
					}
				}
				ctrl.uploading = false;
			}
			
			function uploadFile(fileWrapper) {
			    var xhr = new XMLHttpRequest();
			    var fd = new FormData();
			    fileWrapper.xhr = xhr;
			    xhr.open("POST", 'api/file', true);
			    xhr.onreadystatechange = function() {
			    	$scope.$applyAsync(function() {
			    		if (xhr.readyState == 4 && xhr.status == 200) {
			    			if(fileWrapper.status === 'UPLOADING') {
			    				fileWrapper.status = 'UPLOADED';
				    			fileWrapper.hash = JSON.parse(xhr.responseText).hash;
				    			ctrl.onAddNewFile()(fileWrapper.hash);
			    			} 
				        } else {
				        	fileWrapper.status = 'ERROR';
				        }
			    		updateUploadingStatus()
			    	})
			    };
			    fd.append("file", fileWrapper.file);
			    xhr.send(fd);
			}
		}
	});
	
})();