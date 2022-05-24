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

    $rootScope.toMoney=function (price){
        return price?price.toLocaleString('it-IT', {
            style: 'currency',
            currency: 'VND'
        }):'';
    }

    $rootScope.formatDateTime=function (_date){
        let date = new Date(_date);
        return date.toLocaleString('it-IT');
    }

    $rootScope.formatDate=function (_date){
        let date = new Date(_date);
        return date.toLocaleDateString("it-IT");
    }

    $rootScope.setTitle=function (title,id) {
        $("h1#title").html(title);
        $("a#nav-"+id).addClass("active");
    }
})

function searchTable (e,id){
    let value = $(e).val().toLowerCase();
    $("#"+id+" tbody tr").filter(function() {
        $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
    });
}
