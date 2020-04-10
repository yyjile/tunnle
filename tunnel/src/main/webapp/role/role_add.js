/**
 * Created by mj on 2017/3/31.
 */

//确认新添角色
function addRole() {
    var roleName = $("#roleName").val();
    if (roleName == null || roleName == "") {
        $("#info_notice").html("角色名不为空！");
        $("#info_notice").css("color", "red");
        return false;
    }else {
        $("#info_notice").html("√");
        $("#info_notice").css("color", "#2B79AA");
    }
    $.messager.confirm('确认', '您确认添加吗？', function (r) {
        if (r) {
            $.ajax({
                type: "POST",
                url: "addRole",
                data: $("#addRoleForm").serialize(),
                timeout: 20000,
                dataType: "json",
                cache: false,
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert("提示", "添加成功！","info",function () {
                            //重置表单
                            $("#addRoleForm")[0].reset();
                            $("#RoleWin").window("close");
                            $('#roleList').datagrid("reload");
                        });
                    } else {
                        $.messager.alert("提示", "添加失败！","error",function () {
                            $("#RoleWin").window("close");
                        })
                    }
                },
                error: function () {
                    $.messager.alert("提示", "出现了不可预知的错误，请重新操作！");
                }
            })
        }
    });
}
/**
 * 取消添加角色
 */
function cancelAdd() {
    $("#RoleWin").window("close");
    //重置表单
    $("#addRoleForm")[0].reset();
    $("#info_notice").html("*角色名必填");
    $("#info_notice").css("color","#2B79AA");

}