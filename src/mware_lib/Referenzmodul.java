package mware_lib;

import java.util.Map;
import java.util.HashMap;
import java.util.Map.Entry;

public class Referenzmodul {

	private Map<String, Skeleton> skeletons;
	
	public Referenzmodul() {
		this.skeletons = new HashMap<String, Skeleton>();
	}
	
	public Skeleton getSkeleton(String str) {
		return this.skeletons.get(str);
	}
	
	public void putSkeleton(String str, Skeleton skeleton) {
		this.skeletons.put(str, skeleton);
	}
	
	public void getAllSkeletons() {
		for (Entry<String, Skeleton> e : this.skeletons.entrySet()) {
			System.out.println(e.getKey() + ":" + e.getValue() + "\n");
		}
	}
	
}
