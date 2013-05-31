angular.module('order-groups', [])
.controller('OrderGroupsController', function($scope, $http) {

  $scope.tables = [{
      label: 'Tivoli',
      place: 'http://tivoli.demo'
    },{
      label: 'Chinol',
      place: 'http://chinol.demo'
  }];

  $scope.table = {};

  $scope.isActive = function(table) {
    return $scope.table === table;
  };

  $scope.setActive = function(table) {
    $scope.table = table;
  };
});
