/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.schema.tools.model.schema.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.teiid.designer.schema.tools.ToolsPlugin;
import org.teiid.designer.schema.tools.model.schema.ISchemaModelCopyTraversalContext;
import org.teiid.designer.schema.tools.model.schema.SchemaObject;

/**
 * @since 8.0
 */
public class SchemaModelCopyTraversalContext implements ISchemaModelCopyTraversalContext {

    // The running list of resultElements that have been copied.
    private Map copiedElements = new HashMap();

    // The results of the copy operation.
    private List resultElements;

    private Set resultRoots;

    // The resultElements passed in to copy
    private List originalElements;

    public SchemaModelCopyTraversalContext( List schemaElements,
                                            Set roots ) {
        this.originalElements = schemaElements;
        this.resultElements = new ArrayList(originalElements.size());

        process();
        if (null != roots) {
            this.resultRoots = new HashSet(roots);
        } else {
            this.resultRoots = new HashSet();
        }
    }

    private void process() {
        for (Iterator i = originalElements.iterator(); i.hasNext();) {
            SchemaObject original = (SchemaObject)i.next();
            SchemaObject copy = original.copy(this);
            copiedElements.put(original, copy);
            resultElements.add(copy);
        }
        for (int i = 0; i < originalElements.size(); i++) {
            SchemaObject original = (SchemaObject)originalElements.get(i);
            SchemaObject copy = (SchemaObject)resultElements.get(i);
            ((BaseSchemaObject)original).copy((BaseSchemaObject)copy, this);
        }
    }

    /**
     * Adds an ElementImpl and its copy to the Map of copied resultElements.
     * 
     * @param element The original ElementImpl
     * @param copy The copy
     */
    public void addElement( SchemaObject element,
                            SchemaObject copy ) {
        if (!copiedElements.containsKey(element)) {
            copiedElements.put(element, copy);
            resultElements.add(copy);
        }
    }

    /**
     * Returns an existing copy of an ElementImpl, or creates and returns one if one does not exist.
     * 
     * @param element The ElementImpl to copy
     * @return The copy of the element
     */
    public SchemaObject getElement( SchemaObject element ) {
        SchemaObject copy;
        if (!copiedElements.containsKey(element)) {
            throw new RuntimeException(ToolsPlugin.Util.getString("SchemaModelCopyTraversalContext.copiedElementNotFound")); //$NON-NLS-1$
        }
        copy = (SchemaObject)copiedElements.get(element);
        return copy;
    }

    public List getCopiedElements() {
        if (resultElements.size() != originalElements.size()) {
            throw new RuntimeException(ToolsPlugin.Util.getString("SchemaModelCopyTraversalContext.invalidCopiedElementTotal")); //$NON-NLS-1$
        }
        return resultElements;
    }

    public Set getCopiedRoots() {
        return resultRoots;
    }

}
