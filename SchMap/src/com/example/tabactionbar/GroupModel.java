package com.example.tabactionbar;

public class GroupModel {

	private String name;
	private String location;
	private String type;
	private String style;
	private int begin_h, begin_m, end_h, end_m, capacity;
	private String am_pm;
	private boolean visible, priv;
	
	public GroupModel(String name, String location, String type, String style,
			int begin_h, int begin_m, int end_h, int end_m, int capacity,
			String am_pm, boolean visible, boolean priv) {
		this.name = name;
		this.location = location;
		this.type = type;
		this.style = style;
		this.begin_h = begin_h;
		this.begin_m = begin_m;
		this.end_h = end_h;
		this.end_m = end_m;
		this.capacity = capacity;
		this.am_pm = am_pm;
		this.visible = visible;
		this.priv = priv;
	}
	
	public String getName() {
		return name;
	}
	
	public String getLocation() {
		return location;
	}
	
	public String getType() {
		return type;
	}
	
	public String getStyle() {
		return style;
	}
	
	public String am_pm() {
		return am_pm;
	}
	
	public int getBegin_h() {
		return begin_h;
	}
	
	public int getBegin_m() {
		return begin_m;
	}
	
	public int getEnd_h() {
		return end_h;
	}
	
	public int getEnd_m() {
		return end_m;
	}
	
	public int getCap() {
		return capacity;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public boolean isPrivate() {
		return priv;
	}
}
