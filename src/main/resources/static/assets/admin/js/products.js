let table;
let tableChr;
let elmOld;
let elmPrOld;
//Load all data product
function loadDataPr(e,action,title){
    if(elmPrOld!=e){
        $('#listPr').show();
        $('#listDetailPr').hide();
        $('#editPr').hide();
        $('h1#title').html(title);
        $(elmPrOld).removeClass("active");
        $(e).addClass("active");
        elmPrOld=e;
        $.ajax({
            url:'/api/admin/'+action,
            type:'GET',
            contentType:'application/json',
            success:function (rs){
                let html='';
                if (rs!=null){
                    rs.forEach(item=>
                        html+=`
                    <tr>
                      <td>${item.name}</td>
                      <td>${item.price}</td>
                      <td>${item.receipt}</td>
                      <td>${item.sold}</td>
                      <td>${item.status=0?'ACT':'N-ACT'}</td>
                      <td><a href="#" onclick="loadDataChr(${item.id})"><i class="fas fa-info"></i></a></td>
                      <td>
                        <a href="#" onclick="editProdPr(${item.id})"><i class="far fa-edit"></i></a>
                        <a href=""><i class="fas fa-trash-alt"></i></a>
                      </td>
                    </tr>
                `
                    )
                }
                if(table)table.destroy();
                $('#productPr tbody').html(html);
                table=loadDataTablePr();
                $("div.toolbar").html('<a class="btn btn-default" onclick="insertView()">Thêm mới</a>');
            },
            error:function (error){
                console.log(error);
            }
        });
    }
}

//Load data product by brand
function loadData(e,brand_id,title){
    $('#listPr').show();
    $('#listDetailPr').hide();
    $('#editPr').hide();
    $('h1#title').html(title);
    $(elmOld).removeClass("active");
    $(e).addClass("active");
    elmOld=e;
    $.ajax({
        url:'/api/admin/product'+'?brand_id='+brand_id,
        type:'GET',
        contentType:'application/json',
        success:function (rs){
            let html='';
            rs.forEach(item=>
                html+=`
                    <tr>
                      <td>${item.name}</td>
                      <td>${item.price}</td>
                      <td>${item.receipt}</td>
                      <td>${item.sold}</td>
                      <td>${item.status=1?'ACT':'N-ACT'}</td>
                      <td><a href="#" onclick="loadDataChr(${item.id})"><i class="fas fa-info"></i></a></td>
                      <td>
                        <a href="#" onclick="editProdPr(${item.id})"><i class="far fa-edit"></i></a>
                        <a href=""><i class="fas fa-trash-alt"></i></a>
                      </td>
                    </tr>
                `
            )
            if(table)table.destroy();
            $('#productPr tbody').html(html);

            table=loadDataTablePr();
            $("div.toolbar").html('<a class="btn btn-default" onclick="insertView()">Thêm mới</a>');
        },
        error:function (error){
            console.log(error);
        }
    });
}

//Load data product detail by product id
function loadDataChr(id){
    $('#listPr').hide();
    $('#listDetailPr').show();
    $('#editPr').hide();
    $('#insertDetail').hide();
    $('#editDetail').hide();
    $('#tableListDetail').show();
    $.ajax({
        url:'/api/admin/product/'+id,
        type:'GET',
        contentType:'application/json',
        success:function (rs){
            $('input#name').val(rs.product.name);
            $('input#brand').val(rs.product.brand);
            $('input#description').val(rs.product.description);
            $('textarea#content').val(rs.product.content);
            let html='';
            rs.product.detailList.forEach(item=>
                html+=`
                    <tr>
                      <td>
                        <img src="${item.image}"/>
                      </td>
                      <td>${item.color}</td>
                      <td>${rs.product.price}</td>
                      <td>${item.receipt}</td>
                      <td>${item.sold}</td>
                      <td><a href=""><i class="fas fa-info"></i></a></td>
                      <td>
                        <a href="#" onclick="editDetail(${item.id})"><i class="far fa-edit"></i></a>
                        <a href=""><i class="fas fa-trash-alt"></i></a>
                      </td>
                    </tr>
                `
            )
            let options='<option value="">-- Chọn màu --</option>';
            rs.colors.forEach(color=>options+=`<option value="${color.id}">${color.name}</option>`)
            $('select#color').html(options);

            let sizes='';
            rs.sizes.forEach(size=>sizes+=`<label for="size-${size.name}" onclick="selectSize(this)">${size.name}</label>
                                        <input type="checkbox" name="sizeIds" id="size-${size.name}" value="${size.id}" hidden>`)
            $('#insertDetail .filter-size').html(sizes);
            $('input#productId').val(rs.product.id);
            $('input#productId_e').val(rs.product.id);
            if(tableChr)tableChr.destroy();
            $('#productDetail tbody').html(html);

            tableChr=loadDataTableChr();
            $("div.toolbar").html('<a class="btn btn-default" onclick="insertChrView()">Thêm mới</a>');
        }
    });
}
//view form insert product
function insertView(){
    $('#editPr').show();
    $('#listPr').hide();
    $('#btnEdit').hide();
    $('#btnInsert').show();
    $('#listDetailPr').hide();
    $('.errors').html('');
    document.getElementById("formUpdate").reset();
}
//insert product
function insert(){
    let form=document.getElementById("formUpdate");
    let data=new FormData(form);
    $.ajax({
        type:'POST',
        url: '/api/admin/product/insert',
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        success:function(rs){
            $('div.alert-noti span').html(rs);
            $('div.alert-noti').show();
            $('.errors').html('');
        },
        error: function(data){
            showErr(data,'')
        }
    });
}
//view data product to edit
function editProdPr(id){
    $('#editPr').show();
    $('#listPr').hide();
    $('#btnEdit').show();
    $('#btnInsert').hide();
    $('#listDetailPr').hide();
    $.ajax({
        url:'/api/admin/product/edit/'+id,
        type:'GET',
        contentType:'application/json',
        success:function (rs){
            $('input#idUpPr').val(rs.product.id);
            $('input#nameUpPr').val(rs.product.name);
            $('input#descriptionUpPr').val(rs.product.description);
            $('textarea#contentUpPr').val(rs.product.content);
            $('input#priceUpPr').val(rs.product.price);
            let html='';
            rs.brand.forEach(b=> {
                html += `<option value="${b.id}" ${b.id==rs.product.brand.id?'selected':''}>${b.name}</option>`;
            });
            $('select#brandUpPr').html(html);
        },error:function (){

        }
    })
}


