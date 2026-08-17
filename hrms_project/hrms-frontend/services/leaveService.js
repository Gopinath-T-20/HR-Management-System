app.service('LeaveService', function($http) {

    var baseUrl = 'http://localhost:8080/api/leaves';

    this.applyLeave = function(empId, leaveData) {
        // Backend "/apply" endpoint employee-ah nested object-ah expect pandradhu
        leaveData.employee = { empId: empId };
        return $http.post(baseUrl + '/apply', leaveData);
    };

    this.getLeavesByEmployee = function(empId) {
        return $http.get(baseUrl + '/employee/' + empId);
    };

    this.getAllLeaves = function() {
        return $http.get(baseUrl + '/all');
    };

    this.updateStatus = function(leaveId, status) {
        return $http.put(baseUrl + '/status/' + leaveId, { status: status });
    };
});