app.service('BaseServices',function ($http,$log,$q,$rootScope){
    this.callAPI=function (url,method,model){
        model=JSON.stringify(model);
        let def=$q.defer();//tao doi tuong deferred dai dien cho mot nhiem vu se hoan thanh trong tuong lai
        let req={
            method:method,
            url:'/api/admin'+url,
            contentType:'application/json;charset=utf-8',
            dataType:'json'
        }
        if(model){
            req.data=model;
        }
        $http(req).then(function (data){
            def.resolve(data);//giai quyet 1 promise
        },function (error){
            def.reject(error);//tu choi
        });
        return def.promise;
    }
})