//update product
function update(){
    let form=document.getElementById("formUpdate");
    let data=new FormData(form);
    data=JSON.stringify(data);
    $.ajax({
        type:'PUT',
        url: '/api/admin/product/update',
        data: data,
        cache: false,
        contentType: false,
        processData: false,
        success:function(rs){
            $('div.alert-noti span').html(rs);
            $('div.alert-noti').show();
            $('.errors').html('');
        },
        error: function(data){
            showErr(data,'');
        }
    });
}

//load Datatable from html data
function loadDataTablePr(){
    return $('#productPr').DataTable({
        "orderMulti": true,
        "dom": '<"toolbar">frtip',
        "info": false,
        columnDefs: [
            { orderable: false, targets: [2,3,4,5,6] },
            {
                targets: 1,
                render: $.fn.dataTable.render.intlNumber('it-IT', {
                    style: 'currency',
                    currency: 'VND'
                })
            }
        ],
        "pageLength": 10,
        responsive:true
    });
}

function loadDataTableChr(){
    return $('#productDetail').DataTable({
        "orderMulti": true,
        "dom": '<"toolbar">frtip',
        "info": false,
        columnDefs: [
            { orderable: false, targets: [0,1,3,4,5,6] },
            {
                targets: 2,
                render: $.fn.dataTable.render.intlNumber('it-IT', {
                    style: 'currency',
                    currency: 'VND'
                })
            }
        ],
        "pageLength": 10,
        responsive:true
    });
}

function showErr(data,sub){
    if(data.responseJSON.errors!=null) {
        data.responseJSON.errors.forEach(err => {
            $('span#' + err.field + 'Er'+sub).html(err.defaultMessage);
        });
    }else {
        $('div.alert-noti span').html(data.responseJSON.message);
        $('div.alert-noti').show();
    }
}

//options edit product detail
function selectSize(eml){
        let id=$(eml).attr("for");
        let flag=$('input#'+id).prop("checked");
        if(!flag){
            $(eml).addClass("size-active");
        }else{
            $(eml).removeClass("size-active");
        }
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

function closeForm(){
    $('#editDetail').hide();
    $('#insertDetail').hide();
    $('#tableListDetail').show();
}
//view form insert product detail
function insertChrView(){
    $('#insertDetail').show();
    $('#tableListDetail').hide();
    $('.errors').html('');
    $('.filter-size label').removeClass("size-active");
    document.getElementById('formInsertDetail').reset();
    $('#imgInsert').attr('src','');
    $('#preViewInsert').html('');
}

//view form product detail to update
function editDetail(id){
    $('#editDetail').show();
    $('#tableListDetail').hide();
    $('.errors').html('');
    $('.filter-size label').removeClass("size-active");
    document.getElementById('formEditDetail').reset();
    let productId=$('input#productId_e').val();
}

//insert product detail
function submitForm(){
    let form=document.getElementById('formInsertDetail');
    let data=new FormData(form);
    $.ajax({
        url:'/api/admin/productdetail/insert',
        type:'POST',
        data:data,
        cache:false,
        contentType:false,
        processData:false,
        success:function (rs){
            loadDataChr(rs);
            $('div.alert-noti span').html("Thêm thành công");
            $('div.alert-noti').show();
            $('.errors').html('');
        },
        error:function (data){
            showErr(data,'');
        }
    })
}

//format currency
function toMoney(totalprice){
    return totalprice.toLocaleString('it-IT', {
        style: 'currency',
        currency: 'VND'
    });
}