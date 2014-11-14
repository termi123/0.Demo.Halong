package com.example.demo.bean;

public class SyncBean {
	String _ID;
	String _CateName;
	String _DateSync;
	
	public SyncBean() {

	}

	public SyncBean(String _ID,String _CateName,String _DateSync) {
		this._ID = _ID;
		this._CateName = _CateName;
		this._DateSync = _DateSync;
	}

	public String get_ID() {
		return _ID;
	}

	public void set_ID(String _ID) {
		this._ID = _ID;
	}

	public String get_CateName() {
		return _CateName;
	}

	public void set_CateName(String _CateName) {
		this._CateName = _CateName;
	}

	public String get_DateSync() {
		return _DateSync;
	}

	public void set_DateSync(String _DateSync) {
		this._DateSync = _DateSync;
	}
	
	
}
