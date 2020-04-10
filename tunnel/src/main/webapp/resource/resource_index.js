/**
 * Created by mj on 2017/4/2.
 */
$(function () {
    var ststus = [{'value': '0', 'text': '启用'}, {'value': '1', 'text': '停用'}];

    function forMatter(value) {
        for (var i = 0; i < ststus.length; i++) {
            if (ststus[i].value == value) {
                return ststus[i].text;
            }
        }
    }

    $("#treeData").treegrid({
        url: "queryAll",
        idField: 'id',
        treeField: 'resourceName',
        cascadeCheck: false,
        width: 400,
        rownumbers: true,
        title: "资源列表",
        fit: true,
        columns: [[
            {field: 'resourceName', title: '操 作', width: 180, editor: 'text'},
            {field: 'resourceType', title: '资源类型', width: 200, align: 'center', editor: 'text'},
            {field: 'resourceUrl', title: '资源url', width: 200, align: 'center', editor: 'text'},
            {field: 'resourcePermission', title: '资源权限', width: 200, align: 'center', editor: 'text'},
            {
                field: 'resourceAvailable', title: '资源状态', width: 200, align: 'center', editor: {
                type: 'combobox',
                options: {
                    data: ststus,
                    valueField: "value",
                    textField: "text",
                    editable: false,
                    panelHeight: 50,
                    required: true
                }
            }, formatter: function (value, row, idnex) {
                if (value == 0) {
                    return "<span style='color: green;'>启用</span>";
                } else {
                    return "<span style='color: red;'停用</span>";
                }
            }
            },
        ]],
        toolbar: ['-', {
            text: "保 存", iconCls: 'icon-save', handler: function () {
                save();
            }
        }, '-',
            {
                text: "取 消", iconCls: 'icon-cancel', handler: function () {
                $("#treeData").treegrid("reload")
            }
            },
        ],
        onContextMenu: function (e, row) {
            //屏蔽浏览器的菜单
            e.preventDefault();
            $("#newRowId").empty();
            $("#treeData").treegrid("select", row.id);
            $("#rightMenu").menu("show", {
                left: e.pageX,
                top: e.pageY,
            });
        },
    });
});
var edittingId;
function edit() {
    var row = $("#treeData").treegrid("getSelected");
    edittingId = row.id;
    if (row) {
        $("#treeData").treegrid("beginEdit", edittingId);
    }
}
function save() {
    var newRowId = $("#newRowId").val();
    alert(newRowId)
    var newRow = $("#treeData").treegrid("find", newRowId);
    if (newRowId) {
        $.messager.confirm("提示", "您确定添加该子节点吗？", function (r) {
            if (r) {
                $("#treeData").treegrid("endEdit", newRowId);
                $.ajax({
                    url: "addResource",
                    data: {
                        parentId:newRow._parentId,
                        id: null,
                        resourceName:newRow.resourceName,
                        resourceType:newRow.resourceType,
                        resourceUrl:newRow.resourceUrl,
                        resourcePermission:newRow.resourcePermission,
                        resourceAvailable:newRow.resourceAvailable,
                    },
                    type: "post",
                    dataType: "json",
                    success: function (data) {
                        if (data.msg == "success") {
                            $.messager.alert("提示", "添加成功！", "info", function () {
                                $("#treeData").treegrid("reload");
                            })
                        } else {
                            $.messager.alert("提示", "添加失败！", "error")
                        }
                    },
                    error: function (data) {
                        alert(data)
                    }
                })
            }
        })
    }
}
function cancel() {
    if (edittingId != undefined) {
        $("#treeData").treegrid("cancelEdit", edittingId);
        editingId = undefined;
    }
}
function remove() {
    $.messager.confirm("提示", "您确定删除该节点吗？", function (r) {
        if (r) {
            var row = $("#treeData").treegrid("getSelected");
            //alert(row.id)
            $.ajax({
                url: "removeResource",
                data: {
                    id: row.id,
                },
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.msg == "success") {
                        $.messager.alert("提示", "删除成功！", "info", function () {
                            $("#treeData").treegrid("reload");
                        })
                    } else {
                        $.messager.alert("提示", "删除失败！原因可能是因为有角色拥有该权限。", "error")
                    }
                }
            })
        }
    })
}
function add() {
    var row = $("#treeData").treegrid("getSelected");
    if (row) {
        $("#treeData").treegrid("insert", {
            after: row.id,
            data: {
                id: row.id + 1,
                name: "s",
                resourceType: "button",

            }
        })
    }
}
function addChildren() {
    var row = $("#treeData").treegrid("getSelected");
    if (row) {
        var newChildrenId;
        //获取子节点
        var children = $("#treeData").treegrid("getChildren", row.id);
        //获取父节点
        var parent = $("#treeData").treegrid("getParent", row.id);
        alert(parent==null)
        if(parent==null){
            newChildrenId=Number.MAX_VALUE;
        }else {
            newChildrenId= row.id + children.length + 1;
        }
        //alert(newChildrenId);
        $("#newRowId").val(newChildrenId);
        //alert(childrenId)
        $("#treeData").treegrid("append", {
                parent: row.id,
                data: [{
                    id:newChildrenId,
                    resourceType: "button",
                    resourcePermission: row.resourcePermission.split(":")[0] + ":",
                    resourceAvailable: "0",
                }]
            }
        )
        $("#treeData").treegrid("beginEdit", newChildrenId);
    }
}