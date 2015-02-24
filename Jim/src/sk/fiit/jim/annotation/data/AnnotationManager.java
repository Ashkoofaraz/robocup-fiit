package sk.fiit.jim.annotation.data;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import sk.fiit.jim.log.Log;
import sk.fiit.jim.log.LogType;

/**
 * 
 *  AnnotationManager.java
 *  
 *  Handles the loading of annotations and their relations to moves (skills)
 *  
 *@Title        Jim
 *@author       peterholak
 */
public class AnnotationManager {
	//mapuje nazov pohybu na zoznam anotacii pre tento pohyb
	private Map<String, List<Annotation>> annotations;
	//mapuje nazov anotacie na jej objekt
	private Map<String, Annotation> annotationIds;
	private List<Annotation> allAnnotations;
	private static AnnotationManager instance = new AnnotationManager();
	
	private LinkedList<String> lowSkills;
	
	private AnnotationManager() {
		annotationIds = new HashMap<String, Annotation>();
		annotations = new HashMap<String, List<Annotation>>();
		allAnnotations = new LinkedList<Annotation>(); 
		
		lowSkills = new LinkedList<String>();
	}
	
	
	
	/**
	 * Returns list of all annotations. 
	 *
	 * @return
	 */
	public List<Annotation>getAllAnnotations() {
		return this.allAnnotations;
	}


	/**
	 * Returns AnnotationManager instance to work with it. 
	 *
	 * @return
	 */
	public static AnnotationManager getInstance() {
		return instance;
	}
	
	/**
	 * Loads annotations from specified directory.  
	 *
	 * @param directory
	 * @throws IOException
	 */
	public void loadAnnotations(String directory) throws IOException {
		Log.log(LogType.INIT, "Loading annotations from " + directory);
		File directoryWithXmls = new File(directory);
		if (!directoryWithXmls.isDirectory())
			throw new IOException("Cannot open moves/annotations directory");
		
		File[] xmlFiles = directoryWithXmls.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.matches("^[a-zA-Z_0-9]{1,}[-]{1}[0-9]{1,}\\.xml$");
			}
		});
		
		for (File f : xmlFiles) {
			Log.log(LogType.INIT, "Loading annotation "+f.getName());

			try {
				Annotation a = XMLParser.parse(f);
								
				List<Annotation> moveAnnotList;
				if (annotations.containsKey(a.getName()) && lowSkills.contains(a.getName())) {
					annotationIds.put(a.getId(), a);
					allAnnotations.add(a);
					moveAnnotList = annotations.get(a.getName());
					Log.log(LogType.INIT, "Adding annotation "+a.getId()+" to move "+a.getName());
					moveAnnotList.add(a);
				}else if(lowSkills.contains(a.getName())){
					annotationIds.put(a.getId(), a);
					allAnnotations.add(a);
					moveAnnotList = new LinkedList<Annotation>();
					moveAnnotList.add(a);
					annotations.put(a.getName(), moveAnnotList);
					Log.log(LogType.INIT, "Adding annotation "+a.getId()+" to move "+a.getName());
				}else{
					Log.log(LogType.INIT, "Annotations for non-existent LowSkill "+a.getId()+" to move "+a.getName());
				}
				
			}catch (Exception e) {
				Log.error(LogType.INIT, "Cannot load annotation from " + f.getName());
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Loads annotations from specified directory.  
	 *
	 * @param directory
	 * @throws IOException
	 */
	public void loadLowSkills(String directory) throws IOException {
		Log.log(LogType.INIT, "Loading LowSkills from " + directory);
		File directoryWithXmls = new File(directory);
		if (!directoryWithXmls.isDirectory())
			throw new IOException("Cannot open /moves directory");
		
		File[] xmlFiles = directoryWithXmls.listFiles(new FilenameFilter(){
			public boolean accept(File dir, String name){
				return name.matches("^[a-zA-Z]{1,}_[a-zA-Z]{1,}.*\\.xml$");
			}
		});
		
		for (File f : xmlFiles) {
			Log.log(LogType.INIT, "Loading LowSkills "+f.getName());
			try {
				lowSkills.add(f.getName().replaceAll("\\.xml$", ""));
			}catch (Exception e) {
				Log.error(LogType.INIT, "Cannot load LowSkills from " + f.getName());
				e.printStackTrace();
			}
		}

	}
	
	/**
	 * Returns annotation by specified string. 
	 *
	 * @param move
	 * @return
	 */
	public List<Annotation> getAnnotations(String move) {
		return annotations.get(move);
	}
	
	/**
	 * Returns annotation by specified id. 
	 *
	 * @param id
	 * @return
	 */
	public Annotation getAnnotationById(String id) {
		return annotationIds.get(id);
	}
	
	
	/**
	 * Returns list Of LowSkills 
	 *
	 * @return
	 */
	public List<String> getLowSkills() {
		return lowSkills;
	}
	
}
