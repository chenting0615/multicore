package edu.ucf.cs.multicore.project.Utility;


import java.io.File;

public class Constants {
	/**
	 * Following constants are general in this package
	 */
	public static final int SUCCESS = 0;
	public static final int FAILURE = -1;
	public static final int ZERO = 0;
	public static final int ONE = 1;
	public static final int TWO = 2;
	public static final int THREE = 3;
	public static final int MINUS_ONE = -1;
	public static final int MINUS_TWO = -2;
	public static final int MINUS_THREE = -3;
	public static final char CHR_YES = 'y';
	public static final char CHR_NO = 'n';
	public static final char SPACE = ' ';
	public static final String STR_YES = "y";
	public static final String STR_NO = "n";
	public static final String STR_SPACE = " ";
	public static final String STR_HASH = "#";
	public static final String STR_EQUAL = "=";
	public static final String STR_ERROR = "Error:";
	public static final String STR_SEMICOLON = ";";
	public static final String STR_COLON = ":";
	public static final String STR_HYPHEN = "-";
	public static final String CONF_COMMENT = STR_HASH;
	/*
	 * Following constants are valid values for status
	 */
	public static final char STATUS_ENABLE = CHR_YES;
	public static final char STATUS_DISABLE = CHR_NO;
	public static final char STATUS_UNKNOWN = SPACE;
	/*
	 * Following constants are values for various attributes or paramenters from
	 * xml file
	 */
	public static final boolean ENABLED = true;
	public static final boolean DISABLED = false;
	public static final boolean OPTIONAL = true;
	public static final boolean NOT_OPTIONAL = false;
	public static final boolean DEPENDENT = true;
	public static final boolean NOT_DEPENDENT = false;
	public static final boolean PRERUNON = true;
	public static final boolean PRERUNOFF = false;
	public static final boolean POSTRUNON = true;
	public static final boolean POSTRUNOFF = false;
	public static final int INVALID_DEPENDENTID = FAILURE;
	public static final int INVALID_ID = FAILURE;
	public static final String STR_RUNSON_SUCCESS = "s";
	public static final String STR_RUNSON_FAILURE = "f";
	public static final String STR_OPTIONFOREXTRAFILES = "e";
	public static final String STR_OPTIONFORUSAGE = "h";
	public static final String STR_OPTIONFORCONFFILES = "f";
	public static final String STR_OPTIONFORXMLFILES = "x";
	public static final int INT_RUNSON_SUCCESS = SUCCESS;
	public static final int INT_RUNSON_FAILURE = FAILURE;
	public static final int INVALID_RUNSON = ONE;
	/*
	 * Following constants are parsing command
	 */
	public static final String DOUBLE_DOLLAR = "$$";
	/*
	 * Following constants are for positioning in string
	 */
	public static final int FIRST_POSITION = ZERO;
	public static final int SECOND_POSITION = ONE;
	public static final int REMOVE_LASTCHAR = ONE;
	public static final int DRIVE_LENGTH = TWO;
	
