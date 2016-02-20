package org.genericHpBuilder;

import com.googlecode.objectify.ObjectifyService;
import com.googlecode.objectify.util.Closeable;
import org.genericHpBuilder.Controller.OfyService;
import org.junit.After;
import org.junit.rules.ExternalResource;

/**
 * Provides the environment for a test to use objecitfy to access the datastore
 */
public class RegisteredOfyEnvironmentProvider extends ExternalResource {

	private Closeable closeable;

	@Override
	protected void before() throws Throwable {
		// make sure the static register part is done
		OfyService.factory();

		closeable = ObjectifyService.begin();
	}

	@After
	public void tearDown() {
		closeable.close();
	}
}
