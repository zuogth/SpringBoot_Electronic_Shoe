app.controller("ProductController",["$scope","ProductService",function ($scope,ProductService){

    $scope.listPr=true;
    $scope.editPr=false;
    $scope.listDetailPr=false;
    $scope.insertDetail=false;
    $scope.editDetail=false;
    $scope.tableListDetail=false;
    $scope.btnAddChr=true;
    $scope.preViewEdit=true;
    $scope.changeSize=false;
    $scope.flagSize=0;
    $scope.message='';
    $scope.error={};
    $scope.brandSlug='';
    $scope.products=[];
    $scope.product={};
    $scope.productDetails=[];
    $scope.productDetail={};
    $scope.totalItems = 64;
    $scope.totalItemsChr = 64;
    $scope.currentPage = 1;
    $scope.currentPageChr=1;

    init("",$scope.currentPage);
    function init(brand_id,page){
        $scope.currentPageChr=1;
        $scope.listPr=true;
        $scope.editPr=false;
        $scope.listDetailPr=false;
        $scope.insertDetail=false;
        $scope.editDetail=false;
        return ProductService.getAll(brand_id,page).then(function (data){
            $scope.products=data.data.products;
            $scope.totalItems = data.data.totalItems;
            angular.element("h1#title").html("Product");
        },function (error){
            showErr(error);
        })
    }
    //add product parent
    $scope.addPr=function (){
        $scope.listPr=false;
        $scope.editPr=true;
        $scope.listDetailPr=false;
        $scope.insertDetail=false;
        $scope.editDetail=false;
        $scope.product={};
        resetValid();
    }
    //get product parent by id
    $scope.getById=function (id){
        $scope.listPr=false;
        $scope.editPr=true;
        $scope.listDetailPr=false;
        $scope.insertDetail=false;
        $scope.editDetail=false;
        resetValid();
        return ProductService.getById(id).then(function (data){
            $scope.product=data.data;
        },function (error){
            showErr(error);
        });
    }
    //process product parent
    $scope.process=function (){
        return ProductService.process($scope.product).then(function (data){
            $scope.message={content:data.data.message,show:true};
            $scope.getByBrand();
        },function (error){
            if(error.data.statusCode=="3400"){
                mapErrValid(error.data.valid,'');
            }else {
                showErr(error);
            }
        })
    }

    //get product parent by brand
    $scope.getByBrand=function (flag){
        if (flag){
            $scope.currentPage=1;
        }
        $scope.products=[];
        init("&brandSlug="+$scope.brandSlug,$scope.currentPage);
    }

    //get list product detail by id product
    $scope.getDetailById=function (id){
        $scope.listPr=false;
        $scope.editPr=false;
        $scope.listDetailPr=true;
        $scope.insertDetail=false;
        $scope.editDetail=false;
        $scope.tableListDetail=true
        $scope.btnAddChr=true;
        $scope.product.id=id;
        $scope.productDetail={};
        $scope.preViewEdit=true;
        return ProductService.getDetailById(id,$scope.currentPageChr).then(function (data){
            $scope.productDetails=data.data;
            $scope.totalItemsChr=data.data.totalItems;
        },function (error){
            showErr(error);
        });
    }

    $scope.deletePr=function (id){
        if(confirm("Sản phẩm đã xóa sẽ không thể khôi phục! Xóa?")) {
            return ProductService.deletePr(id).then(function (data) {
                $scope.message = {content: data.data.message, show: true};
                init("&brand_id="+$scope.brandSlug,$scope.currentPage);
            }, function (error) {
                showErr(error);
            })
        }
    }

    //product detail
    //add product detail
    $scope.addChr=function (){
        $scope.listPr=false;
        $scope.editPr=false;
        $scope.listDetailPr=true;
        $scope.insertDetail=true;
        $scope.editDetail=false;
        $scope.tableListDetail=false
        $scope.btnAddChr=false;
        $scope.productDetail={};
        addView();
        resetValid();
    }
    //process prod detail
    $scope.processDetail=function(type){
        return submitForm(type).then(function (data){
            $scope.message={content:data.message,show:true};
            $scope.currentPageChr=1;
            $scope.getDetailById($scope.product.id);
        },function (error){
            showErrValid(error,'');
        })
    }

    //get product detail by id
    $scope.getDetailByIdDetail=function (productId,colorId){
        resetForm();
        $scope.listPr=false;
        $scope.editPr=false;
        $scope.listDetailPr=true;
        $scope.insertDetail=false;
        $scope.editDetail=true;
        $scope.tableListDetail=false
        $scope.btnAddChr=false;
        resetValid();
        return ProductService.getDetailByIdDetail(productId,colorId).then(function (data){
            $scope.productDetail=data.data.productDetail;
            $scope.colors=data.data.colors;
            $scope.sizes=data.data.sizes;
        },function (error){
            showErr(error);
        })
    }

    //get sizes don't yet use
    $scope.getSizes=function (productDetailId,flag){
        $scope.flagSize=flag;
        $scope.productDetail.id=productDetailId;
        $scope.changeSize=true;
        return ProductService.getSizes($scope.productDetail).then(function (data){
            $scope.sizeNots=data.data;
        },function (error){
            showErr(error);
        })
    }

    $scope.changeSizeProd=function (){
        return ProductService.processSize($scope.productDetail,$scope.flagSize).then(function (data){
                $scope.changeSize=false;
                $scope.sizes=data.data;
            },function (error){
            showErr(error);
        })
    }

    $scope.pageChanged = function() {
        init("",$scope.currentPage);
    };
    $scope.pageChangedChr = function() {
        $scope.getDetailById($scope.product.id);
    };

    $scope.updateStatus=function (id){
        let lock=$('#'+elm+""+id).prop("checked");
        let status;
        if(lock){
            str='khóa';
            status=0;
        }else {
            str='mở khóa';
            status=1;
        }
        if(confirm("Bạn có muốn "+str+" sản phẩm này không?")){
            $('#'+elm+""+id).prop('checked',!lock);
            lock=!lock;
            if(!lock){
                $('#'+elm+""+id).parent().children('label').addClass('my-custom-control-label-un');
            }else{
                $('#'+elm+""+id).parent().children('label').removeClass('my-custom-control-label-un');
            }
            return ProductService.lockProd(elm,id,status).then(function (data){
                $scope.message={content:data.data.message,show:true};
            },function (error){
                showErr(error);
            })
        }
    }

    $scope.deleteCh=function (id,flag){
        if(confirm("Sản phẩm đã xóa sẽ không thể khôi phục! Xóa?")){
            return ProductService.deleteCh(id,flag).then(function (data){
                $scope.message={content:data.data.message,show:true};
                if(flag==1){
                    if(data.data.httpStatus=='NOT_FOUND'){
                        return $scope.getDetailById($scope.product.id);
                    }
                    return $scope.getDetailByIdDetail($scope.productDetail.productId,$scope.productDetail.colorId);
                }else {
                    return $scope.getDetailById($scope.product.id);
                }
            },function (error){
                showErr(error);
            })
        }
    }

    $scope.closeForm=function (){
        $scope.listPr=false;
        $scope.editPr=false;
        $scope.listDetailPr=true;
        $scope.insertDetail=false;
        $scope.editDetail=false;
        $scope.tableListDetail=true
        $scope.btnAddChr=true;
        $scope.productDetail={};
        $scope.preViewEdit=true;
    }

    function showErr(error){
        $scope.message = {content: error.data.message, show: true};
        if(error.status==401){
            window.location.href="/login";
        }
    }

}]);

