package edu.ucf.cs.multicore.project.Utility;


import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class AppLogger {

	static

	{

		PropertyConfigurator.configure("du_logger.properties");

	}

	private static AppLogger applogger_instance;

	private static final Logger log = Logger.getLogger("multicorelog");;

	private AppLogger()

	{

	}

	public static synchronized AppLogger getInstance()

	{

		if (applogger_instance == null)

		{

			applogger_instance = new AppLogger();

		}

		return applogger_instance;

	}

	public void setLevel(final Level level)

	{

		log.setLevel(level);

	}

	public void log(final Level level, final String msg)

	{

		log.log(level, msg);

	}

}
