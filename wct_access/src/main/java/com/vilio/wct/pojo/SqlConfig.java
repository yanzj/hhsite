package com.vilio.wct.pojo;

public class SqlConfig {

	private String sqlid;
	private String sqlname;
	private String sql;

	public String getSqlid() {
		return sqlid;
	}

	public void setSqlid(String sqlid) {
		this.sqlid = sqlid;
	}

	public String getSqlname() {
		return sqlname;
	}

	public void setSqlname(String sqlname) {
		this.sqlname = sqlname;
	}

	public String getSql() {
		return sql;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public SqlConfig(String sqlid, String sqlname, String sql) {
		super();
		this.sqlid = sqlid;
		this.sqlname = sqlname;
		this.sql = sql;
	}

	public SqlConfig() {
		super();
	}

}
