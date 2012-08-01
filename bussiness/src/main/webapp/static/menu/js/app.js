$(function () {
    $("#all_food,#all_drinks,#new_food_type,.food_type_button a").button();//addClass("btn ui-state-default");
    $("#all_food,#all_drinks,#new_food_type,.food_type_button a").hover(
    		function() {$(this).addClass("ui-state-hover")},
    		function(){$(this).removeClass("ui-state-hover")}
    );
//    $("table tr td button").button();
    $(".food_type_button a").click(function () {
        // $("<div>hello,thla</div>").addClass(".table_panel").appendTo($(".right_panel"));
    });
    $("#splitter").wijsplitter({orientation:"vertical", splitterDistance:210});

    var oTable = $("#example").dataTable({
        "bJQueryUI":true,
        "sScrollY": 480,
         "sScrollX": "95%",
       // "sScrollXInner": "100%",
        "iDisplayLength":20,
        "sPaginationType":"full_numbers",
        "oLanguage": {
            "sLengthMenu": '显示 <select>'+
                '<option value="10">10</option>'+
                '<option value="20">20</option>'+
                '<option value="30">30</option>'+
                '<option value="40">40</option>'+
                '<option value="50">50</option>'+
                '<option value="-1">All</option>'+
                '</select> 条记录',
            "sZeroRecords": "无数据!",
            "sInfo": "显示 _START_ 条 到 _END_ 条 总共 _TOTAL_ 条记录",
            "sInfoEmpty": "显示 0 条到 0 条记录 总共 0 记录",
            "sInfoFiltered": "(从 _MAX_ 条记录过滤)",
            "sSearch":"查找",
            "oPaginate": {
                "sFirst": "第一页",
                "sLast": "最后一页",
                "sNext": "下一页",
                "sPrevious": "上一页"
            },
            "sProcessing": "正在处理中...."
        },
        "aoColumnDefs":[
            {
                "fnRender":function(oObj){
//                    return "<button>"+oObj.aData[4]+"</button>"
                	return oObj.aData[4];
                },
                "aTargets":[4]
            },
            {
                "fnRender":function(oObj){
                    return "<img src='http://imgs.douguo.com/upload/stepimage/6/d/5/120_902092.jpg' width='50' height='40'></image>";
                },
                "aTargets":[1]
            },

            {
                "fnRender":function(oObj){
                    return "<input type='checkbox' value='hello'/>"
                },
                "aTargets":[0]
            },
            { "sWidth": "8%", "aTargets": [ 0 ] },
            { "sWidth": "10%", "aTargets": [ 1 ] }
        ]

    });

    $( "#dialog:ui-dialog" ).dialog( "destroy" );

    var name = $( "#name" ),
        description = $( "#description" ),
        allFields = $( [] ).add( name ).add( description),
        tips = $( ".validateTips" );

    $( "#dialog-form" ).dialog({
        autoOpen: false,
        height: 400,
        width: 400,
        modal: true,
        buttons: {
            "新建": function() {
                var bValid = true;
                allFields.removeClass( "ui-state-error" );

                bValid = bValid && checkLength( name, "name", 3, 16 );
//                bValid = bValid && checkLength( email, "email", 6, 80 );
//                bValid = bValid && checkLength( password, "password", 5, 16 );

                bValid = bValid && checkRegexp( name, /^[a-z]([0-9a-z_])+$/i, "category name may consist of a-z, 0-9, underscores, begin with a letter." );
                // From jquery.validate.js (by joern), contributed by Scott Gonzalez: http://projects.scottsplayground.com/email_address_validation/
//                bValid = bValid && checkRegexp( email, /^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i, "eg. ui@jquery.com" );
//                bValid = bValid && checkRegexp( password, /^([0-9a-zA-Z])+$/, "Password field only allow : a-z 0-9" );

                if ( bValid ) {
//                    $( "#users tbody" ).append( "<tr>" +
//                        "<td>" + name.val() + "</td>" +
//                        "<td>" + email.val() + "</td>" +
//                        "<td>" + password.val() + "</td>" +
//                        "</tr>" );
                	
                	//TODO: send request to server
                	alert("send request");
                    $( this ).dialog( "close" );
                }
            },
            "取消": function() {
                $( this ).dialog( "close" );
            }
        },
        close: function() {
            allFields.val( "" ).removeClass( "ui-state-error" );
        }
    });

    $("#new_food_type").click(function(){
        $("#dialog-form").dialog("open");
    });

});