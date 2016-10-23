package com.cn.dyl.util;

public class DownloadException extends RuntimeException {
	
	private static final long serialVersionUID = 622298471524310906L;

	public DownloadException() {
		super();
	}

	public DownloadException(String message) {
		super(message);
	}

	public DownloadException(Throwable cause) {
		super(cause);
	}

	public DownloadException(String message, Throwable cause) {
		super(message, cause);
	}
}
