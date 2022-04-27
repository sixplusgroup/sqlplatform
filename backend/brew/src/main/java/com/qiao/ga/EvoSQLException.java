package com.qiao.ga;

public class EvoSQLException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EvoSQLException(String msg) {
		super(msg);
	}

	public EvoSQLException(Exception e) {
		super(e);
	}
}
