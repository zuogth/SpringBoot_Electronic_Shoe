'use strict';
app.service('BillsService',['BaseServices',function (BaseServices){
    let urls={
        process:'/bills'
    }

    function getAll(page,now){
        if (now=="true"){
            return BaseServices.callAPI(urls.process+"?page="+page+"&now=true",'GET');
        }
        return BaseServices.callAPI(urls.process+"?page="+page,'GET');
    }

    function getById(id){
        return BaseServices.callAPI(urls.process+"/"+id,"GET");
    }

    function update(bill){
        return BaseServices.callAPI(urls.process+"/"+bill.id+"?status="+bill.status,"PUT");
    }

    return {
        getAll:getAll,
        getById:getById,
        update:update
    }
}])