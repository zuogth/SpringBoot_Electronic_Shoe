app.controller('EventController',['$scope','EventService',function ($scope,EventService){

    $scope.totalItems=0;
    $scope.currentPage=1;

    $scope.events=[];
    $scope.event={};
    $scope.discounts=[];
    $scope.discount={};
    $scope.discount.discountId='';
    $scope.discount.brandIds=[];
    $scope.discount.productDetailIds=[];
    $scope.discount.productIds=[];
    $scope.discountItems=[];
    $scope.discountItem={};
    $scope.brands=[];
    $scope.products=[];
    $scope.productDetails=[];
    $scope.search={};
    $scope.productDetailDiscounts=[];

    $scope.listPr=true;
    $scope.formAdd=false;
    $scope.listCh=false;
    $scope.btnSaveDis=false;
    $scope.btnSave=true;
    $scope.addDis=true;
    init();
    function init(){

        $scope.listPr=true;
        $scope.formAdd=false;
        $scope.listCh=false;
        $scope.btnSaveDis=false;
        $scope.btnSave=true;
        $scope.addDis=true;
        $scope.events=[];
        $scope.event={};
        return EventService.getAll($scope.currentPage).then(function (_data){
            $scope.totalItems=_data.data.totalItems;
            $scope.events=_data.data.events;
        },function (error){
            showErr(error)
        })
    }


    $scope.addPr=function (){
        $scope.listPr=false;
        $scope.formAdd=true;
        $scope.listCh=false;
        $scope.btnSaveDis=false;
        $scope.addDis=true;
        $scope.event={};
        resetValid();
        $(".inputs-discount").html('');
        $("#imgBanner").attr("src","");
        $("#fileBanner").val("");
    }

    $scope.pageChanged=function (){
        init();
    }

    $scope.process=function (){
        return submitForm().then(function (_data){
            $scope.message = {content: _data.message, show: true};
            $scope.event = _data.event;
            $scope.getByIdForEdit($scope.event.id);
        },function (error){
            showErrValid(error,'');
        })
    }

    $scope.getById=function (id){
        $scope.listPr=false;
        $scope.formAdd=false;
        $scope.listCh=true;
        $scope.btnSaveDis=false;
        $scope.addDis=false;
        return EventService.getById(id).then(function (_data){
            $scope.event = _data.data.event;
            $scope.productDetailDiscounts = _data.data.products;
        },function (error){
            showErr(error);
        })
    }

    $scope.getByIdForEdit=function (id){
        $scope.listPr=false;
        $scope.formAdd=true;
        $scope.listCh=false;
        $scope.btnSaveDis=false;
        $scope.addDis=false;
        resetValid();
        return EventService.getById(id).then(function (_data){
            $scope.event=_data.data;
            $scope.discountItems = _data.data.discounts;
            $scope.event.startAt=new Date($scope.event.startAt);
            $scope.event.endAt=new Date($scope.event.endAt);
        },function (error){
            showErr(error);
        })
    }

    function getDataModal(){
        return EventService.getDataModal().then(function (_data){
            $scope.brands=_data.data.brands;
            $scope.products=_data.data.products;
        },function (error){
            showErr(error);
        })
    }

    $scope.addProductDis=function (index){
        $scope.search={};
        $scope.brands=[];
        $scope.products=[];
        $scope.productDetails=[];
        $scope.discountItem = $scope.discountItems[index];
        getDataModal();
        $('#addProductDis').modal('show');
    }

    $scope.searchProduct=function (){
        return EventService.getProductDetails($scope.search).then(function (data){
            if(!$scope.search.productId){
                $scope.products=data.data.products;
            }
            $scope.productDetails=data.data.productDetails.filter(checkProducts);
        },function (error){
            showErr(error);
        })
    }

    function checkProducts(product){
        let length = $scope.discounts.length;
        for(var i =0 ; i<length ; i++){
            if($scope.discountItem.id != $scope.discounts[i].discountId) {
                let lengthP = $scope.discounts[i].productDetailIds.length;
                for(var j =0;j<lengthP ;j++){
                    if($scope.discounts[i].productDetailIds[j]*1==product.id) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    function checkDisId(){
        let length = $scope.discounts.length;
        for(var i =0 ; i<length ; i++){
            if($scope.discountItem.id == $scope.discounts[i].discountId) {
                return true;
            }
        }
        return false;
    }

    $scope.processProductDis=function (){
        let flag=false;
        $scope.discount={};
        $scope.discount.productDetailIds=[];
        $("input.selectProductDetailIds").each(function (){
            if($(this).prop("checked")){
                $scope.discount.productDetailIds.push($(this).val());
                flag=true;
            }
        })
        $scope.discount.discountId=$('#discountId').val();
        if(flag){
            if(!checkDisId()){
                $scope.discounts.push($scope.discount);
            }else {
                for(var i=0;i<$scope.discounts.length;i++){
                    if($scope.discountItem.id == $scope.discounts[i].discountId){
                        $scope.discounts[i]=$scope.discount;
                    }
                }
            }
            $scope.btnSaveDis=true;
            $scope.btnSave=false;

        }
        $scope.message = {content: "Danh sách áp dụng được lưu tạm", show: true};
    }

    $scope.processDis=function (){
        return EventService.processDis($scope.discounts).then(function (_data){
            $scope.message = {content: _data.data.message, show: true};
            init();
        },function (error){
            showErr(error);
        })
    }

    $scope.delete=function (index){
        let id = $scope.events[index].id;
        if(confirm("Bạn có chắc muốn xóa sự kiện này không?")){
            return EventService.deleteEvent(id).then(function (_data){
                $scope.message={content:_data.data.message, show:true};
                init();
            },function (err){
                showErr(err);
            })
        }
    }

    $scope.updateShow=function (id){
        let lock=$('#show'+id).prop("checked");
        let show;
        if(lock){
            show=0;
        }else {
            show=1;
        }
        $('#show'+id).prop('checked',!lock);
            lock=!lock;
            if(!lock){
                $('#show'+id).parent().children('label').addClass('my-custom-control-label-un');
            }else{
                $('#show'+id).parent().children('label').removeClass('my-custom-control-label-un');
            }
            return EventService.showEvent(id,show).then(function (data){
                $scope.message={content:data.data.message,show:true};
            },function (error){
                showErr(error);
            })

    }

    $scope.close=function (){
        init();
    }

    function showErr(error){
        $scope.message = {content: error.data.message, show: true};
        if(error.status==401){
            window.location.href="/login";
        }
    }

    function showErrValid(error,sub){
        $('span.errors').html('');
        if(error.responseJSON.errors!=null) {
            error.responseJSON.errors.forEach(err => {
                $('span#' + err.field + 'Er'+sub).html(err.defaultMessage);
            });
        }else {
            showErr(error);
        }
    }

    $scope.getDate=function (date){
        let d=new Date(date)
        return d.toLocaleString("it-IT");
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

function submitForm(){
    let form=document.getElementById("formAddDis");
    let data=new FormData(form);
    return $.ajax({
        url:"/api/admin/events",
        type:"POST",
        data:data,
        cache:false,
        contentType:false,
        processData:false
    })
}

function addDiscount(){
    let val = $('#valueDisAdd').val();
    if(val=='')return;
    let html = `<div><label class="labelDis">`+val+`% <i class="fas fa-times" onclick="delDis(this)"></i></label>
                    <input type="checkbox" class="form-control" name="discounts" value="`+val+`" hidden checked></div>`;
    $('.inputs-discount').append(html);
    $('#valueDisAdd').val('');
}

function delDis(e){
    $(e).parents().eq(1).remove();
}

function resetValid(){
    $('span.errors').html('');
}
