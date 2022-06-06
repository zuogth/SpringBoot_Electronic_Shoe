app.controller('BillsController',['$scope','BillsService',function ($scope,BillsService){

    $scope.listPr=true;
    $scope.billDetail=false;

    $scope.message={};
    $scope.bills=[];
    $scope.bill={};
    $scope.billDetails=[];

    $scope.totalMoneyStr='';
    $scope.totalItems=0;
    $scope.currentPage=1;

    init();
    function init(){
        let now=$("section#content").attr("now");
        $scope.listPr=true;
        $scope.billDetail=false;
        return BillsService.getAll($scope.currentPage,now).then(function (data){
            $scope.bills=data.data.bills;
            $scope.totalItems=data.data.totalItems;
        },function (error){
            showErr(error);
        })
    }

    $scope.getById=function (id,index){
        $scope.listPr=false;
        $scope.billDetail=true;
        return BillsService.getById(id).then(function (data){
            $scope.billDetails=data.data;
            $scope.bill=$scope.bills[index];
            $scope.totalMoneyStr=($scope.bill.totalPrice).toLocaleString('it-IT');
        },function (error){
            showErr(error);
        })
    }

    $scope.update=function (index){
        $(".preloader-cus2").removeClass("non-active-loader2");
        return BillsService.update($scope.bills[index]).then(function (data){
            $(".preloader-cus2").addClass("non-active-loader2");
            $scope.message = {content: data.data.message, show: true};
        },function (error){
            showErr(error);
        })
    }

    $scope.updateBill=function (){
        return BillsService.update($scope.bill).then(function (data){
            $scope.message = {content: data.data.message, show: true};
        },function (error){
            showErr(error);
        })
    }

    $scope.exportBill=function (id){
        $(".preloader-cus2").removeClass("non-active-loader2");
        return BillsService.exportBill(id).then(function (_data){
            $(".preloader-cus2").addClass("non-active-loader2");
            downloadPDF(_data.data.pdf,'pdf');
        },function (error){
            showErr(error)
        })
    }

    $scope.pageChanged=function (){
        init();
    }

    $scope.close=function (){
        init();
    }

    function showErr(error){
        $scope.message = {content: error.data.message, show: true};
        if(error.status==401){
            window.location.href="/login";
        }
    }

}])

window.downloadPDF = function downloadPDF(str,type) {
    let base64String = str;
    let downloadLink = document.getElementById('download'+type);
    downloadLink.href = 'data:application/octet-stream;base64,' + base64String;
    downloadLink.click();

}