'use strict';
app.service('ProductService',['BaseServices',function (BaseServices){
    let urls={
        getAll:"/product",
        getDetailById:"/product",
        getById:"/product/edit",
        update:"/product/update",
        insert:"/product/insert",
        lock:"/product/lock"
    };

    let urlChrs={
        getById:"/productdetail/edit",
        insert: "/productdetail/insert",
        updateSize:"/productdetail/update/size",
        getSizes:"/productdetail/sizes",
        insertSize:"/productdetail/insert/size",
        lock:"/productdetail/lock"
    }

    //product parent
    function getAll(brand_id,page){
        return BaseServices.callAPI(urls.getAll+"?page="+page+""+brand_id,"GET");
    }
    function getDetailById(id,page){
        return BaseServices.callAPI(urls.getDetailById+"/"+id+"?page="+page,"GET");
    }
    function getById(id){
        return BaseServices.callAPI(urls.getById+"/"+id,"GET")
    }
    function process(data){
        if(data.id){
            return BaseServices.callAPI(urls.update,"PUT",data);
        }
        return BaseServices.callAPI(urls.insert,"POST",data);
    }

    //product detail
    function getDetailByIdDetail(id){
        return BaseServices.callAPI(urlChrs.getById+"/"+id,"GET");
    }
    function getSizes(data){
        return BaseServices.callAPI(urlChrs.getSizes+"?productId="+data.productId+"&colorId="+data.colorId,"GET");
    }

    function processSize(data,flag){
        if(flag==0){
            return BaseServices.callAPI(urlChrs.updateSize+"?sizeId="+data.sizeChange,"PUT",data);
        }
        return BaseServices.callAPI(urlChrs.insertSize+"?sizeId="+data.sizeChange,"POST",data);
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
        lockProd:lockProd
    };
}])