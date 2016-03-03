package jp.ojt.sst.file;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import jp.ojt.sst.util.ASTException;

/**
 * ConfigFile(properties) Class 
 * This class is singleton design.
 *
 */
public class ConfigFile {
	
	/** Property file name */
	private static final String FILE_NAME="ast.properties";
	
	/** An instance of this class */
	private static ConfigFile instance = null;
	
	/** Proerty */
	private Properties prop = null;

	/**
	 * Load configFile(properties).
	 */
	private ConfigFile() {
		prop = new Properties();
		String appPath = System.getProperty("java.class.path");
		try(InputStream is = new FileInputStream(appPath + java.io.File.separator + FILE_NAME)) {
			prop.load(is);
		} catch (IOException ioe) {
			throw new ASTException(ioe);
		}
	}
	
	/**
	 * Return a single instance.
	 * @return an instance of this class
	 */
	public static synchronized ConfigFile getInstance() {
		if(instance == null) {
			instance = new ConfigFile();
		}
		return instance;
	}
	
	/**
	 * Store the reference dialog selected path to the property.
	 * @param refPath
	 */
	public void saveReferencePath(String refPath) {
		prop.setProperty("REFERENCE_PATH", refPath);
		String appPath = System.getProperty("java.class.path");
		try(OutputStream os = new FileOutputStream(appPath + java.io.File.separator + FILE_NAME)) {
			prop.store(os, "selected logFile absolute path by fileChooserDialog");
		} catch (IOException ioe) {
			throw new ASTException(ioe);
		}
	}
	
	/**
	 *  Return the ReferencePath get from the properties file.
	 * @return referencePath
	 */
	public String getReferencePath() {
		return prop.getProperty("REFERENCE_PATH");
	}
	
	/**
	 * Return the searchWord get from properties file.
	 * @return searchWord
	 */
	public String getSearchWord() {
		return prop.getProperty("SEARCH_WORD");
	}
}
