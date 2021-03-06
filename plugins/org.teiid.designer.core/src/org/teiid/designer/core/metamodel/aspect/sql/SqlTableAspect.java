/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.metamodel.aspect.sql;

import java.util.Collection;
import org.eclipse.emf.ecore.EObject;

/**
 * SqlTableAspect
 *
 * @since 8.0
 */
public interface SqlTableAspect extends SqlColumnSetAspect {

    // These are mapping types for which the table can be atarget.
    interface MAPPINGS {
        public static final int SQL_TRANSFORM = 1;
        public static final int TREE_TRANSFORM  = 2;
    }

    // prefix on the name of the materialized table
    final String MATERIALIZED_VIEW_PREFIX = "MV"; //$NON-NLS-1$
    // suffix on the name of the materialized staging table
    final String STAGING_TABLE_SUFFIX = "ST"; //$NON-NLS-1$

    /**
     * Check if UPDATE operations are supported on the table
     * @param eObject The <code>EObject</code> for which update prop is obtained 
     * @return true if the table can be used in an UPDATE
     */
    boolean supportsUpdate(EObject eObject);

    /**
     * Check if table represents a table in a virtual model
     * @return true if the table is virtual
     */
    boolean isVirtual(EObject eObject);

    /**
     * Check if table represents a system table
     * @return true if the table is system table
     */
    boolean isSystem(EObject eObject);

    /**
     * Check if table represents a materialized view
     * @return true if the table is materialized view
     */
    boolean isMaterialized(EObject eObject);   
    
    /**
     * Check if table represents a global temporary table
     * @return true if the table is temporary table
     */
    boolean isGlobalTempTable(EObject eObject);    


    /**
     * Return the table type of this table
     * @see org.teiid.designer.metadata.runtime.MetadataConstants.TABLE_TYPES
     * @return a int value from the available table types
     */
    int getTableType(EObject eObject);
    
    /**
     * Get the materialized view table reference ID
     * @return the string ID
     */
    String getMaterializedTableId(EObject eObject);    

    /**
     * Get a list of <code>EObject</code>s objects for the indexes in the table
     * @param eObject The <code>EObject</code> for which indexes are obtained 
     * @return a list of <code>EObject</code>s
     */
    Collection getIndexes(EObject eObject);

    /**
     * Get a list of <code>EObject</code>s objects for the unique keys in the table
     * @param eObject The <code>EObject</code> for which unique keys are obtained 
     * @return a list of <code>EObject</code>s
     */
    Collection getUniqueKeys(EObject eObject);

    /**
     * Get a list of <code>EObject</code>s objects for the foreign keys in the table
     * @param eObject The <code>EObject</code> for which foreign keys are obtained 
     * @return a list of <code>EObject</code>s
     */
    Collection getForeignKeys(EObject eObject);

    /**
     * Get a primary key <code>EObject</code> in the table
     * @param eObject The <code>EObject</code> for which primary key is obtained 
     * @return a <code>EObject</code> for the primary key
     */
    Object getPrimaryKey(EObject eObject);

    /**
     * Get a list of <code>EObject</code>s objects for the access patterns in the table
     * @param eObject The <code>EObject</code> for which access patterns are obtained 
     * @return a list of <code>EObject</code>s
     */
    Collection getAccessPatterns(EObject eObject);

    /**
     * Get a cardinality of the table in the table
     * @param eObject The <code>EObject</code> for which cardinality is obtained 
     * @return cardinality for the table.
     */
    int getCardinality(EObject eObject);
    
    /**
     * Check if the table is mappable for the given mapping type.
     * @param eObject The <code>EObject</code> whose mapability is checked
     * @param mappinType The mapping type being checked. 
     * @return true if mappable else false
     */
    boolean isMappable(EObject eObject, int mappingType);

    /**
     * Check if the table is can be a transformation source for the given target.
     * @param source The <code>EObject</code> intends to be a transformation source
     * @param target The <code>EObject</code> that is the target of the transformation
     * @return true if can be a source else false
     */
    boolean canBeTransformationSource(EObject source, EObject target);

    /**
     * Check if the table can accept the given transformation source.
     * @param target The <code>EObject</code> that is the target of the transformation
     * @param source The <code>EObject</code> intends to be a transformation source
     * @return true if can be a source else false
     */
    boolean canAcceptTransformationSource(EObject target, EObject source);

    /**
     * Sets updatability property on a table.
     * @param eObject The <code>EObject</code> for which update prop is set.
     * @param supportsUpdate Sets a boolean indicating if the table supports update 
     */
    void setSupportsUpdate(EObject eObject, boolean supportsUpdate);

}
