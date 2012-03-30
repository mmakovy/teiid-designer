/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package org.teiid.designer.runtime.ui;

import java.util.Properties;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.datatools.connectivity.IConnectionProfile;
import org.eclipse.datatools.connectivity.ProfileManager;
import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.custom.BusyIndicator;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.teiid.adminapi.Admin;
import org.teiid.adminapi.VDB;
import org.teiid.datatools.connectivity.ConnectivityUtil;
import org.teiid.designer.datatools.ui.dialogs.NewTeiidFilteredCPWizard;
import org.teiid.designer.datatools.ui.dialogs.TeiidCPWizardDialog;
import org.teiid.designer.runtime.ExecutionAdmin;
import org.teiid.designer.runtime.Server;
import org.teiid.designer.runtime.TeiidJdbcInfo;
import org.teiid.designer.vdb.Vdb;

import com.metamatrix.core.util.I18nUtil;
import com.metamatrix.modeler.dqp.DqpPlugin;
import com.metamatrix.modeler.dqp.ui.DqpUiConstants;
import com.metamatrix.modeler.dqp.ui.DqpUiPlugin;
import com.metamatrix.modeler.ui.actions.SortableSelectionAction;
import com.metamatrix.ui.internal.eventsupport.SelectionUtilities;

/**
 * 
 */
public class ExecuteVDBAction extends SortableSelectionAction {
    protected static final String I18N_PREFIX = I18nUtil.getPropertyPrefix(ExecuteVDBAction.class);
    protected static final String VDB_EXTENSION = "vdb"; //$NON-NLS-1$
    public static final String DEFAULT_USERNAME = "admin"; //$NON-NLS-1$
    public static final String DEFAULT_PASSWORD = "teiid"; //$NON-NLS-1$

    private static final String DTP_PERSPECTIVE = "org.eclipse.datatools.sqltools.sqleditor.perspectives.EditorPerspective"; //$NON-NLS-1$

    protected boolean successfulRefresh = false;

    IFile selectedVDB;
    Vdb vdb;
    boolean contextIsLocal = false;

    public ExecuteVDBAction() {
        super();
        setImageDescriptor(DqpUiPlugin.getDefault().getImageDescriptor(DqpUiConstants.Images.EXECUTE_VDB));
    }

    @Override
    public int compareTo( Object o ) {
        if (o instanceof String) {
            return getText().compareTo((String)o);
        }

        if (o instanceof Action) {
            return getText().compareTo(((Action)o).getText());
        }
        return 0;
    }

    /**
     * @param selection
     * @return
     */
    @Override
    public boolean isApplicable( ISelection selection ) {
        boolean result = false;
        if (!SelectionUtilities.isMultiSelection(selection)) {
            Object obj = SelectionUtilities.getSelectedObject(selection);
            if (obj instanceof IFile) {
                String extension = ((IFile)obj).getFileExtension();
                if (extension != null && extension.equals("vdb")) { //$NON-NLS-1$
//                    Server server = DqpPlugin.getInstance().getServerManager().getDefaultServer();
//                    if (server != null) {
                        return true;
//                    }
                }
            }
        }
        return result;
    }

    /**
     * @see org.eclipse.ui.IActionDelegate#run(org.eclipse.jface.action.IAction)
     */
    @Override
    public void run() {

        /*
         * Server server = DqpPlugin.getInstance().getServerManager()
         * .getDefaultServer();
         * 
         * if (server != null) { try { server. ExecutionAdmin admin =
         * server.getAdmin(); if (null ==
         * server.getAdmin().getVdb(selectedVDB.getName()))
         * 
         * MessageDialog.openInformation(Display.getCurrent() .getActiveShell(),
         * "VDB Deployed", "VDB: " + selectedVDB.getName() +
         * " is Deployed on server: " + server.getUrl()); } catch (Exception e)
         * { DqpUiConstants.UTIL.log(IStatus.ERROR, e, DqpPlugin.Util
         * .getString( "DeployVdbAction.problemDeployingVdbToServer",
         * //$NON-NLS-1$ selectedVDB.getName(), server.getUrl())); } }
         */
    	
    	if( !VdbRequiresSaveChecker.insureOpenVdbSaved(selectedVDB) ) {
    		return;
    	}

        BusyIndicator.showWhile(Display.getDefault(), new Runnable() {

            @Override
            public void run() {
            	internalRun();
            }
        });

    }
    
