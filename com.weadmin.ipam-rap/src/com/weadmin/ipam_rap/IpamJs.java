package com.weadmin.ipam_rap;

import java.util.ArrayList;

import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.swt.widgets.Composite;

public class IpamJs extends SVWidgetBase{

	private static final long serialVersionUID = -7580109674486263430L;

	public IpamJs(Composite parent, int style) {
		super(parent, style);
	}

	@Override
	protected void handleSetProp(JsonObject properties) {
	}

	@Override
	protected void handleCallMethod(String method, JsonObject parameters) {
	}

	@Override
	protected void handleCallNotify(String event, JsonObject parameters) {

	}

	@Override
	protected String getWidgetName() {
		return "ipamjs";
	}
	
	public void loadData(JsonArray itemArr){
		JsonObject json = new JsonObject();
		json.set("array", itemArr);
		super.callRemoteMethod("loadData", json);
	}

	@Override
	protected ArrayList<CustomRes> getCustomRes() {
		ArrayList<CustomRes> res = new ArrayList<CustomRes>();
		res.add(new CustomRes("images/good.png", false, false));
		res.add(new CustomRes("images/error.png", false, false));
		res.add(new CustomRes("images/unconn.png", false, false));
		res.add(new CustomRes("images/warning.png", false, false));
		res.add(new CustomRes("jquery.js", true, false));
		res.add(new CustomRes("handler.js", true, false));
		return res;
	}

}
