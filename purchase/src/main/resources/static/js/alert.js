function addgood(goodid) {
    $.get("/cart/add?goodid="+goodid,function (data,status) {
        if (status==="success")
            alert("操作成功");
        else
            alert("操作失败");
    });
}
function deletegood(goodid) {
    $.get("/cart/delete?goodid="+goodid,function (data,status) {
        if (status==="success")
            alert("操作成功");
        else
            alert("操作失败");
        location.href='/cart/list';
    });
}
function pay() {
    $.get("/cart/pay",function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.href='/order/list';
        }
        else
            alert("操作失败")
    });
}