	public static final String OS_ALL = "all";
	public static final String OS_WIN = "windows";
	public static final String OS_UNIX = "unix";
	public static final String WINDOWS_SHELL = "cmd /c ";
	public static final String UNIX_SHELL = "sh -a ";
	public static final String WIN_COPY = "copy";
	public static final String UNIX_COPY = "cp";
	public static final String TAR_COMMAND = "tar -cvf ";
	public static final String TAR_EXTENTION = ".tar";
	public static final String CHANGE_DIRECTORY = "cd ";
	public static final String ALL_OS_GETPROPERTY_PARAM = "os.name";
	public static final String ALL_NEWLINE_GETPROPERTY_PARAM = "line.seperator";
	public static final String CURR_DIRECTORY = ".";
	public static final String BACK_DIRECTORY = "..";
	public static final String TEMP_DIRECTORY = "tmp";
	public static final String LOG_DIRECTORY = "log";
	public static final String REPORT_DIRECTORY = "report";
	public static final String PACKAGE_DIRECTORY = "package";
	public static final String RESOURCE_DIRECTORY = "resources";
	public static final String CONF_DIRECTORY = "conf";
	public static final String UTILS_DIRECTORY = "utils";
	public static final String GUI_DIRECTORY = "gui";
	public static final String DU_DIRECTORY = "DU";
	public static final String PARENT_DIRECTORY = "..";
	public static final String FILETYPE_XML = "xml";
	public static final String UNIX_SRICPT_EXTENSION = ".sh";
	public static final String WIN_SRICPT_EXTENSION = ".bat";
	public static final String REPORT_EXTENSION = ".txt";
	public static final String REPORT_HTML_EXTENSION = ".html";
	public static final String REPORT_XML_EXTENSION = ".xml";
	public static final String ECHO_OFF = "@echo off";
	public static final String TEMP_CMD_FILEPATH = TEMP_DIRECTORY;
	public static final String DEF_XML_REPORTNAME= "AutomationReport";
	public static final String DEF_DEBXML_REPORTNAME= "AutomationDebugReport";
	public static final String DEF_SILKOUTPUT_REPORTNAME= "output";

	public static final String DU_LOG_PROPERTY_FILE = Constants.LOG_DIRECTORY + File.separator + "du_logger.properties";

	public static final String DEFAULT_TEXT_REPORT_FILEPATH = Constants.REPORT_DIRECTORY;
	public static final String DEFAULT_GLOBALDATA_FILEPATH = Constants.RESOURCE_DIRECTORY;
	public static final String GLOBAL_DATA_FILE = DEFAULT_GLOBALDATA_FILEPATH + File.separator + "Global_Data.xml";
	public static final String I18N_RESOURCE_STRING_FILE = RESOURCE_DIRECTORY + File.separator + "du_messages";
	public static final String DTD_FILENAME = "TH_XMLFormat.dtd";
	/*
	 * Following constants are return code from initialize
	 */
	public static final int INIT_SUCCESS = SUCCESS;
	public static final int INIT_FILLERROR = MINUS_ONE;
	public static final int INIT_PARSINGERROR = MINUS_TWO;
	public static final int INIT_SAXERROR = MINUS_THREE;
	public static final int INIT_XMLERROR = ONE;
	public static final int INIT_DUPLICATECONFIG = TWO;
	public static final int INIT_PARAMMATCHERROR = THREE;
	/*
	 * Following constants char as STRING
	 */
	public static final String EMPTY_STRING = "";
	public static final String EQUALTO = "=";
	public static final String DOUBLE_QUOTE = "\"";
	public static final String TRIPLE_TAB = "\t\t\t";
	public static final String DOUBLE_TAB = "\t\t";
	public static final String SINGLE_TAB = "\t";
	public static final String UNIX_NEWLINE = "\r\n";
	public static final String WIN_NEWLINE = "\n";
	public static final String STR_BACKSLASH = "\\";
	public static final char CHR_NEWLINE = '\n';
	public static final char CHR_TAB = '\t';
	public static final char CHR_SPACE = ' ';
	public static final char CHR_ENTER = '\r';
	/*
	 * Following constants for node types of elements
	 */
	public static final int XML_NODE_TYPE_ELEMENT = ONE;
	public static final int XML_NODE_TYPE_TEXT = THREE;
	/*
	 * Following constants are code to initialize config file
	 */
	public static final String CONGIF_GLOBALDATA = "imglobaldata";
	public static final String CONGIF_CLASSMAP = "classmap";
	public static final String CONGIF_TESTXML = "testsuite";
	public static final String CONFIG_PRODUCT = "productname";
	public static final String CONFIG_TEMPFILE = "temp";
	public static final String CONFIG_TAG = "filename";
	public static final String GLOBALDATA_TAG = "data";
	// All the below constants are used to fetch the data from the main Startup
	// file
	public static final String STARTUP_GLOBALDATA_TAGNAME = "globaldata";
	public static final String STARTUP_MAPPINGDATA_TAGNAME = "mappingdata";
	public static final String STARTUP_TESTDATA_TAGNAME = "testdata";
	public static final String STARTUP_PRODUCTNAME_TAGNAME = "product";
	public static final String STARTUP_TEMPFILE_TAGNAME = "tempfile";
	/*
	 * Following constants for package names
	 */

