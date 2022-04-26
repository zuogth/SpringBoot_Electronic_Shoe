app.controller('ReceiptController',['$scope','ReceiptService',function ($scope,ReceiptService){

    //custom show div
    $scope.listPr=true;
    $scope.listCh=false;
    $scope.formAdd=false;

    //object
    $scope.receipts=[];
    $scope.receipt={};
    $scope.receiptDetails=[];
    $scope.search={};
    $scope.productDetails=[]
    $scope.brandId='';
    $scope.productDetailSelecteds=[];
    $scope.receiptRequest={};
    $scope.receiptRequest.productDetails=[];
    $scope.productDetailsArr=[];
    $scope.quantity=0;
    $scope.totalMoneyStr='';

    $scope.products=[];
    $scope.colors=[];
    $scope.sizes=[];

    //page
    $scope.totalItems=0;
    $scope.currentPage=1;
    $scope.totalItemChs=0;
    $scope.currentPageCh=1;
    init();

    function init(){
        $scope.listPr=true;
        $scope.listCh=false;
        $scope.formAdd=false;
        $scope.currentPageCh=1;
        $scope.brandId='';
        $scope.productDetailSelecteds=[];
        $scope.receiptRequest={};
        $scope.receiptRequest.productDetails=[];
        $scope.productDetailsArr=[];
        $scope.quantity=0;
        $scope.totalMoneyStr='';
        return ReceiptService.getAll($scope.currentPage).then(function (data){
            $scope.receipts=data.data.receipts;
            $scope.totalItems=data.data.totalItems;
        },function (error){
            showErr(error);
        })
    }

    $scope.pageChanged=function (){
        init();
    }

    $scope.addPr=function (){
        $scope.listPr=false;
        $scope.listCh=false;
        $scope.formAdd=true;
        $scope.receipt.username=$("a#fullNameUser").html();
        $scope.receipt.createdDate=new Date();
    }

    $scope.resetSearch=function (){
        $scope.search={};
        $scope.productDetails=[];
    }
    $scope.getProductByBrand=function (){
        $scope.productDetailSelecteds=[];
        return ReceiptService.getProductByBrand($scope.brandId).then(function (data){
            $scope.products=data.data.products;
            $scope.colors=data.data.colors;
            $scope.sizes=data.data.sizes;
        },function (error){
            showErr(error);
        })
    }
    $scope.searchProduct=function (){
        return ReceiptService.getProductDetails($scope.search).then(function (data){
            $scope.productDetails=data.data;
        },function (error){
            showErr(error);
        })
    }

    $scope.selectProductDetail=function (){
        $("input.selectPD").each(function (){
            if($(this).prop("checked")){
                let index=$(this).attr("pdIndex");
                if(!$scope.isChecked($scope.productDetails[index].id)){
                    $scope.productDetailSelecteds.push($scope.productDetails[index]);
                }
            }
        })
    }

    $scope.isChecked=function (id){
        let flag=false;
        $scope.productDetailSelecteds.forEach(function (e){
            if (e.id==id){
                flag=true;
            }
        })
        return flag;
    }

    $scope.deletePD=function (index){
        $scope.productDetailSelecteds.splice(index,1);
        $scope.productDetailsArr.splice(index,1);
        $scope.totalQuantityAndMoney();
    }

    $scope.process=function (){
        $scope.receiptRequest.brandId=$scope.brandId;
        $scope.productDetailSelecteds.forEach((e,i)=>$scope.productDetailsArr[i].id=e.id);
        $scope.receiptRequest.productDetails=[];
        $scope.productDetailsArr.forEach(e=>$scope.receiptRequest.productDetails.push(e));
        return ReceiptService.process($scope.receiptRequest).then(function (data){
            $scope.message={content:data.message,show:true};
            init();
        },function (error){
            showErr(error);
        })
    }

    $scope.totalQuantityAndMoney=function (){
        $scope.quantity=0;
        $scope.receiptRequest.totalMoney=0;
        $scope.productDetailsArr.forEach((e,i)=>{
            $scope.quantity+=e.quantity?e.quantity:0;
            $scope.receiptRequest.totalMoney+=e.quantity?e.quantity*$scope.productDetailSelecteds[i].price:0;
        });
        $scope.totalMoneyStr=($scope.receiptRequest.totalMoney).toLocaleString('it-IT');
    }

    $scope.getById=function (id){
        $scope.listPr=false;
        $scope.listCh=true;
        $scope.formAdd=false;
        $scope.receipt.id=id;
        return ReceiptService.getById(id,$scope.currentPageCh).then(function (data){
            $scope.receiptDetails=data.data.receiptDetails;
            $scope.totalItemChs=data.data.totalItemChs;
        },function (error){
            showErr(error);
        })
    }

    $scope.pageChangedCh=function (){
        $scope.getById($scope.receipt.id);
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