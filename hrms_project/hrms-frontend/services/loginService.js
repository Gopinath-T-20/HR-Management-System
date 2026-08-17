app.service('LoginService', function($http) {

    var baseUrl = 'http://localhost:8080/api/auth/login';

    this.login = function(credentials) {
        return $http.post(baseUrl, credentials);
    };
});