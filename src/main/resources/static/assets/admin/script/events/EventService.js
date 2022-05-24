'use strict';
app.service('EventService',['BaseServices',function (BaseServices){

    let urls={
        process:'/events',
        discount:'/events/discounts'
    }

    function getAll(page){
        return BaseServices.callAPI(urls.process+'?page='+page,'GET');
    }

    function getById(id){
        return BaseServices.callAPI(urls.process+"/detail/"+id,'GET');
    }
    function getProducts(type,id){
        return BaseServices.callAPI(urls.process+"/"+type+"?id="+id,"GET");
    }

    function getDiscounts(eventId){
        return BaseServices.callAPI(urls.discount+'/'+eventId,'GET');
    }

    function process(data){
        return BaseServices.callAPI(urls.process,'POST',data);
    }

    function processDis(data){
        return BaseServices.callAPI(urls.discount,'POST',data);
    }

    function getDataModal(){
        return BaseServices.callAPI(urls.process+"/datas",'GET');
    }

    function getProductDetails(search){
        let str="";
        str+=search.brandId?"&brandId="+search.brandId:"";
        str+=search.productId?"&productId="+search.productId:"";
        return BaseServices.callAPI(urls.process+"/productDetails?h=1"+str,"GET");
    }

    function deleteEvent(id){
        return BaseServices.callAPI(urls.process+"/"+id,"DELETE");
    }

    function showEvent(id,show){
        return BaseServices.callAPI(urls.process+"/show/"+id+"?show="+show,'PUT');
    }

    return {
        getAll:getAll,
        getProducts:getProducts,
        process:process,
        getById:getById,
        getDiscounts:getDiscounts,
        getDataModal:getDataModal,
        getProductDetails:getProductDetails,
        processDis:processDis,
        deleteEvent:deleteEvent,
        showEvent:showEvent
    }

}])