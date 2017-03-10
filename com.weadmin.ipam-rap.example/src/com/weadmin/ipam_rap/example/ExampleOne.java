package com.weadmin.ipam_rap.example;

import java.nio.channels.NonWritableChannelException;

import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.jface.layout.GridLayoutFactory;
import org.eclipse.rap.json.JsonArray;
import org.eclipse.rap.json.JsonObject;
import org.eclipse.rap.rwt.RWT;
import org.eclipse.rap.rwt.application.AbstractEntryPoint;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;

import com.weadmin.ipam_rap.*;

public class ExampleOne extends AbstractEntryPoint{

	private static final long serialVersionUID = 1L;

	@Override
	protected void createContents(Composite parent) {
		parent.setLayout(new GridLayout());

		Composite toolbar = new Composite(parent, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(2).equalWidth(true).margins(0, 0).spacing(0,0).applyTo(toolbar);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(toolbar);
		
		Composite toolbarleft = new Composite(toolbar, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(8).extendedMargins(5, 0, 5, 0).spacing(5,0).applyTo(toolbarleft);
		GridDataFactory.fillDefaults().align(SWT.FILL, SWT.FILL).grab(true, false).applyTo(toolbarleft);
		
		Composite toolbarright = new Composite(toolbar, SWT.NONE);
		GridLayoutFactory.fillDefaults().numColumns(3).margins(0, 0).spacing(3,0).applyTo(toolbarright);
		GridDataFactory.fillDefaults().align(SWT.RIGHT, SWT.FILL).grab(true, false).applyTo(toolbarright);
		
		Label blue = new Label(toolbarleft, SWT.NONE);
		blue.setText("<span style=\"width:20px; height:20px; background-color:#3d85c6;display:block;\"></span>");
		blue.setData(RWT.MARKUP_ENABLED, true);
		
		Label outline = new Label(toolbarleft, SWT.NONE);
		outline.setText("<span style=\"color:#204F85\"><b>离线IP</b></span>");
		outline.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		
		Label green = new Label(toolbarleft, SWT.NONE);
		green.setText("<span style=\"width:20px; height:20px; background-color:#4aac1e;display:block;\"></span>");
		green.setData(RWT.MARKUP_ENABLED, true);
		
		Label online = new Label(toolbarleft, SWT.NONE);
		online.setText("<span style=\"color:#204F85\"><b>在线IP</b></span>");
		online.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		
		Label yellow = new Label(toolbarleft, SWT.NONE);
		yellow.setText("<span style=\"width:20px; height:20px; background-color:yellow;display:block;\"></span>");
		yellow.setData(RWT.MARKUP_ENABLED, true);
		
		Label unreg = new Label(toolbarleft, SWT.NONE);
		unreg.setText("<span style=\"color:#204F85\"><b>未登记</b></span>");
		unreg.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		
		Label red = new Label(toolbarleft, SWT.NONE);
		red.setText("<span style=\"width:20px; height:20px; background-color:red;display:block;\"></span>");
		red.setData(RWT.MARKUP_ENABLED, true);
		
		Label inused = new Label(toolbarleft, SWT.NONE);
		inused.setText("<span style=\"color:#204F85\"><b>被占用</b></span>");
		inused.setData(RWT.MARKUP_ENABLED, Boolean.TRUE);
		inused.addMouseListener(new MouseAdapter() {

			private static final long serialVersionUID = 1L;

			@Override
			public void mouseUp(MouseEvent e) {
				//openTopoById(levelTopoId,filename[0]);
			}
		});
		
		Button button2 = new Button(toolbarright, SWT.PUSH);
		button2.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		button2.setText("纠正");
		button2.addSelectionListener(new SelectionAdapter() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		Button refresh = new Button(toolbarright, SWT.PUSH);
		refresh.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		refresh.setText("刷新");
		
		Button export = new Button(toolbarright, SWT.PUSH);
		export.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false));
		export.setText("全部导出");
		export.addSelectionListener(new SelectionAdapter() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				
			}
		});
		
		IpamJs ipjs = new IpamJs(parent, SWT.NONE);
		ipjs.setLayoutData(new GridData(GridData.FILL_BOTH));
		ipjs.loadData(getData());
		ipjs.addListener(SWT.Selection, new Listener() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void handleEvent(Event event) {
				JsonObject parameters = (JsonObject) event.data;
				System.out.println("IP:"+parameters.get("ip").asString()
						+"\nMac:"+parameters.get("mac").asString()
						+"\nDepartment:"+parameters.get("department").asString()
						+"\n------------------");
			}
		});
		
		refresh.addSelectionListener(new SelectionAdapter() {
			
			private static final long serialVersionUID = 1L;

			@Override
			public void widgetSelected(SelectionEvent e) {
				ipjs.refresh(getData());
			}
		});
	}
	
	public JsonArray getData(){
		String[] color = {"#3d85c6","#4aac1e","#4aac1e","#4aac1e","#4aac1e","#4aac1e","#4aac1e","#4aac1e"};
		String[] status = {"good","error","warning","unconn"};
		JsonObject item = null;
		JsonArray itemArr = new JsonArray();
		for(int i=0;i<255;i++){
			item = new JsonObject();
			item.add("color", color[(int) (Math.random()*8)]);
			String str = item.get("color").asString();
			if (str.equals(color[0])) {
				item.add("status", status[3]);
			}else{
				item.add("status", status[(int) (Math.random()*3)]);
			}
			item.add("ip", "192.168.10."+(i+1));
			item.add("mac", "5465656565");
			item.add("department", "kaifa");
			itemArr.add(item);
		}
		return itemArr;
	}
}
