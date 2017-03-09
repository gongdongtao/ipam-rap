(function() {
	'use strict';
	rap.registerTypeHandler("eclipsesource.ipamjs", {
		factory : function(properties) {
			return new eclipsesource.ipamjs(properties);
		},
		destructor : "destroy",
		methods : ["loadData"],
		properties : [ "size"],
		events:[]
	});

	if (!window.eclipsesource) {
		window.eclipsesource = {};
	}

	eclipsesource.ipamjs = function(properties) {
		bindAll(this, [ "layout", "onReady", "onSend", "onRender"]);
		this.parent = rap.getObject(properties.parent); //获取java端的一个图形容器。
		this.element = document.createElement("div");
		this.parent.append(this.element);
		this.parent.addListener("Resize", this.layout);

		this._size = properties.size ? properties.size : {
			width : 300,
			height : 300
		};
		var area = this.parent.getClientArea();
		this._size = {width:area[2]||300,height:area[3]||300};
		this.element.style.width = '100%';//this._size.width+"px";
		this.element.style.height = '100%';//(this._size.height)+"px";
		this.element.style.overflow = 'auto';
		//this.element.style.border = '1px solid grey';
		rap.on("render", this.onRender);
	};
	eclipsesource.ipamjs.prototype = {
		ready : false,
		onReady : function() {
			// TODO [tb] : on IE 7/8 the iframe and body has to be made
			// transparent explicitly
			this.ready = true;
			this.layout();
			console.log("ipamjs...onReady..");
		},

		onRender : function() {
		var _this = this;
			if (this.element.parentNode) {
				rap.off("render", this.onRender);
				console.log("ipamjs...onRender..");
				// Creates the graph inside the given container
				// rap.on("send", this.onSend);
				this.ready = true;
				this.layout();
			}
		},
		onSend : function() {
			console.log("ipam-rap...onSend!!");
		},
		setSize : function(size) {
			this._size = size;
		},
		destroy : function() {
			// rap.off("send", this.onSend);
			this.element.parentNode.removeChild(this.element);
		},

		layout : function() {
			if (this.ready) {
				var area = this.parent.getClientArea();
				this.element.style.left = area[0] + "px";
				this.element.style.top = area[1] + "px";
				this.element.style.width = area[2] + "px";
				this.element.style.height = area[3] + "px";
			}
		},
		
		loadData : function(obj){
			var content = document.createElement("div");
			$(content).css({"overflow":"hidden"});
			var itemArr = obj.array;
			var itemEleStr = "";
			$.each(itemArr,function(index,itemm){
				itemEleStr += "<div class='div2' style='background:"+itemm.color+";' title='"+itemm.ip+"'>"
						+ "<div style='width:15px;height:15px;margin-left:33px;'>"
						+ "<img style='width:100%;height:100%;' title='"+itemm.status+"' src='rwt-resources/ipamjs/images/"+itemm.status+".png' />"
						+ "</div>"
						+ "<div style='text-align:center'>"
						+ "<span class='inside' style='color:#fff;font-weight:bold'>"+itemm.ip.split(".")[3]+"</span>"
						+ "</div>"
						+ "</div>";
			});
			var oldcolor = null;
			$(content).append(itemEleStr);
			$(content).find(".div2").css({"margin-left":"1px","margin-top":"1px","border":"1px solid #3d85c6","width":"50px","float":"left","height":"50px","cursor":"pointer"});
			$(this.element).append(content);
			$(this.element).on("mouseover",".div2",function(evt){
				oldcolor = $(this).css("background-color");
				$(this).css("background-color","#0c343d");
			});
			$(this.element).on("mouseout",".div2",function(evt){
				$(this).css("background-color",oldcolor);
				oldcolor = null;
			});
			$(this.element).on("click",".div2",function(evt){
				alert('IP:'+$(this).attr("title")+'---'+'status:'+$(this).find("img").attr("title"));
			});
		}
	};

	var bind = function(context, method) {
		return function() {
			return method.apply(context, arguments);
		};
	};

	var bindAll = function(context, methodNames) {
		for (var i = 0; i < methodNames.length; i++) {
			var method = context[methodNames[i]];
			context[methodNames[i]] = bind(context, method);
		}
	};

	var async = function(context, func) {
		window.setTimeout(function() {
			func.apply(context);
		}, 0);
	};

	var now = Date.now || function() {
    return new Date().getTime();
  };

}());
