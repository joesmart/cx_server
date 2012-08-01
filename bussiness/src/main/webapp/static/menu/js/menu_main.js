/**
 * 
 */

function changeCategory(env) {
	var categoryId = env.getAttribute("id");
	var command = new Command();
	command.url = MENU_MAIN_URL;
	command.method = METHOD_POST;
	command.setPostbody(categoryId);
	command.handler = changeCategoryCallback;
	sendRequest(command, function(resultCode, resultJson) {
		changeCategoryCallback(categoryId, resultCode, resultJson);
	});
}

function changeCategoryCallback(categoryId, resultCode, resultJson) {
	if(resultCode == ERROR_SUCCESS) {
		if(resultJson == null || resultJson.dishes == null) {
			updateCategoryListUI(categoryId, null);
		} else {
			var dishes = resultJson.dishes;
			updateCategoryListUI(categoryId, dishes);
		}
	}
}

function updateCategoryListUI(categoryId, dishes) {
	$("tbody tr").remove();
	if(dishes == null) 
		return;	
	
	for(var i = 0; i < dishes.length; i++) {
		var tr = createElement(categoryId, dishes[i]);
		$("tbody").append(tr);
	}
	
}

function createElement(categoryId, dish) {
	var li = $('<tr class="gradeX odd">');
	li.append($('<td class="sorting_1"><input type="checkbox" value="hello"></td>'));
	var img = $('<img></img>').attr({
		"src":dish.pictureurl,
		"width": "50",
		"height": "40"
	}); 
	
	li.append($('<td class=" "></td>').append(img))
		.append($('<td class="center"></td>').html(dish.name))
		.append($('<td class="center"></td>').html(dish.price));
	
	var operation_td = $('<td class="center"></td>')
	var deleteHrefUrl = "delete/dish/" + dish.id;
	var updateHrefUrl = "update/dish/" + dish.id;
	operation_td.append($('<a href= ' + updateHrefUrl + ' class="menu_main_operation"></a>').html("修改"));
	operation_td.append($('<a href= ' + deleteHrefUrl + ' class="menu_main_operation"></a>').html("删除"));
//	var deleteNode = $('<a class="menu_main_operation"></a>').html("删除").attr({
//		"categoryId" : categoryId,
//		"dishId" : dish.id
//	});
//	deleteNode.click(function() {
//		deleteDish(this);
//	});
//	operation_td.append(deleteNode);
	
	li.append(operation_td);
	return li;
	
}

function deleteDish(env) {
	if(confirm("确定要删除这道菜吗?")) {
		var categoryId = $(env).attr("categoryId");
		var dishId = $(env).attr("dishId");
		var command = new Command();
		command.url = "http://localhost:8080/mini-web/menu/category/" + categoryId + "/dish/" + dishId;
		command.method = "DELETE";
		sendRequest(command, function(resultCode, resultJson) {
			deleteDishCallback(env, resultCode, resultJson);
		});
	}
}

function deleteDishCallback(env, resultCode, resultJson) {
	if(resultCode == ERROR_SUCCESS) {
		$(env).parents("tr").remove();
	}
}