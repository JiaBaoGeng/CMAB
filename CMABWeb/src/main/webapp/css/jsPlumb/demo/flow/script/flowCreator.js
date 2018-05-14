jsPlumb.ready(function(){
	$(".dragitem").draggable({
		revert: true,
		revertDuration: 200,
		zIndex: 99999,
		cursor: "move",
		helper: function(){
			if($(".dragclone").length == 0){
				$("body").append("<div class='dragclone'></div>");
			}
			var dom = $(".dragclone").get(0);
			return dom;
		}
	});

	$(".targetarea").droppable({
		accept: ".dragitem",
		drop: function(e, ui){
			createTacheDiv(ui.draggable, ui.offset);
		}
	});
	
	//����ȫ�ֵ�jsPlumbʵ��
	var instance = jsPlumb.getInstance({
		Endpoint: ["Dot", {radius: 2}],
		HoverPaintStyle: {strokeStyle: "#1e8151", lineWidth: 2 },
		ConnectionOverlays: [
			[ "Arrow", {
				location: 1,
				id: "arrow",
				length: 14,
				foldback: 0.8
			} ],
			[ "Label", { label: "FOO", id: "label", cssClass: "aLabel" }]
		],
		Container: "holder"
	});

	window.jsp = instance;
	
	instance.bind("dblclick", function (c) {
		instance.detach(c);
	});
	
	//����ʱ������¼�
	instance.bind("connection", function (info) {
		info.connection.getOverlay("label").setLabel(info.connection.id);
	});
});

function createTacheDiv(el, offset){
	var name = el.attr("name");
	var parent_offset = $("#holder").offset();

	var left = offset.left - parent_offset.left;
	var top = offset.top - parent_offset.top;

	var html = [];
	html.push("<div class='tache' style='left:"+left+"px;top:"+top+"px;'>");
	html.push("<div class='"+name+"' style='width: 50px;height:50px;'></div>");
	
	html.push("<div class='ep'></div>");
	html.push("</div>");

	var tache = $(html.join(""));
	$("#holder").append(tache);

	//�˻��ڿ��϶�
	jsp.draggable(tache);
	
	//Դ����
	jsp.makeSource(tache, {
		filter: ".ep",
		anchor: "Continuous",
		//Bezier Straight Flowchart StateMachine
		connector: [ "StateMachine", { curviness: 20 } ],
		connectorStyle: { strokeStyle: "#5c96bc", lineWidth: 2, outlineColor: "transparent", outlineWidth: 4 },
		maxConnections: 1,
		onMaxConnections: function (info, e) {
			alert("Maximum connections (" + info.maxConnections + ") reached");
		}
	});
	
	//Ŀ�����
	jsp.makeTarget(tache, {
		dropOptions: { hoverClass: "dragHover" },
		anchor: "Continuous",
		allowLoopback: true
	});
	
	bindTacheEvent(tache);
}

function bindTacheEvent($){
	$.click(function(e){
		$(this).siblings(".tache").removeClass("clicked").end().addClass("clicked");
	});
}

function deleteObject(){
	var tacheDivs = $("#holder").find(".tache.clicked");
	tacheDivs.each(function(){
		$(this).remove();
	});
}