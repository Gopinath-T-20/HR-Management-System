app.controller('NavController', function($scope, $location) {

    $scope.loggedInEmployee = JSON.parse(localStorage.getItem('loggedInEmployee'));

    $scope.logout = function() {
        localStorage.removeItem('loggedInEmployee');
        localStorage.removeItem('authToken');
        $scope.loggedInEmployee = null;
        $location.path('/login');
    };

    $scope.$on('$routeChangeSuccess', function() {
        $scope.loggedInEmployee = JSON.parse(localStorage.getItem('loggedInEmployee'));
    });

});