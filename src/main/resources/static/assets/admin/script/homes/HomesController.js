app.controller('HomesController',['$scope','HomesService',function ($scope,HomesService){

    $scope.message={};
    $scope.datasetsCh=[];
    $scope.labelsCh=[];
    $scope.year=2022;
    $scope.datasetsB=[];
    $scope.labelsB=[];
    $scope.countBill=0;
    $scope.countUser=0;

    $scope.myChart;
    $scope.salesChart;

    init();
    getChart();

    function init(){
        return HomesService.getAll().then(function (_data){
            $scope.datasetsB=_data.data.chart;
            $scope.labelsB=_data.data.labels;
            $scope.countBill=_data.data.countBill;
            $scope.countUser=_data.data.countUser;
            chartBar();
        },function (err){
            showErr(err);
        })
    }

    function getChart(){
        return HomesService.getChart($scope.year).then(function (_data){
            $scope.datasetsCh=_data.data.chart;
            $scope.labelsCh=_data.data.labels;
            chartLine();
        },function (err){
            showErr(err);
        })
    }

    $scope.getYear=function (op){
        let date=new Date(Date.now());
        if(op=='next'){
            if($scope.year==date.getUTCFullYear())return;
            $scope.year=$scope.year+1;
        }else {
            $scope.year=$scope.year-1;
        }
        getChart();
    }

    function showErr(error){
        $scope.message = {content: error.data.message, show: true};
        if(error.status==401){
            window.location.href="/login";
        }
    }

    function chartLine(){
        if($scope.myChart)$scope.myChart.destroy();
        const data = {
            labels: $scope.labelsCh,
            datasets: $scope.datasetsCh
        };

        const config = {
            type: 'line',
            data: data,
            options: {
                responsive: true
            }
        };
        $scope.myChart = new Chart(
            document.getElementById('myChart'),
            config
        );
    }

    function chartBar(){
        if($scope.salesChart)$scope.salesChart.destroy();
        // eslint-disable-next-line no-unused-vars
        $scope.salesChart = new Chart($('#sales-chart'), {
            type: 'bar',
            data: {
                labels: $scope.labelsB,
                datasets: $scope.datasetsB
            },
            options: {
                responsive: true,
                interaction: {
                    mode: 'index',
                    axis: 'y'
                }
            }
        })
    }

}])