	public static final int WAIT_PERIOD = 30000;
	/**
	 * 
	 * @author jbarde Error codes for the diagnostic utility
	 * 
	 */
	public static final int DU_ERR_OK = 0; /* No errors */
	public static final int DU_ERR_REPORTER_NO_DATA = -101; /*
															 * ResultCollection
															 * object wsa null
															 */
	public static final int DU_ERR_REPORTER_NO_CONTEXT = -102; /*
																 * Context
																 * object was
																 * null
																 */
	public static final int DU_ERR_REPORTMANAGER_PARTIAL_SUCCESS = -103; /*
																		 * Not
																		 * all
																		 * reporters
																		 * could
																		 * do
																		 * their
																		 * job
																		 * successfully
																		 */
	public static final int DU_ERR_REPORT_BAD_CONTEXT = -104; /*
															 * Context did not
															 * contain the
															 * required data
															 */
	public static final int DU_ERR_STEP_FAILED = -105; /*
														 * The suite result base
														 * on the test is
														 * failure
														 */
	public static final int DU_ERR_TEST_FAILED = -106; /*
														 * The test result base
														 * on the step is
														 * failure
														 */
	public static final int DU_ERR_SUITE_FAILED = -107; /*
														 * The suite result base
														 * on the test is
														 * failure
														 */
	public static final int DU_ERR_REPORTER_CANT_CREATE_REPORT = -108; /*
																		 * The
																		 * report
																		 * could
																		 * not
																		 * be
																		 * created
																		 * ,
																		 * general
																		 * error
																		 * , log
																		 * would
																		 * mention
																		 * the
																		 * exact
																		 * error
																		 */
	public static final int DU_ERR_REPORTMANAGER_NO_REPORTS = -109; /*
																	 * The
																	 * report
																	 * manager
																	 * has no
																	 * reports
																	 * to
																	 * delegate
																	 * reporting
																	 * step
																	 */
	public static final int DU_ERR_LOGGER_PROPERTY_FILE_NOT_FOUND = -110; /*
																		 * The
																		 * logger
																		 * properties
																		 * file
																		 * not
																		 * found
																		 */
	public static final int DU_ERR_CONTROLLER_NULL_REQUEST = -111; /*
																	 * The
																	 * request
																	 * object is
																	 * null
																	 */
	public static final int DU_ERR_EXECUTOR_REQUEST_NULL = -112; /*
																 * The request
																 * object is
																 * null
																 */
	public static final int DU_ERR_EXECUTOR_CONTEXT_NULL = -113; /*
																 * The context
																 * object is
																 * null
																 */
	public static final int DU_ERR_REPORTER_NO_REQUEST = -114; /*
																 * Request
																 * object was
																 * null for the
																 * reporter
																 */
	public static final int DU_ERR_CONTROLLER_NULL_DIGNOSTIC_XML_FILE = -115; /*
																			 * Diagnostic
																			 * file
																			 * argument
																			 * passed
																			 * to
																			 * the
																			 * function
																			 * was
																			 * null
																			 */
	public static final int DU_ERR_CONFIGKEY_NOTMATCHING = -116; /*
																 * Diagnostic
																 * file argument
																 * passed to the
																 * function was
																 * null
																 */
	public static final int TREAD_POOL_COUNT = 5; /* Thread pool count */

	
}
