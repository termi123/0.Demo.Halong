package com.example.demo.bean;

public class CategoriesBean {
	String _ID;
	String _Name;
	String _DateSync;

	public CategoriesBean() {

	}

	public CategoriesBean(String _ID,String _Name,String _DateSync) {
		this._ID = _ID;
		this._Name = _Name;
		this._DateSync = _DateSync;
	}

	public String get_ID() {
		return _ID;
	}

	public void set_ID(String _ID) {
		this._ID = _ID;
	}

	public String get_Name() {
		return _Name;
	}

	public void set_Name(String _Name) {
		this._Name = _Name;
	}

	public String get_DateSync() {
		return _DateSync;
	}

	public void set_DateSync(String _DateSync) {
		this._DateSync = _DateSync;
	}

	
}
