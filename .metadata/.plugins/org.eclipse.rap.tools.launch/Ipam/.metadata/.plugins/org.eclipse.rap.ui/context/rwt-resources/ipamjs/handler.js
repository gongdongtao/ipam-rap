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
		this.element.style.border = '1px solid grey';
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
			(this.element && this.element.parentNode) ? this.element.parentNode.removeChild(this.element): null;
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
		
		loadData : function(){
			var content = document.createElement("div");
			$(content).addClass("content");
			//$(content).css({"padding":"5px"});
			$(this.element).append(content);
			var item = null;
			for(var i=0;i<255;i++){
				item = document.createElement("div");
				$(content).append(item);
				$(item).addClass("items");
				$(item).append("<span class='inside' style='color:#fff'>."+(i+1)+"</span>");
//				$(item).mouseover(function(){
//					$(this).css("background-color","grey");
//				});
//				$(item).mouseout(function(){
//					$(this).css("background-color","green");
//				});
			}
			$(content).children().css({"margin-left":"1px","margin-top":"1px","border":"1px solid blue","width":"50px","height":"50px","float":"left","text-align":"center","line-height":"50px","background":"green"});
			$(content).children().mouseover(function(){
				$(this).css("background-color","grey");
			});
			$(content).children().mouseout(function(){
				$(this).css("background-color","green");
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
