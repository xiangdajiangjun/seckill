function buyseck(activitiesGoodsId) {
    $.get("/seckill/buygoods?activitiesGoodsId="+activitiesGoodsId,function (data,status) {
        alert(data);
    });
}

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
function changeorderstatus(orderid) {
    $.get("/order/status?orderId="+orderid,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.href='/order/list';
        }
        else
            alert("操作失败");
    });
}

function return_goods(orderid) {
    $.get("/order/return?orderId="+orderid,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.href='/order/list';
        }
        else
            alert("操作失败");
    });
}