function mapErrValid(error,sub){
    $('span.errors').html('');
    error.forEach(err => {
        $('span#' + err.key + 'Er'+sub).html(err.message);
    });
}

function previewImgs(){
    $("#preViewEdit").html($("#preViewEdit2").html());
}

function selectImgPr(e){
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

function selectImgChr(e){
    if (typeof(FileReader) != "undefined") {
        var id=$(e).attr("data-img");
        var divPre=$('#'+id);
        divPre.html('');
        var regex = /^([a-zA-Z0-9\s_\\.\-:])+(.jpg|.jpeg|.gif|.png|.bmp)$/;
        $.each(e.files, function(i,file){
            let reader=new FileReader();
            if (regex.test(file.name.toLowerCase())) {
                $(reader).on("load", function() {
                    divPre.append($("<img/>",{src:this.result}));
                })
                reader.readAsDataURL(file);
            }else {
                alert(file.name + " is not a valid image file.");
                divPre.html("");
                return false;
            }
        });
    }else{
        alert("This browser does not support HTML5 FileReader.");
    }
}

function selectSize(eml){
    let id=$(eml).attr("for");
    let flag=$('input#'+id).prop("checked");
    if(!flag){
        $(eml).addClass("size-active");
    }else{
        $(eml).removeClass("size-active");
    }
}

function submitForm(type){
    let formId;
    let url;
    let method;
    if(type=='insert'){
        formId='formInsertDetail';
        url='/api/admin/productdetails';
        method='POST';
    }else{
        formId='formEditDetail';
        url='/api/admin/productdetails';
        method='PUT';
    }
    let form=document.getElementById(formId);
    let data=new FormData(form);
    return $.ajax({
        url:url,
        type:method,
        data:data,
        cache:false,
        contentType:false,
        processData:false
    })
}

function showErrValid(data,sub){
    $('span.errors').html('');
    if(data.responseJSON.errors!=null) {
        data.responseJSON.errors.forEach(err => {
            $('span#' + err.field + 'Er'+sub).html(err.defaultMessage);
        });
    }else {
        console.log(data);
    }
}

function addView(){
    $('#imgInsert').attr('src','');
    $('#preViewInsert').html('');
}

function resetForm(){
    $("#formEditDetail").trigger("reset");
}

function resetValid(){
    $('span.errors').html('');
}