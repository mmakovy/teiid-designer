/*
 * JBoss, Home of Professional Open Source.
*
* See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
*
* See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
*/
package org.teiid.query.validator.v88;

import org.teiid.designer.runtime.version.spi.ITeiidServerVersion;
import org.teiid.designer.runtime.version.spi.TeiidServerVersion.Version;
import org.teiid.query.validator.v87.Test87AlterValidation;

/**
 *
 */
@SuppressWarnings( "javadoc" )
public class Test88AlterValidation extends Test87AlterValidation {

    protected Test88AlterValidation(Version teiidVersion) {
        super(teiidVersion);
    }

    public Test88AlterValidation() {
        this(Version.TEIID_8_8);
    }

}
