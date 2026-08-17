app.service('PayslipService', function($http) {

    var baseUrl = 'http://localhost:8080/api/payslips';

    this.getPayslipsByEmployee = function(empId) {
        return $http.get(baseUrl + '/employee/' + empId);
    };

    this.generatePayslip = function(empId, payslipData) {
        return $http.post(baseUrl + '/generate/' + empId, payslipData);
    };

    // PDF download - blob response venum
    this.downloadPayslip = function(payslipId) {
        return $http.get(baseUrl + '/download/' + payslipId, { responseType: 'arraybuffer' });
    };
});