package com.ledi.bean;

public class MoreInfo {
public String icon;
public String id;
public String info;
public String name;
public String sort;
public String size;
public String pathUrl;
public MoreInfo() {
	super();
	// TODO Auto-generated constructor stub
}
public MoreInfo(String icon,String id, String info, String name, String sort,
		String size,String pathUrl) {
	super();
	this.icon = icon;
	this.id = id;
	this.info = info;
	this.name = name;
	this.sort = sort;
	this.size = size;
	this.pathUrl=pathUrl;
}
public String getPathUrl(){
	return pathUrl;
}
public void setPathUrl(String pathUrl){
	this.pathUrl=pathUrl;
}
public String getIcon() {
	return icon;
}
public void setIcon(String icon) {
	this.icon = icon;
}
public String getId() {
	return id;
}
public void setId(String id) {
	this.id = id;
}
public String getInfo() {
	return info;
}
public void setInfo(String info) {
	this.info = info;
}
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getSort() {
	return sort;
}
public void setSort(String sort) {
	this.sort = sort;
}
public String  getSize() {
	return size;
}
public void setSize(String size) {
	this.size = size;
}

}
