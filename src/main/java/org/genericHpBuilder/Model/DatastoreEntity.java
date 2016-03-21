package org.genericHpBuilder.Model;

import com.googlecode.objectify.annotation.Id;
import com.googlecode.objectify.annotation.Index;

import java.io.Serializable;

/**
 * Abstract super class for all datastore entities
 */
public abstract class DatastoreEntity implements Persistent, Serializable{

    @Id
    protected Long id;

    @Index
    protected boolean deleted;

    /**
     *
     */
    protected transient int writeCount = 0;

    /**
     *
     */
    public final boolean isDirty() {
        return writeCount != 0;
    }

    /**
     *
     */
    public final void resetWriteCount() {
        writeCount = 0;
    }

    /**
     *
     */
    public final void incWriteCount() {
        writeCount++;
    }


    public final boolean isDeleted() {
        return deleted;
    }

    public final void setDeleted(boolean deleted) {
        this.deleted = deleted;
        incWriteCount();
    }
}
