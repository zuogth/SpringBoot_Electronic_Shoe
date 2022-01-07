$.ajaxSetup({
    headers: {
        'Token': '4f678ed6-44a9-11ec-ac64-422c37c6de1b',
    }
});
//Lấy data từ list data lên ô input
function getData(element) {
    let parent = $(element).attr("parent");
    let dataName = $(element).html();
    let dataCode = $(element).attr("data-parent");
    $('input#' + parent).val(dataName);
    $('input#' + parent).attr("parent_code", dataCode);
    $(element).parents().eq(1).hide();
    if (parent == 'province') {
        $('input#district').val('');
        $('input#ward').val('');
        $('input#village').val('');
        $('.getcity span').removeClass("span-ac");
        $('input#district').attr("parent_code","")
    }
    if (parent == 'district') {
        $('input#ward').val('');
        $('input#village').val('');
        $('.getcity span').removeClass("span-ac");
        $('span#province').addClass("span-ac");
    }
    //tùy bài
    $(element).parents().eq(2).children('span').addClass("span-ac");
    $(element).parents().eq(2).children('label').hide();
}

//lấy data thành phố từ api ra các thẻ li
function listProvince(element) {
    let parentToList = $(element).attr("id");
    $.ajax({
        url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province',
        type: 'GET',
        contentType:'application/json',
        success: function (rs) {
            let html = '';
            rs['data'].forEach(item => {
                html += `
                            <li data-parent="${item.ProvinceID}" onclick="getData(this)" parent="${parentToList}">${item.ProvinceName}</li>
                            `;
            });
            $('.' + parentToList + ' ul').html(html);
            $('.' + parentToList).show();
        }
    })
}
//lấy data quận, huyện từ api ra các thẻ li
function listDistrict(element) {
    let parentToList = $(element).attr("id");
    let province_id=$('input#province').attr("parent_code");
    if(province_id!=''){
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district',
            type: 'GET',
            data: {province_id},
            success: function (rs) {
                let html = '';
                rs['data'].forEach(item => {
                    html += `
                            <li data-parent="${item.DistrictID}" onclick="getData(this)" parent="${parentToList}">${item.DistrictName}</li>
                            `;
                });
                $('.' + parentToList + ' ul').html(html);
                $('.' + parentToList).show();
            }
        })
    }
}
//lấy data xã từ api ra các thẻ li
function listWard(element) {
    let parentToList = $(element).attr("id");
    let district_id=$('input#district').attr("parent_code");
    if(district_id!=''){
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward',
            type: 'GET',
            data: {district_id},
            success: function (rs) {
                let html = '';
                rs['data'].forEach(item => {
                    html += `
                            <li data-parent="" onclick="getData(this)" parent="${parentToList}">${item.WardName}</li>
                            `;
                });
                $('.' + parentToList + ' ul').html(html);
                $('.' + parentToList).show();
            }
        })
    }
}
//tìm kiếm thành phố
function searchProvince(element) {
    let parentToList = $(element).attr("id");
    let key=$(element).val();
    $.ajax({
        url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/province',
        type: 'GET',
        contentType:'application/json',
        success: function (rs) {
            let html = '';
            rs['data'].forEach(item => {
                let str=ChangeToSlug(item.ProvinceName);
                if(str.indexOf(ChangeToSlug(key))!=-1){
                    html += `
                            <li data-parent="${item.ProvinceID}" onclick="getData(this)" parent="${parentToList}">${item.ProvinceName}</li>
                            `;
                }
            });
            $('.' + parentToList + ' ul').html(html);
            $('.' + parentToList).show();
        }
    })
}
//tìm kiếm quận, huyện
function searchDistrict(element){
    let parentToList = $(element).attr("id");
    let province_id=$('input#province').attr("parent_code");
    let key=$(element).val();
    if(province_id!=''){
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/district',
            type: 'GET',
            data: {province_id},
            success: function (rs) {
                let html = '';
                rs['data'].forEach(item => {
                    let str=ChangeToSlug(item.DistrictName);
                    if(str.indexOf(ChangeToSlug(key))!=-1){
                        html += `
                            <li data-parent="${item.DistrictID}" onclick="getData(this)" parent="${parentToList}">${item.DistrictName}</li>
                            `;
                    }
                });
                $('.' + parentToList + ' ul').html(html);
                $('.' + parentToList).show();
            }
        })
    }
}
//tìm kiếm xã
function searchWard(element){
    let parentToList = $(element).attr("id");
    let district_id=$('input#district').attr("parent_code");
    let key=$(element).val();
    if(district_id!=''){
        $.ajax({
            url: 'https://dev-online-gateway.ghn.vn/shiip/public-api/master-data/ward',
            type: 'GET',
            data: {district_id},
            success: function (rs) {
                let html = '';
                rs['data'].forEach(item => {
                    let str=ChangeToSlug(item.WardName)
                    if(str.indexOf(ChangeToSlug(key))!=-1){
                        html += `
                            <li data-parent="" onclick="getData(this)" parent="${parentToList}">${item.WardName}</li>
                            `;
                    }
                });
                $('.' + parentToList + ' ul').html(html);
                $('.' + parentToList).show();
            }
        })
    }
}

let flag=false;
function onMouseLeave(){
    flag=true;
}
function onMouseEnter(){
    flag=false;
}
function unFocusInput(element){
    let id=$(element).attr("id");
    if(flag){
        $('div.'+id).hide();
    }
}
//hàm chuyển text thành slug
function ChangeToSlug(text) {
    var slug;

    //Đổi chữ hoa thành chữ thường
    slug = text.toLowerCase();

    //Đổi ký tự có dấu thành không dấu
    slug = slug.replace(/á|à|ả|ạ|ã|ă|ắ|ằ|ẳ|ẵ|ặ|â|ấ|ầ|ẩ|ẫ|ậ/gi, 'a');
    slug = slug.replace(/é|è|ẻ|ẽ|ẹ|ê|ế|ề|ể|ễ|ệ/gi, 'e');
    slug = slug.replace(/i|í|ì|ỉ|ĩ|ị/gi, 'i');
    slug = slug.replace(/ó|ò|ỏ|õ|ọ|ô|ố|ồ|ổ|ỗ|ộ|ơ|ớ|ờ|ở|ỡ|ợ/gi, 'o');
    slug = slug.replace(/ú|ù|ủ|ũ|ụ|ư|ứ|ừ|ử|ữ|ự/gi, 'u');
    slug = slug.replace(/ý|ỳ|ỷ|ỹ|ỵ/gi, 'y');
    slug = slug.replace(/đ/gi, 'd');
    //Xóa các ký tự đặt biệt
    slug = slug.replace(/\`|\~|\!|\@|\#|\||\$|\%|\^|\&|\*|\(|\)|\+|\=|\,|\.|\/|\?|\>|\<|\'|\"|\:|\;|_/gi, '');
    //Đổi khoảng trắng thành ký tự gạch ngang
    slug = slug.replace(/ /gi, "-");
    //Đổi nhiều ký tự gạch ngang liên tiếp thành 1 ký tự gạch ngang
    //Phòng trường hợp người nhập vào quá nhiều ký tự trắng
    slug = slug.replace(/\-\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-\-/gi, '-');
    slug = slug.replace(/\-\-\-/gi, '-');
    slug = slug.replace(/\-\-/gi, '-');
    //Xóa các ký tự gạch ngang ở đầu và cuối
    slug = '@' + slug + '@';
    slug = slug.replace(/\@\-|\-\@|\@/gi, '');
    //In slug ra textbox có id “slug”
    return slug;
}
