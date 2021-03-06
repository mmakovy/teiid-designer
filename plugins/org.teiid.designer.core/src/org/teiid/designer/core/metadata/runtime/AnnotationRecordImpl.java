/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.core.metadata.runtime;

import org.eclipse.emf.ecore.EObject;
import org.teiid.designer.core.index.IndexConstants;
import org.teiid.designer.core.metamodel.aspect.sql.SqlAnnotationAspect;


/**
 * AnnotationRecordImpl
 *
 * @since 8.0
 */
public class AnnotationRecordImpl extends org.teiid.designer.metadata.runtime.impl.AnnotationRecordImpl {

	private static final long serialVersionUID = 1615791785370971740L;

	/**
	 * Flags to determine if values have been set.
	 */
	private boolean descriptionSet;

    public AnnotationRecordImpl(final SqlAnnotationAspect sqlAspect, final EObject eObject) {
        super(new ModelerMetadataRecordDelegate(sqlAspect, eObject));
		setRecordType(IndexConstants.RECORD_TYPE.ANNOTATION);
		this.eObject = eObject;
	}

	private SqlAnnotationAspect getAnnotationAspect() {
		return (SqlAnnotationAspect) ((ModelerMetadataRecordDelegate)this.delegate).getSqlAspect();
	}

    //==================================================================================
    //                     I N T E R F A C E   M E T H O D S
    //==================================================================================

    /**
     * @see org.teiid.designer.metadata.runtime.AnnotationRecord#getDescription()
     */
    @Override
    public String getDescription() {
		if(this.eObject != null && !descriptionSet) {
			setDescription(getAnnotationAspect().getDescription((EObject)this.eObject));
		}
        return super.getDescription();
    }

    /**
     * @param string
     */
    @Override
    public void setDescription(final String string) {
		descriptionSet = true;
		super.setDescription(string);
    }

}
