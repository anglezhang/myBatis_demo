package com.cyw.mammoth.model;

public class Add {
	
	private String id;//ID
    private String tname;//用户名
    private String tpwd;//密码

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    public String getTname() {
        return tname;
    }
    public void setTname(String tname) {
        this.tname = tname == null ? null : tname.trim();
    }

    public String getTpwd() {
        return tpwd;
    }
    public void setTpwd(String tpwd) {
        this.tpwd = tpwd == null ? null : tpwd.trim();
    }
}