    void internalRun() {
        Server server = DqpPlugin.getInstance().getServerManager().getDefaultServer();
        VDB deployedVDB = null;

        try {
            if (server != null) {
            	IStatus connectStatus = server.ping();
            	if( connectStatus.isOK() ) {
	                ExecutionAdmin admin = server.getAdmin();
	                if (admin != null) {
	                    deployedVDB = admin.getVdb(selectedVDB.getName());
	                    if (deployedVDB == null) {
	                        deployedVDB = DeployVdbAction.deployVdb(server, selectedVDB);
	                    }
	
	                    if (deployedVDB != null && deployedVDB.getStatus().equals(VDB.Status.ACTIVE)) {
	                        executeVdb(DqpPlugin.getInstance().getServerManager().getDefaultServer(),
	                                   selectedVDB.getFullPath().removeFileExtension().lastSegment());
	                    } else {
	                        StringBuilder message = new StringBuilder(
	                                                                  DqpUiConstants.UTIL.getString("ExecuteVDBAction.vdbNotActiveMessage", selectedVDB.getName())); //$NON-NLS-1$
	                        if(null != deployedVDB) {
	                        	for (String error : deployedVDB.getValidityErrors()) {
	                        		message.append("\nERROR:\t").append(error); //$NON-NLS-1$
	                        	}
	                        }
	                        MessageDialog.openWarning(getShell(),
	                                                  DqpUiConstants.UTIL.getString("ExecuteVDBAction.vdbNotActiveTitle"), //$NON-NLS-1$
	                                                  message.toString());
	                    }
	                }
            	} else {
            		MessageDialog.openWarning(getShell(),
                            DqpUiConstants.UTIL.getString("ExecuteVDBAction.noTeiidServerConnection.title"), //$NON-NLS-1$
                            DqpUiConstants.UTIL.getString("ExecuteVDBAction.noTeiidServerConnection.message", connectStatus.getMessage())); //$NON-NLS-1$
            	}
            } else {
            	MessageDialog.openWarning(DqpUiPlugin.getDefault().getCurrentWorkbenchWindow().getShell(),
                        DqpUiConstants.UTIL.getString("ExecuteVDBAction.noTeiidInstance.title"), //$NON-NLS-1$
                        DqpUiConstants.UTIL.getString("ExecuteVDBAction.noTeiidInstance.message")); //$NON-NLS-1$
            }
        } catch (Exception e) {
            DqpUiConstants.UTIL.log(IStatus.ERROR, e, DqpUiConstants.UTIL.getString("ExecuteVDBAction.vdbNotDeployedError", //$NON-NLS-1$
            		selectedVDB.getName()));
        }
    }

    @Override
    public void selectionChanged( IWorkbenchPart part,
                                  ISelection selection ) {
        boolean enable = false;
        if (!SelectionUtilities.isMultiSelection(selection)) {
            Object obj = SelectionUtilities.getSelectedObject(selection);
            if (obj instanceof IFile) {
                String extension = ((IFile)obj).getFileExtension();
                if (extension != null && extension.equals(VDB_EXTENSION)) {
                    this.selectedVDB = (IFile)obj;
                    enable = true;
                }
            }
        }
        if( !enable ) {
        	this.selectedVDB = null;
        }
        setEnabled(enable);
    }

    public static void executeVdb( Server server,
                                   String vdbName ) throws CoreException {
        processForDTP(server, vdbName);
    }

    private static void processForDTP( Server server,
    		String vdbName ) throws CoreException {

    	String driverPath = Admin.class.getProtectionDomain().getCodeSource().getLocation().getFile();

    	TeiidJdbcInfo jdbcInfo = new TeiidJdbcInfo(vdbName, server.getTeiidJdbcInfo());
    	
    	String connectionURL = jdbcInfo.getUrl();
    	
    	String profileName = DqpUiConstants.UTIL.getString("ExecuteVDBAction.profileName", vdbName, server.getHost()); //$NON-NLS-1$
    	
    	IConnectionProfile profile = ProfileManager.getInstance().getProfileByName(profileName);
    	if(profile == null) {
    		// If username or password is not supplied we bring up the New Connection Profile dialog
    		if(null == jdbcInfo.getUsername() || jdbcInfo.getUsername().isEmpty() ||
    				null == jdbcInfo.getPassword() || jdbcInfo.getPassword().isEmpty()) {
    			Properties cpProps = ConnectivityUtil.createVDBTeiidProfileProperties(driverPath,
    					connectionURL, jdbcInfo.getUsername(), jdbcInfo.getPassword(), vdbName, profileName);
    			NewTeiidFilteredCPWizard wiz = new NewTeiidFilteredCPWizard(profileName, null);
    			TeiidCPWizardDialog wizardDialog = new TeiidCPWizardDialog(Display.getCurrent().getActiveShell(), wiz);
    			wizardDialog.setProperties(cpProps);
    			wizardDialog.setBlockOnOpen(true);
    			if (wizardDialog.open() == Window.OK) {
    				try {
    					PlatformUI.getWorkbench().showPerspective(DTP_PERSPECTIVE, DqpUiPlugin.getDefault().getCurrentWorkbenchWindow());
    				} catch (Throwable e) {
    					DqpUiConstants.UTIL.log(e);
    				}
    			} else {
    				return;
    			}
    		// if we have all the info we create it w/o user interaction	
    		} else {
    			profile = ConnectivityUtil.createVDBTeiidProfile(driverPath,
    					connectionURL, jdbcInfo.getUsername(), jdbcInfo.getPassword(), vdbName, profileName);	
    		}     		
    	}
    	profile.connectWithoutJob();
    	try {
    		PlatformUI.getWorkbench().showPerspective(DTP_PERSPECTIVE, DqpUiPlugin.getDefault().getCurrentWorkbenchWindow());
    	} catch (Throwable e) {
    		DqpUiConstants.UTIL.log(e);
    	}
    }

    private static Shell getShell() {
    	return DqpUiPlugin.getDefault().getCurrentWorkbenchWindow().getShell();
    }
}