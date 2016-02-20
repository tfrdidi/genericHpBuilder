package org.genericHpBuilder.Controller;

import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;
import org.genericHpBuilder.Model.TextFile;

/**
 * Service class to configure the Objectify service
 */
public class OfyService {

	/**
	 * Register all entities at startup
	 */
	static {
		factory().register(TextFile.class);
	}

	public static Objectify ofy() {
		return ObjectifyService.ofy();
	}

	public static ObjectifyFactory factory() {
		return ObjectifyService.factory();
	}
}
