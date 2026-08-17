app.directive('fileModel', function() {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
            element.bind('change', function() {
                scope.$apply(function() {
                    scope.$eval(attrs.fileModel + ' = element[0].files[0]', { element: element });
                    if (attrs.fileChange) {
                        scope.$eval(attrs.fileChange);
                    }
                });
            });
        }
    };
});