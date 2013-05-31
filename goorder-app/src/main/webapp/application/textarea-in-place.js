angular.module('textarea-in-place', ['ngSanitize'])
.factory('extenralizeLinks', function(linkyFilter) {
  //TODO: unit test
  return function(text) {
    return linkyFilter(text).replace(/<a\s+href/g, '<a target=_blank href');
  };
})
.directive('goTextareaInPlace', function(extenralizeLinks) {
  return {
    link: function($scope, elem) {
      var edit = elem.find('textarea');
      var render = elem.find('.render');
      var editing;

      doEdit(false);

      function doEdit(enabled) {
        editing = enabled;
        enabled && render.hide() || render.show();
        enabled && edit.show().focus() || edit.hide();
        doRender();
      }

      function doRender() {
        if (editing) {
          return;
        }
        render.empty();
        var content = edit.val() || '';
        angular.forEach(content.split('\n'), function(line, idx) {
          line = line.trim();
          render.append(extenralizeLinks(line));
          render.append('<br>');
        });
      }

      $scope.$watch(function() {
        return edit.val();
      }, doRender);

      render.click(function() {
        $scope.$apply(function() {
          doEdit(true);
        });
      });

      edit.focusout(function() {
        $scope.$apply(function() {
          doEdit(false);
        });
      });

      edit.keyup(function(e) {
        $scope.$apply(function() {
          e.keyCode === 27 && doEdit(false); //27 is ESC
        });
      });

    }
  };
});