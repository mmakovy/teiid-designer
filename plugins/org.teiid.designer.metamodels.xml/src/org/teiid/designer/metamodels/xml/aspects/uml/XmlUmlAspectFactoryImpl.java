/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.metamodels.xml.aspects.uml;

import org.eclipse.emf.ecore.EClassifier;
import org.teiid.designer.core.metamodel.aspect.MetamodelAspect;
import org.teiid.designer.core.metamodel.aspect.MetamodelAspectFactory;
import org.teiid.designer.core.metamodel.aspect.MetamodelEntity;
import org.teiid.designer.metamodels.xml.XmlDocumentPackage;
import org.teiid.designer.metamodels.xml.XmlDocumentPlugin;


/**
 * XmlUmlAspectFactoryImpl
 *
 * @since 8.0
 */
public class XmlUmlAspectFactoryImpl implements MetamodelAspectFactory {
    @Override
	public MetamodelAspect create(EClassifier classifier, MetamodelEntity entity) {
        switch (classifier.getClassifierID()) {
            case XmlDocumentPackage.XML_FRAGMENT: return createXmlFragmentAspect(entity);
            case XmlDocumentPackage.XML_DOCUMENT: return createXmlDocumentAspect(entity);
            case XmlDocumentPackage.XML_ELEMENT: return null;
            case XmlDocumentPackage.XML_ATTRIBUTE: return null;
            case XmlDocumentPackage.XML_ROOT: return null;
            case XmlDocumentPackage.XML_COMMENT: return null;
            case XmlDocumentPackage.XML_NAMESPACE: return null;
            case XmlDocumentPackage.XML_SEQUENCE: return null;
            case XmlDocumentPackage.XML_ALL: return null;
            case XmlDocumentPackage.XML_CHOICE: return null;
            case XmlDocumentPackage.PROCESSING_INSTRUCTION: return null;
            case XmlDocumentPackage.XML_FRAGMENT_USE: return null;
            default:
                throw new IllegalArgumentException(XmlDocumentPlugin.Util.getString("XmlUmlAspectFactoryImpl.Invalid_ClassiferID,_,_for_creating_UML_Aspect_1",classifier)); //$NON-NLS-1$
        }
    }

    /**
     * @return
     */
    private MetamodelAspect createXmlDocumentAspect(MetamodelEntity entity) {
        return new XmlDocumentAspect(entity);
    }

    /**
     * @return
     */
    private MetamodelAspect createXmlFragmentAspect(MetamodelEntity entity) {
        return new XmlFragmentAspect(entity);
    }

}
