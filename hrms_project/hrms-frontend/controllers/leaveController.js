app.controller('LeaveController', function($scope, LeaveService) {

    $scope.loggedInEmployee = JSON.parse(localStorage.getItem('loggedInEmployee'));
    $scope.newLeave = {};
    $scope.myLeaves = [];
    $scope.allLeaves = [];
    $scope.message = '';

    // Employee: To Apply leave
    // Employee: Apply for Leave
$scope.applyLeave = function() {

    var today = new Date();
    today.setHours(0,0,0,0);

    var fromDate = new Date($scope.newLeave.fromDate);
    var toDate = new Date($scope.newLeave.toDate);

    // Validation 1: From date cannot be in the past
    if (fromDate < today) {
        $scope.message = 'From Date cannot be before today!';
        return;
    }

    // Validation 2: To date cannot be before From date
    if (toDate < fromDate) {
        $scope.message = 'To Date cannot be before From Date!';
        return;
    }

    // Validation 3: Reason cannot be empty
    if (!$scope.newLeave.reason || $scope.newLeave.reason.trim() === '') {
        $scope.message = 'Reason is required!';
        return;
    }

    LeaveService.applyLeave($scope.loggedInEmployee.empId, $scope.newLeave).then(function(response) {
        $scope.message = 'Leave applied successfully!';
        $scope.newLeave = {};
        $scope.loadMyLeaves();
    }, function(error) {
        $scope.message = 'Error applying leave';
        console.log(error);
    });
};

    // Employee can see the history of leave 
    $scope.loadMyLeaves = function() {
        LeaveService.getLeavesByEmployee($scope.loggedInEmployee.empId).then(function(response) {
            $scope.myLeaves = response.data;
        }, function(error) {
            console.log('Error loading leaves:', error);
        });
    };

    // HR can load leave request of employees
    $scope.loadAllLeaves = function() {
        LeaveService.getAllLeaves().then(function(response) {
            $scope.allLeaves = response.data;
        }, function(error) {
            console.log('Error loading all leaves:', error);
        });
    };

    // HR: Approve/Reject 
    $scope.updateStatus = function(leaveId, status) {
        LeaveService.updateStatus(leaveId, status).then(function(response) {
            $scope.loadAllLeaves();
        }, function(error) {
            console.log('Error updating status:', error);
        });
    };

    if ($scope.loggedInEmployee.role === 'HR') {
        $scope.loadAllLeaves();
    } else {
        $scope.loadMyLeaves();
    }
});