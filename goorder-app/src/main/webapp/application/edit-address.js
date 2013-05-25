angular.module('edit-address', ['ngSanitize'])
.factory('createExternalLinks', function(linkyFilter) {
  //TODO: unit test
  return function(text) {
    return linkyFilter(text).replace(/<a\s+href/g, '<a target=_blank href');
  }
})
.directive('goEditAddress', function(createExternalLinks) {
  return {
    link: function(scope, elem) {
      var edit = elem.find('textarea');
      var render = elem.find('.render');

      //initial state, from HTML template
      scope.address = edit.html();

      render.click(function() {
        scope.$apply(function() {
          scope.editStart();
        });
      });

      edit.focusout(function() {
        scope.$apply(function() {
          scope.editEnd();
        });
      });

      edit.keyup(function(e) {
        scope.$apply(function() {
          e.keyCode === 27 && scope.editCancel(); //27 is ESC
        });
      });

      scope.$watch('editing', function(editing) {
        if (editing) {
          edit.show().focus(); render.hide();
        } else {
          render.empty();
          var address = [];
          angular.forEach(scope.address.split('\n'), function(line, idx) {
            var first = idx === 0;
            line = line.trim();
            render.append((first ? '<strong>' : '') + createExternalLinks(line) + (first ? '</strong>' : ''));
            render.append('<br>');
            address.push(line);
          });
          scope.address = address.join('\n');
          edit.hide(); render.show();
        }
      });
    }
  };
})
.controller('AddressEditController', function($scope) {
  var addressCache = '';
  $scope.address = '';
  $scope.editing = false;

  $scope.editStart = function() {
    addressCache = $scope.address;
    $scope.editing = true;
  };

  $scope.editEnd = function() {
    $scope.editing = false;
  };

  $scope.editCancel = function() {
    $scope.address = addressCache;
    $scope.editing = false;
  };
});
