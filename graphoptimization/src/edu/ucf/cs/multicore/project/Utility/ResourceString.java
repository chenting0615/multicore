package edu.ucf.cs.multicore.project.Utility;



import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.log4j.Level;

public class ResourceString {

	private static ResourceBundle resources;

	private static Locale locale;

	/**
	 * 
	 * This constructor will initialise Resource Bundle with locale specified.
	 */

	public static void init(Locale parentLocale)

	{

		locale = parentLocale;

		try

		{

			resources = ResourceBundle.getBundle(Constants.I18N_RESOURCE_STRING_FILE, locale);

			if (null == resources)

			{

				AppLogger.getInstance().log(Level.ERROR, "Error while reading the resourse bundle, " +

				"please check if right resource bundle for your locale is available");

			}

		}

		catch (MissingResourceException e)

		{

			System.out.println(e.getStackTrace());

			AppLogger.getInstance().log(Level.ERROR, e.getMessage());

		}

	}

	/**
	 * 
	 * This method will retrieve value of key passed from resource Bundle &
	 * returns back,
	 * 
	 * If not found then returns " "
	 * 
	 * 
	 * 
	 * @param key
	 * 
	 * @return String
	 */

	public static String getString(String key)

	{

		try

		{

			if (key == "success") {
				return "success";
			}
			return resources.getString(key);

		}

		catch (MissingResourceException e)

		{

			System.out.println(key);

			return getString("res_key_not_found");

		}

	}// public static String getString(String key)

	/**
	 * 
	 * This method will return locale object.
	 * 
	 * @return locale Locale
	 */

	public static Locale getLocale()

	{

		return locale;

	}

}
