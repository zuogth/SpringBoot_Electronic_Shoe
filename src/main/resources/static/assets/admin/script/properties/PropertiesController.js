app.controller('PropertiesController',['$scope','PropertiesService',function ($scope,PropertiesService){


    //Object
    $scope.colors=[];
    $scope.color={};
    $scope.sizes=[];
    $scope.size={};
    $scope.brands=[];
    $scope.brand={};

    //properties
    $scope.totalColors=0;
    $scope.totalSizes=0;
    $scope.currentColorPage=1;
    $scope.currentSizePage=1;


    init();
    function init(){
        return PropertiesService.getAll().then(function (data){
            $scope.colors=data.data.colors;
            $scope.sizes=data.data.sizes;
            $scope.brands=data.data.brands;
            $scope.totalSizes=data.data.totalSizes;
            $scope.totalColors=data.data.totalColors;
        },function (error){
            showErr(error)
        })
    }


    $scope.sizePageChanged=function (){
        return PropertiesService.getPage($scope.currentSizePage,'size').then(function (data){
            $scope.sizes=data.data.sizes;
            $scope.totalSizes=data.data.totalSizes;
        },function (error){
            showErr(error);
        })
    }

    $scope.colorPageChanged=function (){
        return PropertiesService.getPage($scope.currentColorPage,'color').then(function (data){
            $scope.colors=data.data.colors;
            $scope.totalColors=data.data.totalColors;
        },function (error){
            showErr(error)
        })
    }

    $scope.brandProcess=function (){
        return submitForm($scope.brand.info.id).then(function (data){
            $scope.message = {content: data.message, show: true};
            getBrands();
            $('#brandOption').modal('hide');
        },function (err){
            console.log(err);
        })
    }

    function getBrands(){
        return PropertiesService.getBrands().then(function (data){
            $scope.brands=data.data;
        },function (err){
            showErr(err);
        })
    }

    $scope.getSizeById=function (id){
        resetValid();
        return PropertiesService.getSizeById(id).then(function (data){
            $scope.size=data.data;
        },function (error){
            showErr(error);
        })
    }

    $scope.getColorById=function (id){
        resetValid();
        return PropertiesService.getColorById(id).then(function (data){
            $scope.color=data.data;
        },function (error){
            showErr(error);
        })
    }

    $scope.getBrandById=function (index){
        $scope.brand=$scope.brands[index];
    }

    $scope.showFormColor=function (){
        $scope.color={};
        resetValid();
    }
    $scope.showFormSize=function (){
        $scope.size={};
        resetValid();
    }

    $scope.colorProcess=function (){
        return PropertiesService.colorProcess($scope.color).then(function (data){
            $scope.message = {content: data.data.message, show: true};
            $("#colorOption").modal("hide");
            $scope.colorPageChanged();
        },function (error){
            if(error.data.statusCode=="3400"){
                mapErrValid(error.data.valid,'');
            }else {
                showErr(error);
            }
        })
    }

    $scope.sizeProcess=function (){
        if(!$scope.size.name){
            $("span#sizeNameEr").html("Bạn chưa nhập cỡ");
            return ;
        }
        return PropertiesService.sizeProcess($scope.size).then(function (data){
            $scope.message = {content: data.data.message, show: true};
            $("#sizeOption").modal("hide");
            $scope.sizePageChanged();
        },function (error){
            if(error.data.statusCode=="3400"){
                mapErrValid(error.data.valid,'');
            }else {
                showErr(error);
            }
        })
    }

    function showErr(error){
        $scope.message = {content: error.data.message, show: true};
        if(error.status==401){
            window.location.href="/login";
        }
    }

    function mapErrValid(error,sub){
        $('span.errors').html('');
        error.forEach(err => {
            $('span#' + err.key + 'Er'+sub).html(err.message);
        });
    }

    function resetValid(){
        $('span.errors').html('');
    }
}])

function selectImg(e){
    if (typeof(FileReader) != "undefined") {
        var id=$(e).attr("data-img");
        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/;
        $.each(e.files, function(i,file){
            let reader=new FileReader();
            if (regex.test(file.name.toLowerCase())) {
                $(reader).on("load", function() {
                    $("#"+id).attr("src",this.result);
                })
                reader.readAsDataURL(file);
            }else {
                alert(file.name + " is not a valid image file.");
                return false;
            }
        });
    }else{
        alert("This browser does not support HTML5 FileReader.");
    }
}

function submitForm(id){
    let form=document.getElementById("formBrand");
    let data=new FormData(form);
    return $.ajax({
        url:"/api/admin/properties/brands/"+id,
        type:"PUT",
        data:data,
        cache:false,
        contentType:false,
        processData:false
    })
}
