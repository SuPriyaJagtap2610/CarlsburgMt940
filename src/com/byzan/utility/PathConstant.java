package com.byzan.utility;

import java.util.LinkedHashMap;
import java.util.ResourceBundle;




public interface PathConstant {

	ResourceBundle rb = ResourceBundle.getBundle("Configurations");

	static String INPUT = rb.getString("INPUT").trim();
	static String OUTPUT = rb.getString("OUTPUT").trim();
	static String INPUT_BACKUP = rb.getString("INPUT_BACKUP").trim();
	static String ERROR = rb.getString("ERROR").trim();

	static String AuditLog = rb.getString("AuditLog").trim();

	
	

}
