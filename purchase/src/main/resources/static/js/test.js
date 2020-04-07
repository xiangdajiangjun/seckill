function addgood(goodid) {
    $.get("/cart/add?goodid="+goodid,function (data,status) {
        if (status==="success")
            alert("操作成功")
        else
            alert("操作失败")
    });
}