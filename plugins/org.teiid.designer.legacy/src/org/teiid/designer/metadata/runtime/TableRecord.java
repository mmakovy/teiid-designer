/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */

package org.teiid.designer.metadata.runtime;

import java.util.Collection;
import java.util.List;

/**
 * TableRecord
 *
 * @since 8.0
 */
public interface TableRecord extends MetadataRecord  {
	
	public enum Type {
		Table,
		View,
		Document,
		XmlMappingClass,
		XmlStagingTable,
		MaterializedTable,
		/** Temporary from a Teiid Perspective - will not have a parent set */
		TemporaryTable {
			@Override
			public String toString() {
				return "TEMPORARY TABLE"; //$NON-NLS-1$
            }
        }
	}
    
    /**
     * Constants for perperties stored on a TableRecord 
     * @since 4.3
     */
    public interface TableRecordProperties {

        String ELEMENTS_IN_GROUP = "elementsInGroup";  //$NON-NLS-1$
        String INDEXES_IN_GROUP = "indexesInGroup";  //$NON-NLS-1$
        String UNIQUEKEYS_IN_GROUP = "uksInGroup";  //$NON-NLS-1$
        String FOREIGNKEYS_IN_GROUP = "fksInGroup";  //$NON-NLS-1$
        String ACCESS_PTTRNS_IN_GROUP = "accPttrnsInGroup";  //$NON-NLS-1$
        String QUERY_PLAN        = "queryPlan";  //$NON-NLS-1$
        String INSERT_PLAN       = "insertPlan";  //$NON-NLS-1$
        String UPDATE_PLAN       = "updatePlan";  //$NON-NLS-1$
        String DELETE_PLAN       = "deletePlan";  //$NON-NLS-1$
        String MAPPING_NODE_FOR_RECORD  = "mappingNodeForRecord";  //$NON-NLS-1$
        String TEMPORARY_GROUPS_FOR_DOCUMENT  = "tempGroupsForDocument";  //$NON-NLS-1$
        String SCHEMAS_FOR_DOCUMENT  = "schemasForDocument";  //$NON-NLS-1$
        
    }
    
    /**
     * Check if UPDATE operations are supported on the table
     * @return true if the table can be used in an UPDATE
     */
    boolean supportsUpdate();

    /**
     * Check if table represents a table in a virtual model
     * @return true if the table is virtual
     */
    boolean isVirtual();

    /**
     * Check if table represents a table in a physical model
     * @return true if the table is physical
     */
    boolean isPhysical();

    /**
     * Check if table represents a table in a system model
     * @return true if the table is system
     */
    boolean isSystem();
    
    /**
     * Check if table represents a materialized table
     * @return true if the table is materialized
     */
    boolean isMaterialized();
    
    /**
     * Check if table a temporary table
     * @return true if the table is a temporary table
     */
    boolean isTempTable();

    /**
     * Return the table type of this table
     * @see org.teiid.designer.metadata.runtime.MetadataConstants.TABLE_TYPES
     * @return a int value from the available table types
     */    
    int getTableType();

    @Deprecated
    /**
     * Get a list of identifiers for the columns in the table
     * @return a list of identifiers
     * @deprecated columnIDs are no longer stored in the record. Columns can be
     * retrieved by querying for all columns having the same qualified parent table name.
     */
    List getColumnIDs();

    /**
     * Get a list of identifiers for the indexes in the table
     * @return a list of identifiers
     */    
    Collection getIndexIDs();

    /**
     * Get a list of identifiers for the unique keys in the table
     * @return a list of identifiers
     */    
    Collection getUniqueKeyIDs();
    
    /**
     * Get a list of identifiers for the foreign keys in the table
     * @return a list of identifiers
     */    
    Collection getForeignKeyIDs();

    /**
     * Get a materialized table identifier for this table
     * @return an identifier for materialized table
     */
    Object getMaterializedTableID();

    /**
     * Get a materialized staging table identifier for this table
     * @return an identifier for materialized staging table
     */
    Object getMaterializedStageTableID();

    /**
     * Get a primary key identifier in the table
     * @return an identifier for the primary key
     */    
    Object getPrimaryKeyID();

    /**
     * Get a list of identifiers for the access patterns in the table
     * @return a list of identifiers
     */    
    Collection getAccessPatternIDs();
    
    /**
     * Get a cardinality of the table in the table
     * @return the cardinality
     */    
    int getCardinality();    

}
