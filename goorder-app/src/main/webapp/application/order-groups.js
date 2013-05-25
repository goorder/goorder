angular.module('order-groups', [])
.controller('OrderGroupsController', function($scope) {

  $scope.groups = [{
      name: 'Tivoli'
    },{
      name: 'Chinol'
    }];

  $scope.isActive = function(group) {
    return group.name === 'Tivoli';
  }
});
