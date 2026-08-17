app.service('DepartmentService', function($http) {

    var baseUrl = 'http://localhost:8080/api/departments';

    this.getAll = function() {
        return $http.get(baseUrl);
    };

    this.add = function(dept) {
        return $http.post(baseUrl, dept);
    };
});