package sk.fiit.robocup.library.init;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.bsf.BSFException;
import org.apache.bsf.BSFManager;
import org.apache.bsf.util.IOUtils;

/**
 *  Script.java
 *  
 *  Runs an interpreted code using apache bean scripting framework.
 *  As of february 2010, only JRuby support is implemented.
 *
 *@Title	Jim
 *@author	marosurbanec
 */

public class Script{
	
	static final BSFManager manager;
	
	private final Map<String, Object> declaredBeans;
	
	private final List<String> beansToBeFetched;
	
	private String code;
	
	static{
		BSFManager.registerScriptingEngine("ruby", "org.jruby.javasupport.bsf.JRubyEngine", new String[]{"rb"});
		manager = new BSFManager();
		try{
			manager.exec("ruby", "call_java.rb", -1, -1, "$:.unshift './scripts'");
		}
		catch (BSFException e) {
			e.getTargetException().printStackTrace();
		}
	}
	
	public static Script createScriptFrom(String filePath){
		try{
			String code = IOUtils.getStringFromReader(new InputStreamReader(new FileInputStream(filePath)));
			return new Script(code);
		}
		catch (IOException e){
			throw new IllegalArgumentException("Cannot find file "+filePath);
		}
	}
	
	public static Script createScript(String code){
		return new Script(code);
	}
	
	private Script(String text){
		this.code = text;
		this.declaredBeans = new HashMap<String, Object>();
		this.beansToBeFetched = new ArrayList<String>();
	}
	
	public void registerBean(String name, Object value){
		declaredBeans.put(name, value);
	}
	
	public void fetchBeans(String...names){
		beansToBeFetched.addAll(Arrays.asList(names));
	}
	
	public Map<String, Object> execute(){
		try{
			for (String name : declaredBeans.keySet())
				manager.declareBean(name, declaredBeans.get(name), declaredBeans.get(name).getClass());
			
			manager.exec("ruby", "call_java.rb", -1, -1, code);
			
			Map<String, Object> values = new HashMap<String, Object>();
			
			for(String name : beansToBeFetched)
				values.put(name, manager.lookupBean(name));
			
			return values;
		}
		catch (BSFException e) {
			e.printStackTrace();
			throw new IllegalStateException(e.getTargetException().getMessage());
		}
		finally{
			try{
			for (String name : declaredBeans.keySet())
				manager.undeclareBean(name);
			}catch(Exception e){}
		}
	}
}