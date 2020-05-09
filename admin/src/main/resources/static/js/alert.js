function continue_next(activitiesId) {
    $.get("/activities/continue?activitiesId="+activitiesId,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.reload();
        }
        else
            alert("操作失败");
    });
}
function role_distribution(username,roleId) {
    $.get("/account/role/distribute?username="+username+"&roleId="+roleId,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.reload();
        }
        else
            alert("操作失败");
    });
}
function deltype(typeId) {
    $.get("/type/delete?typeId="+typeId,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.reload();
        }
        else
            alert("操作失败");
    });
}
function change_type_status(typeId) {
    $.get("/type/status?typeId="+typeId,function (data,status) {
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
function changeaccountstatus(username) {
    $.get("/account/status?username="+username,function (data,status) {
        if (status==="success"){
            alert("操作成功");
            location.reload();
        }
        else
            alert("操作失败");
    });
}