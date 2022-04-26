'use strict';
app.service('PropertiesService',['BaseServices',function (BaseServices){
    let urls={
       process:'/properties',
        sizeProcess:'/properties/sizes',
        colorProcess:'/properties/colors',
        brandProcess:'/properties/brands'
    }

    function getAll(){
        return BaseServices.callAPI(urls.process,'GET');
    }

    function getPage(page,flag){
        return BaseServices.callAPI(urls.process+"/"+page+"?flag="+flag,"GET");
    }

    function getSizeById(id){
        return BaseServices.callAPI(urls.sizeProcess+"/"+id,'GET');
    }

    function getColorById(id){
        return BaseServices.callAPI(urls.colorProcess+"/"+id,'GET');
    }

    function getBrands(){
        return BaseServices.callAPI(urls.brandProcess,'GET');
    }

    function colorProcess(data){
        return data.id?BaseServices.callAPI(urls.colorProcess,"PUT",data):BaseServices.callAPI(urls.colorProcess,"POST",data);
    }

    function sizeProcess(data){
        return data.id?BaseServices.callAPI(urls.sizeProcess+"/"+data.id+"?size="+data.name,"PUT"):BaseServices.callAPI(urls.sizeProcess+"?size="+data.name,"POST");
    }

    return {
        getAll:getAll,
        getPage:getPage,
        getSizeById:getSizeById,
        getColorById:getColorById,
        colorProcess:colorProcess,
        sizeProcess:sizeProcess,
        getBrands:getBrands
    };
}])