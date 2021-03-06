/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.resource;

import org.eclipse.emf.ecore.EObject;
import org.teiid.core.designer.id.ObjectID;


/** 
 * @since 8.0
 */
public interface EProxyCacheHolder {

    /**
     * Returns the eProxy value to which this cache maps the specified key.  
     * Returns <tt>null</tt> if the cache contains no mapping for this key or
     * if the EResource is current loaded in which case there are no eProxy instances
     * associated with it.  
     * @param key key whose associated value is to be returned.
     * @return the value to which this cache maps the specified key, or
     *         <tt>null</tt> if the map contains no mapping for this key.
     */
    EObject getEProxy(ObjectID key);

}
