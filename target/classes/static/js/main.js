$(function() {
	reload();
	selectBar();
	// $(".style-btn a").click(function() {
	// 	applyStyle();
	// })
	$(".dialog-btn").click(function() {
		nlpDialog();
	})
})
function reload() {
    $(".main-comp").height($(window).height());
}

function selectBar() {
	$(".select-item").click(function() {
		var selectId = $(this).attr("id").split("-")[1];
		var barId = selectId + "bar";
		$(".select-item.selected").removeClass("selected");
		$(this).addClass("selected");

		$(".compbar").addClass("hide");
		$("#" + barId).removeClass("hide");
	})
}

function showProps(node) {
	var relations;

	if (node.index < 1000) {
		$.ajax({
			url: "http://localhost:8082/kgraph/findRelationByPatent/" + node.index.toString(),
			async: false,
			success: function (result) {
				console.log(result);
				relations = JSON.parse(result);
			}
		})

		$("#patent-name").text(node.name);
		$("#patent-id").text(node.id);
		$("#patent-date").text(node.date);
		$("#patent-summary").text(node.summary);
		$(".desp-dev").empty();
		for (var i = 0; i < relations.length; i++) {
			var exp_num = 0;
			var expert_name;
			$.ajax({
				url: "http://localhost:8082/kgraph/findRelationByExpert/" + relations[i].target.toString(),
				async: false,
				success: function (result) {
					exp_num = JSON.parse(result).length;
				}
			})
			$.ajax({
				url: "http://localhost:8082/kgraph/findExpert/" + relations[i].target.toString(),
				async: false,
				success: function (result) {
					console.log(JSON.parse(result)[0]);
					expert_name = JSON.parse(result)[0]["name"];
				}
			})
			var devItem = '<li class="dev-item">' +
				'<span class="collection-index">' + (i + 1) + '</span>' +
				'<span class="collection-name">' + expert_name + '</span>' +
				'<span class="collection-badge badge" data-badge-caption="">' + exp_num + '</span>' +
				'</li>';
			$("#patent-propbar .desp-dev").append(devItem);
		}
		$("#patent-propbar").removeClass("hide");
		$("#expert-propbar").addClass("hide");
	}
	else {
		console.log(node.name);
		$.ajax({
			url: "http://localhost:8082/kgraph/findRelationByExpert/" + node.index.toString(),
			async: false,
			success: function (result) {
				relations = JSON.parse(result);
				console.log(relations);
			}
		})

		$("#expert-name").text(node.name);
		$(".desp-dev").empty();
		for (var i = 0; i< relations.length; i++) {
			var patent_name;
			$.ajax({
				url: "http://localhost:8082/kgraph/findItem/" + relations[i].source.toString(),
				async: false,
				success: function (result) {
					console.log(result);
					patent_name = JSON.parse(result)[0]['name'];
				}
			})
			var devItem = '<li class="dev-item">' +
				'<span class="collection-index">' + (i + 1) + '</span>' +
				'<span class="collection-name">' + patent_name + '</span>' +
				'</li>';
			$("#expert-propbar .desp-dev").append(devItem);
		}
		$("#patent-propbar").addClass("hide");
		$("#expert-propbar").removeClass("hide");
	}
}

function applyStyle() {
	var patentColor = $("#patent-color").val();
	var expColor = $("#exp-color").val();
	var patentSize = $("#patent-size").val();
	var expSize = $("#exp-size").val();

	console.log(patentColor, expColor, patentSize, expSize);

	graph(items_json, experts_json,link_json, patentColor, expColor, patentSize, expSize);
}

function sendDialog(text) {
	var dialog = '<div class="dialog-part">' +
		'<div class="dialog-content right">' + text + '</div>' +
		'<div style="clear: both"></div>' +
		'</div>';
	$(".dialogbar").append(dialog);
}

function receiveDialog(text) {
	var dialog = '<div class="dialog-part">' +
		'<div class="dialog-content left">' + text + '</div>' +
		'<div style="clear: both"></div>' +
		'</div>';
	$(".dialogbar").append(dialog);
}

function nlpDialog() {
	var relations;
	var messageText = $(".message-text input").val();
	var messageBody = messageText.split("的")[0];
	var messageRelation = messageText.split("的")[1];

	sendDialog(messageText);

	if (messageRelation == "发明者") {
		var type = "hasExpert";
		var experts = [];
		var sendText;
		$.ajax({
			url: "http://localhost:8082/kgraph/findRelationByPatentRel/" + messageBody + "/" + type,
			dataType: "json",
			async: false,
			success: function(result) {
				for(var i = 0; i< result.length; i++) {
					$.ajax({
						url: "http://localhost:8082/kgraph/findExpert/" + result[i].target.toString(),
						async: false,
						success: function (result) {
							experts.push(JSON.parse(result)[0]["name"]);
						}
					})
				}
			}
		})
		sendText = experts.join(",");
		receiveDialog(sendText);
	}
	else if (messageRelation == "相关专利") {
		var type = "1";
		var patents = [];
		var sendText;
		$.ajax({
			url: "http://localhost:8082/kgraph/findRelationByIdRel/" + messageBody + "+" + type,
			dataType: "json",
			async: false,
			success: function(result) {
				console.log(result);
				for(var i = 0; i< result.length; i++) {
					console.log(result[i].target.toString());
					patents.push(result[i].target.toString());
				}
			}
		})
		sendText = patents.join(",");
		receiveDialog(sendText);
	}
	else if (messageRelation == "发明专利") {
		var type = "0";
		var patents = [];
		var sendText;
		$.ajax({
			url: "http://localhost:8082/kgraph/findRelationByNameRel/" + messageBody + "+" + type,
			dataType: "json",
			async: false,
			success: function(result) {
				console.log(result);
				for(var i = 0; i< result.length; i++) {
					patents.push(result[i].source_set);
				}
			}
		})
		sendText = patents.join(",");
		receiveDialog(sendText);
	}
}