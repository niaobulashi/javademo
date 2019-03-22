package com.niaobulashi.excel.exception;

/**
 * Excel异常
 * @author lisuo
 *
 */
public class ExcelException extends RuntimeException{

	private static final long serialVersionUID = 3240288821877252548L;

	public ExcelException(String message) {
		super(message);
	}

	public ExcelException(Throwable cause) {
		super(cause);
	}

}
