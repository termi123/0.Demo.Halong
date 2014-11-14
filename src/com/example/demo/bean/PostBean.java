package com.example.demo.bean;

public class PostBean {
	String _ID = "";
	String _Cate_ID = "";
	String _TITLE = "";
	String _CONTENT = "";

	String _ADDRESS = "";
	String _PRICE = "";
	String _TIME = "";
	String _RATE = "";
	String _IMG = "";
	String _MAP = "";
	String _PHONE = "";
	int _FAV;
	
	public PostBean() {

	}

	public PostBean(String _ID, String _Cate_ID, String _TITLE,
			String _CONTENT, String _ADDRESS, String _PRICE, String _TIME,
			String _RATE, String _IMG, String _MAP, String _PHONE) {
		this._ID = _ID;
		this._Cate_ID = _Cate_ID;
		this._TITLE = _TITLE;
		this._CONTENT = _CONTENT;

		this._ADDRESS = _ADDRESS;
		this._PRICE = _PRICE;
		this._TIME = _TIME;
		this._RATE = _RATE;
		this._IMG = _IMG;
		this._MAP = _MAP;
		this._PHONE = _PHONE;

	}

	
	public int get_FAV() {
		return _FAV;
	}

	public void set_FAV(int _FAV) {
		this._FAV = _FAV;
	}

	public String get_ADDRESS() {
		return _ADDRESS;
	}

	public void set_ADDRESS(String _ADDRESS) {
		this._ADDRESS = _ADDRESS;
	}

	public String get_PRICE() {
		return _PRICE;
	}

	public void set_PRICE(String _PRICE) {
		this._PRICE = _PRICE;
	}

	public String get_TIME() {
		return _TIME;
	}

	public void set_TIME(String _TIME) {
		this._TIME = _TIME;
	}

	public String get_RATE() {
		return _RATE;
	}

	public void set_RATE(String _RATE) {
		this._RATE = _RATE;
	}

	public String get_IMG() {
		return _IMG;
	}

	public void set_IMG(String _IMG) {
		this._IMG = _IMG;
	}

	public String get_MAP() {
		return _MAP;
	}

	public void set_MAP(String _MAP) {
		this._MAP = _MAP;
	}

	public String get_PHONE() {
		return _PHONE;
	}

	public void set_PHONE(String _PHONE) {
		this._PHONE = _PHONE;
	}

	public String get_ID() {
		return _ID;
	}

	public void set_ID(String _ID) {
		this._ID = _ID;
	}

	public String get_Cate_ID() {
		return _Cate_ID;
	}

	public void set_Cate_ID(String _Cate_ID) {
		this._Cate_ID = _Cate_ID;
	}

	public String get_TITLE() {
		return _TITLE;
	}

	public void set_TITLE(String _TITLE) {
		this._TITLE = _TITLE;
	}

	public String get_CONTENT() {
		return _CONTENT;
	}

	public void set_CONTENT(String _CONTENT) {
		this._CONTENT = _CONTENT;
	}

}
