function addgoods(goodsid) {
    $.get("/cart/add?goodid="+goodsid,function (data,status) {
        if (status==="success")
            alert("操作成功");
        else
            alert("操作失败");
    });
}
function delgoods(goodsid) {
    $.get("/goods/del?goodsid="+goodsid,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.reload();
        }
        else
            alert("操作失败");
    });
}
function logout() {
    $.get("/logout",function (data,status) {
        if (status==="success")
            alert("操作成功");
        else
            alert("操作失败");
        location.href='/welcome';
    });
}
function formsuba() {
    $("#goodsform")

}