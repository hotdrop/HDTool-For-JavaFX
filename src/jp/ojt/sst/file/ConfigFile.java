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
 * this class is singleton design.
 *
 */
public class ConfigFile {
	
	private static final String FILE_NAME="ast.properties";
	
	private static ConfigFile instance = null;
	private Properties prop = null;

	private ConfigFile() {
		prop = new Properties();
		String appPath = System.getProperty("java.class.path");
		try(InputStream is = new FileInputStream(appPath + java.io.File.separator + FILE_NAME)) {
			prop.load(is);
		} catch (IOException ioe) {
			// TODO write error log
			throw new ASTException(ioe);
		}
	}
	
	public static synchronized ConfigFile getInstance() {
		if(instance == null) {
			instance = new ConfigFile();
		}
		return instance;
	}
	
	public void saveReferencePath(String refPath) {
		prop.setProperty("REFERENCE_PATH", refPath);
		String appPath = System.getProperty("java.class.path");
		try(OutputStream os = new FileOutputStream(appPath + java.io.File.separator + FILE_NAME)) {
			prop.store(os, "selected logFile absolute path by fileChooserDialog");
		} catch (IOException ioe) {
			// TODO write error log
			throw new ASTException(ioe);
		}
	}
	
	public String getReferencePath() {
		return prop.getProperty("REFERENCE_PATH");
	}
	
	public String getSearchWord() {
		return prop.getProperty("SEARCH_WORD");
	}
}
