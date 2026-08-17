var app = angular.module('hrmsApp', ['ngRoute']);

app.config(function($routeProvider) {
    $routeProvider
        .when('/login', {
            templateUrl: 'views/login.html',
            controller: 'LoginController'
        })
        .when('/employees', {
            templateUrl: 'views/employee-list.html',
            controller: 'EmployeeController'
        })
        .when('/add-employee', {
            templateUrl: 'views/employee-add.html',
            controller: 'EmployeeController'
        })
        .when('/attendance', {
            templateUrl: 'views/attendance.html',
            controller: 'AttendanceController'
        })
        .when('/leave', {
            templateUrl: 'views/leave.html',
            controller: 'LeaveController'
        })
        .when('/my-profile', {
            templateUrl: 'views/my-profile.html',
            controller: 'ProfileController'
        })
        .otherwise({
            redirectTo: '/login'
        });
});

// Ella $http requests-lyum JWT token-ah automatic-a attach pandra interceptor
app.factory('AuthInterceptor', function() {
    return {
        request: function(config) {
            var token = localStorage.getItem('authToken');
            if (token) {
                config.headers.Authorization = 'Bearer ' + token;
            }
            return config;
        },
        responseError: function(rejection) {
            // Token expire aagiruntha illa invalid-ah irundha, login-ku thirumba anuppuROM
            if (rejection.status === 401) {
                localStorage.removeItem('authToken');
                localStorage.removeItem('loggedInEmployee');
                window.location.hash = '#!/login';
            }
            return rejection;
        }
    };
});

app.config(function($httpProvider) {
    $httpProvider.interceptors.push('AuthInterceptor');
});

app.run(function($rootScope, $location) {

    var hrOnlyRoutes = ['/employees', '/add-employee'];

    $rootScope.$on('$routeChangeStart', function(event, next) {

        var loggedInEmployee = JSON.parse(localStorage.getItem('loggedInEmployee'));
        var currentPath = $location.path();

        // Login pannalaina, login page-ku mattum povom
        if (!loggedInEmployee && currentPath !== '/login') {
            $location.path('/login');
            return;
        }

        // Login pannirundha, Employee role oda HR-mattum pages-ku poga try pannuNganna block pannuROM
        if (loggedInEmployee && loggedInEmployee.role !== 'HR' && hrOnlyRoutes.indexOf(currentPath) !== -1) {
            $location.path('/my-profile');
        }
    });
});