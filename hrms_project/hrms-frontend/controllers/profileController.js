app.controller('ProfileController', function($scope, EmployeeService, AttendanceService, LeaveService, PayslipService) {

    var loggedIn = JSON.parse(localStorage.getItem('loggedInEmployee'));
    $scope.empId = loggedIn.empId;

    $scope.employee = {};
    $scope.attendanceList = [];
    $scope.leaveList = [];
    $scope.payslipList = [];

    $scope.leaveForm = {};
    $scope.leaveMsg = '';
    $scope.selectedProfilePhoto = null;

    // Employee full details load pannuROM
    function loadEmployeeDetails() {
        EmployeeService.getAll().then(function(response) {
            var all = response.data;
            for (var i = 0; i < all.length; i++) {
                if (all[i].empId === $scope.empId) {
                    $scope.employee = all[i];
                    break;
                }
            }
        });
    }
    loadEmployeeDetails();

    // Attendance history
    AttendanceService.getAttendanceByEmployee($scope.empId).then(function(response) {
        $scope.attendanceList = response.data;
    });

    // Leave history
    function loadLeaves() {
        LeaveService.getLeavesByEmployee($scope.empId).then(function(response) {
            $scope.leaveList = response.data;
        });
    }
    loadLeaves();

    // Payslips
    PayslipService.getPayslipsByEmployee($scope.empId).then(function(response) {
        $scope.payslipList = response.data;
    });

    // Attendance mark pandradhu
    $scope.markAttendance = function() {
        AttendanceService.markAttendance($scope.empId).then(function(response) {
            alert(response.data);
            AttendanceService.getAttendanceByEmployee($scope.empId).then(function(res) {
                $scope.attendanceList = res.data;
            });
        }, function(error) {
            alert(error.data && error.data.error ? error.data.error : 'Error marking attendance');
        });
    };

    // Leave apply pandradhu
    $scope.applyLeave = function() {
        $scope.leaveMsg = '';
        LeaveService.applyLeave($scope.empId, $scope.leaveForm).then(function(response) {
            $scope.leaveMsg = 'Leave applied successfully!';
            $scope.leaveForm = {};
            loadLeaves();
        }, function(error) {
            $scope.leaveMsg = error.data && error.data.error ? error.data.error : 'Error applying leave';
        });
    };

    // Payslip PDF download pandradhu
    $scope.downloadPayslip = function(payslip) {
        PayslipService.downloadPayslip(payslip.payslipId).then(function(response) {
            var blob = new Blob([response.data], { type: 'application/pdf' });
            var url = window.URL.createObjectURL(blob);
            var a = document.createElement('a');
            a.href = url;
            a.download = 'payslip_' + payslip.month + '_' + payslip.year + '.pdf';
            document.body.appendChild(a);
            a.click();
            document.body.removeChild(a);
            window.URL.revokeObjectURL(url);
        }, function(error) {
            alert('Failed to download payslip');
        });
    };

    // Profile photo change pandradhu
    $scope.selectProfilePhoto = function(file) {
        if (!file) return;
        EmployeeService.uploadPhoto($scope.empId, file).then(function(response) {
            alert('Photo updated successfully!');
            $scope.employee.photoUrl = response.data.photoUrl;

            // localStorage-la um update pannanum (navbar photo refresh aaga)
            loggedIn.photoUrl = response.data.photoUrl;
            localStorage.setItem('loggedInEmployee', JSON.stringify(loggedIn));
        }, function(error) {
            console.log('Error uploading photo:', error);
            alert('Error uploading photo');
        });
    };
});