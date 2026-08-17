app.service('EmployeeService', function($http) {

    var baseUrl = 'http://localhost:8080/api/employees';

    this.getAll = function() {
        return $http.get(baseUrl);
    };

    this.add = function(emp) {
        return $http.post(baseUrl, emp);
    };

    this.update = function(id, emp) {
        return $http.put(baseUrl + '/' + id, emp);
    };

    this.delete = function(id) {
        return $http.delete(baseUrl + '/' + id);
    };

    this.uploadPhoto = function(id, file) {
        var formData = new FormData();
        formData.append('file', file);

        return $http.post(baseUrl + '/' + id + '/photo', formData, {
            transformRequest: angular.identity,
            headers: { 'Content-Type': undefined }
        });
    };
});