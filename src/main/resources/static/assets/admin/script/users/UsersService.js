'use strict';
app.service('UsersService',['BaseServices',function (BaseServices){
    let urls={
        process:"/users"
    }

    function getAll(page){
        return BaseServices.callAPI(urls.process+"?page="+page,'GET');
    }

    return {
        getAll:getAll
    }
}])