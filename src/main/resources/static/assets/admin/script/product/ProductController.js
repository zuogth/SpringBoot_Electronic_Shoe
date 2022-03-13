app.controller("ProductController",["$scope","ProductService",function ($scope,ProductService){

    $scope.listPr=true;
    $scope.editPr=false;
    $scope.listDetailPr={display:'none'};
    $scope.insertDetail={display:'none'};
    $scope.editDetail={display:'none'};
    $scope.tableListDetail={display:'none'};
    $scope.btnAddChr=true;
    $scope.preViewEdit=true;
    $scope.changeSize=false;
    $scope.flagSize=0;
    $scope.message='';
    $scope.error={};
    $scope.brandId='';
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
        $scope.listDetailPr={display:'none'};
        $scope.insertDetail={display:'none'};
        $scope.editDetail={display:'none'};
        return ProductService.getAll(brand_id,page).then(function (data){
            $scope.products=data.data.products;
            $scope.totalItems = data.data.totalItems;
            angular.element("h1#title").html("Product");
        },function (error){
            console.log(error);
        })
    }
    //add product parent
    $scope.addPr=function (){
        $scope.listPr=false;
        $scope.editPr=true;
        $scope.listDetailPr={display:'none'};
        $scope.insertDetail={display:'none'};
        $scope.editDetail={display:'none'};
        $scope.product={};
    }
    //get product parent by id
    $scope.getById=function (id){
        $scope.listPr=false;
        $scope.editPr=true;
        $scope.listDetailPr={display:'none'};
        $scope.insertDetail={display:'none'};
        $scope.editDetail={display:'none'};
        return ProductService.getById(id).then(function (data){
            $scope.product=data.data;
        },function (error){
            console.log(error.data);
        });
    }
    //process product parent
    $scope.process=function (){
        return ProductService.process($scope.product).then(function (data){
            $scope.message={content:data.data.message,show:true};
            $scope.getByBrand();
        },function (error){
            if(error.data.type=='valid'){
                mapErrValid(error.data.data,'');
            }
            console.log(error.data);
        })
    }

    //get product parent by brand
    $scope.getByBrand=function (flag){
        if (flag){
            $scope.currentPage=1;
        }
        $scope.products=[];
        init("&brand_id="+$scope.brandId,$scope.currentPage);
    }

    //get list product detail by id product
    $scope.getDetailById=function (id){
        $scope.listPr=false;
        $scope.editPr=false;
        $scope.listDetailPr={display:'block'};
        $scope.insertDetail={display:'none'};
        $scope.editDetail={display:'none'};
        $scope.tableListDetail={display:'block'}
        $scope.btnAddChr=true;
        $scope.product.id=id;
        $scope.productDetail={};
        $scope.preViewEdit=true;
        return ProductService.getDetailById(id,$scope.currentPageChr).then(function (data){
            $scope.productDetails=data.data;
            $scope.totalItemsChr=data.data.totalItems;
        },function (error){
            console.log(error);
        });
    }


    //product detail
    //add product detail
    $scope.addChr=function (){
        $scope.listPr=false;
        $scope.editPr=false;
        $scope.listDetailPr={display:'block'};
        $scope.insertDetail={display:'block'};
        $scope.editDetail={display:'none'};
        $scope.tableListDetail={display:'none'}
        $scope.btnAddChr=false;
        $scope.productDetail={};
        addView();
    }
    //process prod detail
    $scope.processDetail=function(type){
        return submitForm(type).then(function (data){
            $scope.message={content:data.message,show:true};
            $scope.currentPageChr=1;
            $scope.getDetailById($scope.product.id);
        },function (error){
            showErr(error,'');
        })
    }

    //get product detail by id
    $scope.getDetailByIdDetail=function (id){
        resetForm();
        $scope.listPr=false;
        $scope.editPr=false;
        $scope.listDetailPr={display:'block'};
        $scope.insertDetail={display:'none'};
        $scope.editDetail={display:'block'};
        $scope.tableListDetail={display:'none'}
        $scope.btnAddChr=false;
        return ProductService.getDetailByIdDetail(id).then(function (data){
            $scope.productDetail=data.data.productDetail;
            $scope.colors=data.data.colors;
            $scope.sizes=data.data.sizes;
        },function (error){
            console.log(error)
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
            console.log(error);
        })
    }

    $scope.changeSizeProd=function (){
        return ProductService.processSize($scope.productDetail,$scope.flagSize).then(function (data){
                $scope.changeSize=false;
                $scope.sizes=data.data;
            },function (error){
            console.log(error);
        })
    }

    $scope.pageChanged = function() {
        init("",$scope.currentPage);
    };
    $scope.pageChangedChr = function() {
        $scope.getDetailById($scope.product.id);
    };

    $scope.updateStatus=function (elm,id){
        let lock=angular.element('#'+elm+""+id).prop("checked");
        let status;
        if(!lock){
            str='khóa';
            status=0;
        }else {
            str='mở khóa';
            status=1;
        }
        if(confirm("Bạn có muốn "+str+" sản phẩm này không?")){
            return ProductService.lockProd(elm,id,status).then(function (data){
                $scope.message={content:data.data.message,show:true};
            },function (error){
                console.log(error);
            })
        }
    }

    $scope.closeForm=function (){
        $scope.listPr=false;
        $scope.editPr=false;
        $scope.listDetailPr={display:'block'};
        $scope.insertDetail={display:'none'};
        $scope.editDetail={display:'none'};
        $scope.tableListDetail={display:'block'}
        $scope.btnAddChr=true;
        $scope.productDetail={};
        $scope.preViewEdit=true;
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
        url='/api/admin/productdetail/insert';
        method='POST';
    }else{
        formId='formEditDetail';
        url='/api/admin/productdetail/update';
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

function showErr(data,sub){
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