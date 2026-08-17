app.controller('EmployeeController', function($scope, EmployeeService, DepartmentService, PayslipService) {
   
    $scope.employees = [];
    $scope.departments = [];
    $scope.newEmp = {};
    $scope.newEmpPhoto = null;
    $scope.selectedProfilePhoto = null;
    $scope.loggedInEmployee = JSON.parse(localStorage.getItem('loggedInEmployee'));

    // Payslip generate panradhukku
    $scope.payslipForm = {};
    $scope.payslipEmpId = null;
    $scope.payslipMsg = '';

    // Load all employees
    $scope.loadEmployees = function() {
        EmployeeService.getAll().then(function(response) {
            $scope.employees = response.data;
        }, function(error) {
            console.log('Error loading employees:', error);
        });
    };

    // Load all departments (for dropdown)
    $scope.loadDepartments = function() {
        DepartmentService.getAll().then(function(response) {
            $scope.departments = response.data;
        }, function(error) {
            console.log('Error loading departments:', error);
        });
    };

    // Add new employee
    $scope.addEmployee = function() {
        EmployeeService.add($scope.newEmp).then(function(response) {
            var newEmpId = response.data.empId;

            if ($scope.newEmpPhoto) {
                EmployeeService.uploadPhoto(newEmpId, $scope.newEmpPhoto).then(function() {
                    finishAdd();
                }, function() {
                    alert('Employee added, but photo upload failed.');
                    finishAdd();
                });
            } else {
                finishAdd();
            }
        }, function(error) {
            console.log('Error adding employee:', error);
            var msg = error.data && error.data.error ? error.data.error : 'Error adding employee';
            alert(msg);
        });
    };

    function finishAdd() {
        alert('Employee added successfully!');
        $scope.newEmp = {};
        $scope.newEmpPhoto = null;
        $scope.loadEmployees();
        window.location.href = '#!/employees';
    }

    // Delete employee
    $scope.deleteEmployee = function(id) {
        if (!confirm('Are you sure you want to delete this employee?')) {
            return;
        }
        EmployeeService.delete(id).then(function(response) {
            $scope.loadEmployees();
        }, function(error) {
            console.log('Error deleting employee:', error);
            var msg = (error.data && error.data.error) ? error.data.error : 'Cannot delete this employee — they may have linked attendance, leave or payslip records';
            alert(msg);
        });
    };

    // Change photo (existing employee)
    $scope.selectPhoto = function(file, empId) {
        if (!file) return;
        EmployeeService.uploadPhoto(empId, file).then(function(response) {
            alert('Photo updated successfully!');
            $scope.loadEmployees();
        }, function(error) {
            console.log('Error uploading photo:', error);
            alert('Error uploading photo');
        });
    };

    // Payslip generate form open pandradhu
    $scope.openPayslipForm = function(empId) {
        $scope.payslipEmpId = empId;
        $scope.payslipForm = { allowances: 0, deductions: 0 };
        $scope.payslipMsg = '';
    };

    $scope.closePayslipForm = function() {
        $scope.payslipEmpId = null;
    };

    // Payslip generate pandradhu
    $scope.generatePayslip = function() {
        PayslipService.generatePayslip($scope.payslipEmpId, $scope.payslipForm).then(function(response) {
            $scope.payslipMsg = 'Payslip generated successfully!';
            setTimeout(function() {
                $scope.closePayslipForm();
                $scope.$apply();
            }, 1000);
        }, function(error) {
            $scope.payslipMsg = error.data && error.data.error ? error.data.error : 'Error generating payslip';
        });
    };

    // Load data when controller starts
    $scope.loadEmployees();
    $scope.loadDepartments();
});