/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.ui.actions;

import java.util.Iterator;
import java.util.List;
import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRunnable;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.jface.dialogs.IInputValidator;
import org.eclipse.jface.dialogs.InputDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.teiid.designer.core.ModelerCore;
import org.teiid.designer.core.workspace.ModelResource;
import org.teiid.designer.ui.UiConstants;
import org.teiid.designer.ui.UiPlugin;
import org.teiid.designer.ui.common.actions.AbstractAction;
import org.teiid.designer.ui.common.eventsupport.SelectionUtilities;
import org.teiid.designer.ui.viewsupport.ModelUtilities;


/**
 * BookmarkAction
 *
 * @since 8.0
 */
public class BookmarkAction extends AbstractAction implements UiConstants {
    /**
     * Whether to prompt the user for the bookmark name.
     */
    boolean promptForName = true;

    public BookmarkAction() {
        super(UiPlugin.getDefault());
    }

    /**
     * @see org.eclipse.ui.ISelectionListener#selectionChanged(IWorkbenchPart, ISelection)
     */
    @Override
    public void selectionChanged( IWorkbenchPart thePart,
                                  ISelection theSelection ) {
        super.selectionChanged(thePart, theSelection);
        setEnabled(!isEmptySelection());
    }

    /**
     * Creates a marker of the given type on each of the files in the current selection.
     * 
     * @param markerType the marker type
     */
    void createMarker( String markerType ) {

        List selectedResources = SelectionUtilities.getSelectedObjects(getSelection());
        for (Iterator enumeration = selectedResources.iterator(); enumeration.hasNext();) {
            Object o = enumeration.next();
            if (o instanceof IFile) {
                createMarker((IFile)o, markerType);
            } else if (o instanceof IAdaptable) {
                Object resource = ((IAdaptable)o).getAdapter(IResource.class);
                if (resource instanceof IFile) createMarker((IFile)resource, markerType);
            } else if (o instanceof EObject) {
                EObject eObj = (EObject)o;
                ModelResource mr = ModelUtilities.getModelResourceForModelObject(eObj);
                createEObjectMarker(mr.getResource(), eObj);
            }
        }
    }

    /**
     * Creates a marker of the given type on the given file resource.
     * 
     * @param file the file resource
     * @param markerType the marker type
     */
    void createMarker( final IFile file,
                       final String markerType ) {
        try {
            file.getWorkspace().run(new IWorkspaceRunnable() {
                @Override
				public void run( IProgressMonitor monitor ) throws CoreException {
                    String markerMessage = file.getName();
                    if (promptForName) markerMessage = askForLabel(markerMessage);
                    if (markerMessage != null) {
                        IMarker marker = file.createMarker(markerType);
                        marker.setAttribute(IMarker.MESSAGE, markerMessage);
                    }
                }
            }, null);
        } catch (CoreException e) {
            Util.log(e.getStatus()); // We don't care
        }
    }

    /**
     * Create a marker given a validationProblem
     */
    private void createEObjectMarker( IResource resource,
                                      EObject eObject ) {
        try {
            String markerMessage = null;
            if (promptForName) markerMessage = askForLabel(markerMessage);
            if (markerMessage != null) {
                IMarker marker = resource.createMarker(IMarker.BOOKMARK);
                // Note: IMarker.LOCATION is not used by bookmark view
                // & Location column is actually LINE_NUMBER, which we can't use.. go figure.
                // String location = ModelObjectUtilities.getTrimmedFullPath(eObject);
                // if( location != null )
                // marker.setAttribute(IMarker.LINE_NUMBER, location);
                String targetURI = ModelerCore.getModelEditor().getUri(eObject).toString();
                marker.setAttribute(ModelerCore.MARKER_URI_PROPERTY, targetURI);
                marker.setAttribute(ModelerCore.TARGET_MARKER_URI_PROPERTY, targetURI);

                marker.setAttribute(IMarker.MESSAGE, markerMessage);
            }
        } catch (CoreException e) {
            Util.log(e.getStatus()); // We don't care
        }
    }

    @Override
    protected void doRun() {
        createMarker(IMarker.BOOKMARK);
    }

    /**
     * Asks the user for a bookmark name.
     * 
     * @param proposal the suggested bookmark name
     * @return the bookmark name or <code>null</code> if cancelled.
     */
    String askForLabel( String proposal ) {
        String title = UiConstants.Util.getString("AddBookmarkDialog.title"); //$NON-NLS-1$
        String message = UiConstants.Util.getString("AddBookmarkDialog.message"); //$NON-NLS-1$

        IInputValidator inputValidator = new IInputValidator() {
            @Override
			public String isValid( String newText ) {
                return (newText == null || newText.length() == 0) ? " " : null; //$NON-NLS-1$
            }
        };

        Shell shell = UiPlugin.getDefault().getCurrentWorkbenchWindow().getShell();
        InputDialog dialog = new InputDialog(shell, title, message, proposal, inputValidator);

        if (dialog.open() != Window.CANCEL) {
            String name = dialog.getValue();
            if (name == null) return null;
            name = name.trim();
            return (name.length() == 0) ? null : name;
        }

        return null;
    }
}
