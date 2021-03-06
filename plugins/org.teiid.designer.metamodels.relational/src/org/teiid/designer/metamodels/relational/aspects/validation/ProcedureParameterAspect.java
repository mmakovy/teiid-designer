/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.relational.aspects.validation;

import org.teiid.designer.core.metamodel.aspect.MetamodelEntity;
import org.teiid.designer.core.validation.ValidationRuleSet;

/**
 * ProcedureParameterAspect
 *
 * @since 8.0
 */
public class ProcedureParameterAspect extends RelationalEntityAspect {

    /**
     * Construct an instance of ProcedureParameterAspect.
     * @param entity
     */
    public ProcedureParameterAspect(MetamodelEntity entity){
        super(entity);
    }
    
	/**
	 * Get all the validation rules for a parameter.
	 */
	@Override
    public ValidationRuleSet getValidationRules() {
        addRule(CHAR_DATATYPE_LENGTH_RULE);
		return super.getValidationRules();		
	}
}
