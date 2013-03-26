package com.snda.youni.taskweb.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AppSettings class provides a convenient way to access the app configuration.
 * 
 * This class can be initialized by setting the config file when the app starts.
 * 
 * It implements the Singleton Patterns and the instance can be got via the static method {@link #getInstance()}
 * 
 * @author liangwenzheng@snda.com
 * @Date Mar 7, 2012
 */
public class AppSettings {

	private static final Logger logger = LoggerFactory.getLogger(AppSettings.class);

	private static final String DEFAULT_CHARSET = "utf-8";

	private static final AppSettings instance = new AppSettings();

	private Properties properties = new Properties();

	private AppSettings() {
	}

	public static AppSettings getInstance() {
		return instance;
	}

	/**
	 * Clean up all the properties
	 */
	public void reset() {
		this.properties.clear();
		this.properties = new Properties();
	}

	private String get(String key) {
		return properties.getProperty(key);
	}

	/**
	 * Get the string value associated with the given key, if the key is not found, return the provided defaultValue.
	 */
	public String getString(String key, String defaultValue) {
		String value = get(key);
		return value == null ? defaultValue : value;
	}

	/**
	 * Get the integer value associated with the given key, if the key is not found, return the provided defaultValue.
	 */
	public int getInt(String key, int defaultValue) {
		String value = get(key);
		if (value == null) {
			return defaultValue;
		} else {
			try {
				return Integer.parseInt(value);
			} catch (NumberFormatException e) {
				logger.error("Caught exception when parsing integer. Key:[{}]", key);
				return defaultValue;
			}
		}
	}

	/**
	 * Get the long value associated with the given key, if the key is not found, return the provided defaultValue.
	 */
	public long getLong(String key, long defaultValue) {
		String value = get(key);
		if (value == null) {
			return defaultValue;
		} else {
			try {
				return Long.parseLong(value);
			} catch (NumberFormatException e) {
				logger.error("Caught exception when parsing long. Key:[{}]", key);
				return defaultValue;
			}
		}
	}

	/**
	 * Get the boolean value associated with the given key, if the key is not found, return the provided defaultValue.
	 */
	public boolean getBoolean(String key, boolean defaultValue) {
		String value = get(key);
		if (value == null) {
			return defaultValue;
		} else {
			return BooleanUtils.toBoolean(value);
		}
	}

	/**
	 * Get a group of string values associated with the key which starts with the given prefix
	 * 
	 * @param prefix
	 * @return a group of string values, return empty list if no target values is found.
	 */
	public List<String> getStringByPrefix(String prefix) {
		List<String> values = new ArrayList<String>();
		for (Object key : properties.keySet()) {
			if (key.toString().startsWith(prefix)) {
				values.add(properties.getProperty(key.toString()));
			}
		}
		return values;
	}

	/**
	 * Initialize the app configuration based on the given config file paths.
	 * 
	 * The config file path is strongly recommanded as file name in the classpath.
	 * 
	 * @param configFilePaths
	 *            - a group paths of config file
	 */
	public void init(String... configFilePaths) {
		for (String configFilePath : configFilePaths) {
			Properties properties = loadProperties(configFilePath);
			if (properties != null) {
				addProperties(properties);
			}
		}
	}

	private void addProperties(Properties properties) {
		for (Object key : properties.keySet()) {
			if (this.properties.containsKey(key)) {
				logger.error("Duplicate key. Key:[{}]", key);
				continue;
			} else {
				String value = properties.getProperty((String) key).trim();
				this.properties.put(key, value);
				logger.info("Load config:[{}={}]", key, value);
			}
		}
	}

	private Properties loadProperties(String configFilePath) {
		if (StringUtils.isBlank(configFilePath)) {
			logger.error("Config file path is blank.");
			return null;
		}
		Properties properties = new Properties();
		File file = new File(configFilePath);
		try {
			InputStreamReader reader = null;
			if (file.exists()) {
				reader = new InputStreamReader(new FileInputStream(file), DEFAULT_CHARSET);
				logger.debug("Load config from a file.");
			} else {
				reader = new InputStreamReader(AppSettings.class.getResourceAsStream("/" + configFilePath),
						DEFAULT_CHARSET);
				logger.debug("Load config from the resource.");
			}
			properties.load(reader);
			return properties;
		} catch (Exception e) {
			logger.error("Caught exception when loading config. Config file path:[{}]. Error:[{}]", configFilePath,
					e.getMessage());
			return null;
		}
	}
}