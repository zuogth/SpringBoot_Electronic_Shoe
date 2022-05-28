app.controller('ReceiptController',['$scope','ReceiptService',function ($scope,ReceiptService){

    //custom show div
    $scope.listPr=true;
    $scope.listCh=false;
    $scope.formAdd=false;
    $scope.btnAdd=false;
    $scope.btnEdit = false;

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
    $scope.typeFile = "pdf";
    $scope.receiptId='';
    $scope.dataBase64='';

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
        $scope.btnAdd=false;
        $scope.btnEdit = false;
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
        $scope.btnAdd=true;
        $scope.btnEdit = false;
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

    $scope.process=function (type){
        $scope.receiptRequest.brandId=$scope.brandId;
        $scope.receiptRequest.productDetails=$scope.productDetailSelecteds;
        return ReceiptService.process($scope.receiptRequest,type).then(function (data){
            $scope.message={content:data.data.message,show:true};
            init();
        },function (error){
            showErr(error);
        })
    }

    $scope.getByIdForEdit=function (id){
        $scope.listPr=false;
        $scope.listCh=false;
        $scope.formAdd=true;
        $scope.btnAdd=false;
        $scope.btnEdit = true;
        return ReceiptService.getDetailsById(id).then(function (_data){
            $scope.receipt = _data.data.receipt;
            $scope.productDetailSelecteds = _data.data.productDetails;
            $scope.quantity = $scope.receipt.quantity;
            $scope.totalMoneyStr=($scope.receipt.totalPrice).toLocaleString('it-IT');
            $scope.receipt.createdDate = new Date($scope.receipt.createdDate);
            $scope.brandId = $scope.receipt.brandId.toString();
            $scope.receiptRequest.totalMoney = $scope.receipt.totalPrice;
            $scope.receiptRequest.id = $scope.receipt.id;
            $scope.products=_data.data.products;
            $scope.colors=_data.data.colors;
            $scope.sizes=_data.data.sizes;
        },function (err){
            showErr(err);
        })
    }

    $scope.totalQuantityAndMoney=function (){
        $scope.quantity=0;
        $scope.receiptRequest.totalMoney=0;
        $scope.productDetailSelecteds.forEach((e,i)=>{
            $scope.quantity+=e.receipt?e.receipt:0;
            $scope.receiptRequest.totalMoney+=e.receipt?e.receipt*e.price:0;
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

    $scope.exportReceipt=function (){
        return ReceiptService.exportReceipt($scope.receiptId,$scope.typeFile).then(function (_data){
            console.log(_data.data.pdf);
            downloadPDF(_data.data.pdf,$scope.typeFile);
        },function (error){
            showErr(error)
        })
    }

    $scope.setReceiptId=function (id){$scope.receiptId = id;}

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

var isIE = /*@cc_on!@*/false || !!document.documentMode;
var isEdge = !isIE && !!window.StyleMedia;
var isChrome = !!window.chrome && (!!window.chrome.webstore || !!window.chrome.runtime);

window.downloadPDF = function downloadPDF(str,type) {
    var base64String = str;

    if(isIE || isEdge){
        var byteCharacters = atob(base64String);
        var byteNumbers = new Array(byteCharacters.length);
        for (var i = 0; i < byteCharacters.length; i++) {
            byteNumbers[i] = byteCharacters.charCodeAt(i);
        }
        var byteArray = new Uint8Array(byteNumbers);
        var blob = new Blob([byteArray], {type: 'application/pdf'});
        window.navigator.msSaveOrOpenBlob(blob, "Sample.pdf");
    }else{
        var downloadLink = document.getElementById('download'+type);
        downloadLink.href = 'data:application/octet-stream;base64,' + base64String;
        downloadLink.click();
    }
}