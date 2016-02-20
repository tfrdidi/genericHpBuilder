package org.genericHpBuilder.Controller;

import org.genericHpBuilder.Model.Persistent;

import java.util.Collection;
import java.util.List;
import java.util.logging.Logger;

/**
 * An ObjectManager creates/reads/updates/deletes Persistent (objects) from Google Datastore. It is an abstract
 * superclass that relies an inheritance interface and the Persistent interface.
 */
public abstract class ObjectManager {

	private static final Logger log = Logger.getLogger(ObjectManager.class.getName());


	/**
	 * Reads the first Entity with the given key in the Datastore
	 */
	protected <E> E readObject(Class<E> type, Long id) throws IllegalArgumentException {
		assertIsNonNullArgument(type, "type");
		assertIsNonNullArgument(id, "id");

		log.info("Load Type " + type.toString() + " with ID " + id + " from datastore.");
		return OfyService.ofy().load().type(type).id(id).now();
	}

	/**
	 *
	 */
	protected void assertIsNonNullArgument(Object arg, String label) {
		if (arg == null) {
			throw new IllegalArgumentException(label + " should not be null");
		}
	}

	/**
	 * Reads the first Entity with the given key in the Datastore
	 */
	protected <E> E readObject(Class<E> type, String id) throws IllegalArgumentException {
		assertIsNonNullArgument(type, "type");
		assertIsNonNullArgument(id, "id");

		log.info("Load Type " + type.toString() + " with ID " + id + " from datastore.");
		return OfyService.ofy().load().type(type).id(id).now();
	}

	/**
	 * Reads an Entity of the specified type where the wanted parameter has the given name, e.g. readObject(User.class,
	 * "emailAddress", "name@provider.com").
	 */
	protected <E> E readObject(Class<E> type, String parameterName, Object value) {
		assertIsNonNullArgument(type, "type");
		assertIsNonNullArgument(parameterName, "parameterName");
		assertIsNonNullArgument(value, "value");

		log.info("Load Type " + type.toString() + " with parameter " +
						parameterName + " == " + value + " from datastore.");

		return OfyService.ofy().load().type(type).filter(parameterName, value).first()
				.now();
	}

	/**
	 * Reads all Entities of the specified type, e.g. readObject(User.class) to get a list of all clients
	 */
	protected <E> void readObjects(Collection<E> result, Class<E> type) {
		assertIsNonNullArgument(result, "result");
		assertIsNonNullArgument(type, "type");

		log.info("Datastore: load all entities of type " + type.getName());
		List<E> objects = OfyService.ofy().load().type(type).list();
		log.info("Datastore: number of loaded objects: " + objects.size());
		result.addAll(objects);
	}

	/**
	 * Reads all Entities of the specified type, where the given property matches the wanted value e.g.
	 * readObject(User.class) to get a list of all clients
	 */
	protected <E> void readObjects(Collection<E> result, Class<E> type, String propertyName, Object value) {
		assertIsNonNullArgument(result, "result");
		assertIsNonNullArgument(type, "type");
		assertIsNonNullArgument(propertyName, "propertyName");
		assertIsNonNullArgument(value, "value");

		log.info("Datastore: Load all Entities of type " + type.toString() + " where parameter "
						+ propertyName + " = " + value.toString() + " from datastore.");
		List<E> objects = OfyService.ofy().load().type(type).filter(propertyName, value).list();
		log.info("Datastore: number of loaded objects: " + objects.size());
		result.addAll(objects);
	}

	/**
	 * Updates all entities of the given collection in the datastore.
	 */
	protected void updateObjects(Collection<? extends Persistent> collection) {
		for (Persistent object : collection) {
			updateObject(object);
		}
	}

	/**
	 * Updates the given entity in the datastore.
	 */
	protected void updateObject(Persistent object) {
		writeObject(object);
	}

	/**
	 * Writes the given entity to the datastore.
	 */
	protected void writeObject(Persistent object) {
		assertIsNonNullArgument(object, "object");

		if (object.isDirty()) {
			log.info("Datastore: Write object of type " + object.getClass().getName());
			OfyService.ofy().save().entity(object).now();
			updateDependents(object);
			object.resetWriteCount();
		} else {
			log.info("Datastore: No need to update object " + object);
		}
	}

	/**
	 * Updates all dependencies of the object.
	 */
	protected void updateDependents(Persistent object) {
		// overwrite if your object has additional dependencies
	}

	/**
	 * Deletes the given entity from the datastore.
	 */
	protected <E> void deleteObject(E object) {
		assertIsNonNullArgument(object, "object");

		log.config("Datastore: delete entity" + object.toString());
		OfyService.ofy().delete().entity(object).now();
	}

	/**
	 * Deletes all entities of the type that have a property with the specified value, e.g.
	 * deleteObjects(PhotoCase.class, "wasDecided", true) to delete all cases that have been decided.
	 */
	protected <E> void deleteObjects(Class<E> type, String propertyName, Object value) {
		assertIsNonNullArgument(type, "type");
		assertIsNonNullArgument(propertyName, "propertyName");
		assertIsNonNullArgument(value, "value");

		log.info("Datastore: delete entities of type " + type
						+ " where property " + propertyName + " == " + value);
		List<com.googlecode.objectify.Key<E>> keys = OfyService.ofy().load().type(type).
				filter(propertyName, value).keys().list();
		OfyService.ofy().delete().keys(keys);
	}

}
