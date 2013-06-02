angular.module('order-groups', [])
.controller('OrderGroupsController', function($scope, $http) {

  $scope.tables = [{
      label: 'Tivoli',
      place: 'http://tivoli.demo',
      items: [
        {id:1,who:'Witek',what:'Kurak',price:12.50},
        {id:2,who:'Łukasz',what:'Makaron',price:10.50},
        {id:3,who:'Zbyszek',what:'Makaron',price:10.50},
        {id:4,who:'Krzysiek',what:'Rodzynki',price:5.60}
      ]
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
