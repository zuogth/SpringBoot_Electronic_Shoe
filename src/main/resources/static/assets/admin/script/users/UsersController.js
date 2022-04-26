app.controller('UsersController',['$scope','UsersService',function ($scope,UsersService){

    $scope.users=[];

    $scope.totalItems=0;
    $scope.currentPage=1;

    init();

    function init(){
        return UsersService.getAll($scope.currentPage).then(function (data){
            $scope.users=data.data.users;
            $scope.totalItems=data.data.totalItems;
        },function (err){
            showErr(err);
        })
    }

    $scope.gender=function (gender){
        return gender==1?"Nam":"Nữ";
    }

    function showErr(error){
        $scope.message = {content: error.data.message, show: true};
        if(error.status==401){
            window.location.href="/login";
        }
    }

    function mapErrValid(error,sub){
        resetValid();
        error.forEach(err => {
            $('span#' + err.key + 'Er'+sub).html(err.message);
        });
    }

    function resetValid(){
        $('span.errors').html('');
    }

}])