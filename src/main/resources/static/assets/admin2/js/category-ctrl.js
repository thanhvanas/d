app.controller("category-ctrl", function($scope, $http) {
	$scope.items = [];
	$scope.form = {};

	$scope.initialize = function() {


		$http.get("/rest/categories").then(resp => {
			$scope.items = resp.data;
		});
		$scope.reset(); //để có hình mây lyc1 mới đầu
		$scope.loadCurrentUser();
	}
	$scope.create = function() {
		var item = angular.copy($scope.form);



		$http.post(`/rest/categories`, item).then(resp => {
			$scope.items.push(resp.data);
			$scope.reset();

			alert("Thêm mới thành công!");
		}).catch(error => {
			alert("Lỗi thêm mới !");
			console.log("Error", error);
		});
	}
$scope.loadCurrentUser = function() {
    $http.get("/rest/accounts/current-account").then(resp => {
        $scope.account = resp.data;
    }); 
};

	$scope.edit = function(item) {
		$scope.form = angular.copy(item);

	}

	$scope.reset = function() {
		$scope.form = {
			available: true,
		}
	}

	$scope.update = function() {
		var item = angular.copy($scope.form);

		$http.put(`/rest/categories/${item.id}`, item).then(resp => {
			var index = $scope.items.findIndex(p => p.id == item.id);
			$scope.items[index] = item;
			alert("Cập nhật thành công!");
		})
			.catch(error => {
				alert("Lỗi cập nhật !");
				console.log("Error", error);
			});
	}

	$scope.delete = function(item) {
		var itemCopy = angular.copy(item);

		$http({
			method: 'DELETE',
			url: '/rest/categories/' + itemCopy.id
		}).then(function successCallback(response) {
			var index = $scope.items.findIndex(p => p.id === itemCopy.id);
			if (index !== -1) {
				$scope.items.splice(index, 1);
				$scope.reset();
				alert("Xóa sản phẩm thành công!");
			}
		}, function errorCallback(error) {
			// Nếu không xóa được, thực hiện cập nhật trạng thái
			itemCopy.available = false;
			$http({
				method: 'PUT',
				url: `/rest/categories/${itemCopy.id}`,
				data: itemCopy
			}).then(function successCallback(response) {
				var index = $scope.items.findIndex(p => p.id === itemCopy.id);
				if (index !== -1) {
					$scope.items[index] = itemCopy;
					alert("Cập nhật trạng thái thành công!");
				}
			});
		});
	};





	$scope.pager = {
		page: 0,
		size: 4,
		get items() {
			if (this.page < 0) {
				this.last();
			}
			if (this.page >= this.count) {
				this.first();
			}
			var start = this.page * this.size;
			return $scope.items.slice(start, start + this.size)
		},
		get count() {
			return Math.ceil(1.0 * $scope.items.length / this.size);
		},
		first() {
			this.page = 0;
		},
		last() {
			this.page = this.count - 1;
		},
		next() {
			this.page++;
		},
		prev() {
			this.page--;
		}
	}
	$scope.initialize();
});