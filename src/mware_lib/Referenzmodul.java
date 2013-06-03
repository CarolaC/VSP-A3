package mware_lib;

import java.util.Map;
import java.util.HashMap;

public class Referenzmodul {

	private Map<Object, Skeleton> skeletons;
	
	public Referenzmodul() {
		this.skeletons = new HashMap<Object, Skeleton>();
	}
	
	public Skeleton getSkeleton(Object obj) {
		return this.skeletons.get(obj);
	}
	
	public void putSkeleton(Object obj, Skeleton skeleton) {
		this.skeletons.put(obj, skeleton);
	}
	
}
