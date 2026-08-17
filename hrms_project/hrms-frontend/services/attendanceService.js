app.service('AttendanceService', function($http) {

    var baseUrl = 'http://localhost:8080/api/attendance';

    this.markAttendance = function(empId) {
        return $http.post(baseUrl + '/mark/' + empId);
    };

    this.getAttendanceByEmployee = function(empId) {
        return $http.get(baseUrl + '/employee/' + empId);
    };
});