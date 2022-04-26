'use strict';
app.service('HomesService',['BaseServices',function (BaseServices){
    let urls={
        chart:'/chart',
        index:''
    }

    function getAll(){
        return BaseServices.callAPI(urls.index,'GET');
    }

    function getChart(year){
        return BaseServices.callAPI(urls.chart+"?year="+year,'GET');
    }
    return {
        getAll:getAll,
        getChart:getChart
    }
}])