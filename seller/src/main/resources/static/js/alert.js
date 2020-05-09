function upper_acyivities(activitiesId) {
    $.get("/activities/upper?activitiesId="+activitiesId,function (data,status) {
        if (status==="success")
            alert("操作成功");
        else
            alert("操作失败");
    });
}

function join_acyivities(activitiesId) {
    $.get("/activities/join?activitiesId="+activitiesId,function (data,status) {
        if (status==="success")
            alert("操作成功");
        else
            alert("操作失败");
    });
}

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
function changeorderstatus(orderid) {
    $.get("/order/status?orderId="+orderid,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.reload();
        }
        else
            alert("操作失败");
    });
}
function agree(orderId) {
    $.get("/order/agree?orderId="+orderId,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.reload();
        }
        else
            alert("操作失败");
    });
}
function refuse(orderId) {
    $.get("/order/refuse?orderId="+orderId,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.reload();
        }
        else
            alert("操作失败");
    });
}