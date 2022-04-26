'use strict';
app.service('ReceiptService',['BaseServices',function (BaseServices){
    let urls={
       process:'/receipts'
    }

    function getAll(page){
        return BaseServices.callAPI(urls.process+"?page="+page,'GET');
    }

    function getById(id,page){
        return BaseServices.callAPI(urls.process+"/"+id+"?page="+page,'GET');
    }

    function getProductByBrand(brandId){
        return BaseServices.callAPI(urls.process+"/products/"+brandId,"GET");
    }

    function getProductDetails(search){
        let str="";
        str+=search.productId?"&productId="+search.productId:"";
        str+=search.colorId?"&colorId="+search.colorId:"";
        str+=search.sizeId?"&sizeId="+search.sizeId:"";
        return BaseServices.callAPI(urls.process+"/productDetails?h=1"+str,"GET");
    }

    function process(data){
        return BaseServices.callAPI(urls.process,"POST",data);
    }
    return {
        getAll:getAll,
        getById:getById,
        getProductByBrand:getProductByBrand,
        getProductDetails:getProductDetails,
        process:process
    };
}])