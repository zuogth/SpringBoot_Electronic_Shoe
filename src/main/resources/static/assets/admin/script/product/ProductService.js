'use strict';
app.service('ProductService',['BaseServices',function (BaseServices){
    let urls={
        getAll:"/products",
        getDetailById:"/products/productDetails",
        lock:"/products/status",
        process:"/products"
    };

    let urlChrs={
        process:"/productdetails",
        processSize:"/productdetails/sizes",
        lock:"/productdetails/status"
    }

    //product parent
    function getAll(brandSlug,page){
        return BaseServices.callAPI(urls.getAll+"?page="+page+""+brandSlug,"GET");
    }
    function getDetailById(id,page){
        return BaseServices.callAPI(urls.getDetailById+"/"+id+"?page="+page,"GET");
    }
    function getById(id){
        return BaseServices.callAPI(urls.process+"/"+id,"GET")
    }
    function process(data){
        if(data.id){
            return BaseServices.callAPI(urls.process,"PUT",data);
        }
        return BaseServices.callAPI(urls.process,"POST",data);
    }
    function deletePr(id){
        return BaseServices.callAPI(urls.process+"/"+id,"DELETE");
    }

    //product detail
    function getDetailByIdDetail(productId,colorId){
        return BaseServices.callAPI(urlChrs.process+"?productId="+productId+"&colorId="+colorId,"GET");
    }
    function getSizes(data){
        return BaseServices.callAPI(urlChrs.processSize+"?productId="+data.productId+"&colorId="+data.colorId,"GET");
    }

    function processSize(data,flag){
        if(flag==0){
            return BaseServices.callAPI(urlChrs.processSize+"?sizeId="+data.sizeChange,"PUT",data);
        }
        return BaseServices.callAPI(urlChrs.processSize+"?sizeId="+data.sizeChange,"POST",data);
    }

    function deleteCh(id,flag){
        return BaseServices.callAPI(urlChrs.process+"/"+id+"?flag="+flag,"DELETE");
    }

    function lockProd(flag,id,data){
        if(flag=='pr'){
            return BaseServices.callAPI(urls.lock+"/"+id+"?status="+data,"PUT");
        }
        return BaseServices.callAPI(urlChrs.lock+"/"+id+"?status="+data,"PUT");
    }
    return {
        getAll: getAll,
        getDetailById: getDetailById,
        getById: getById,
        process: process,
        getDetailByIdDetail:getDetailByIdDetail,
        getSizes:getSizes,
        processSize:processSize,
        lockProd:lockProd,
        deleteCh:deleteCh,
        deletePr:deletePr
    };
}])