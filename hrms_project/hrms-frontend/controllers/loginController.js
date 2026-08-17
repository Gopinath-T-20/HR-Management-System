app.controller('LoginController', function($scope, $location, LoginService) {

    $scope.credentials = {};
    $scope.errorMsg = '';
    $scope.selectedRole = 'HR';

    $scope.selectRole = function(role) {
        $scope.selectedRole = role;
        $scope.errorMsg = '';
    };

    $scope.doLogin = function() {
        LoginService.login($scope.credentials).then(function(response) {

            var data = response.data;

            // Tab-la select pannina role, DB-la irukra actual role match aaguthaanu check pannuROM
            if (data.role !== $scope.selectedRole) {
                $scope.errorMsg = 'This account is not a ' + $scope.selectedRole + ' account';
                return;
            }

            $scope.errorMsg = '';

            localStorage.setItem('authToken', data.token);
            localStorage.setItem('loggedInEmployee', JSON.stringify(data));

            if (data.role === 'HR') {
                $location.path('/employees');
            } else {
                $location.path('/my-profile');
            }

        }, function(error) {
            $scope.errorMsg = 'Invalid username or password';
            console.log('Login error:', error);
        });
    };
});