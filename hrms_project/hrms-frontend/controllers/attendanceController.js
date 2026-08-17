app.controller('AttendanceController', function($scope, AttendanceService) {

    $scope.loggedInEmployee = JSON.parse(localStorage.getItem('loggedInEmployee'));
    $scope.attendanceList = [];
    $scope.message = '';

    // To mark attendance
    $scope.markAttendance = function() {
        AttendanceService.markAttendance($scope.loggedInEmployee.empId).then(function(response) {
            $scope.message = response.data;
            $scope.loadAttendanceHistory();
        }, function(error) {
            $scope.message = 'Error marking attendance';
            console.log(error);
        });
    };

    // To load Attendance history 
    $scope.loadAttendanceHistory = function() {
        AttendanceService.getAttendanceByEmployee($scope.loggedInEmployee.empId).then(function(response) {
            $scope.attendanceList = response.data;
        }, function(error) {
            console.log('Error loading attendance:', error);
        });
    };

    // PTo show history after the page load 
    $scope.loadAttendanceHistory();
});