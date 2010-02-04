/*
 * JBoss, Home of Professional Open Source.
 *
 * See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing.
 *
 * See the AUTHORS.txt file distributed with this work for a full listing of individual contributors.
 */
package com.metamatrix.metamodels.history.impl;

import java.util.Collection;
import org.eclipse.emf.common.notify.NotificationChain;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.InternalEObject;
import org.eclipse.emf.ecore.util.EObjectContainmentWithInverseEList;
import org.eclipse.emf.ecore.util.InternalEList;
import com.metamatrix.metamodels.history.HistoryCriteria;
import com.metamatrix.metamodels.history.HistoryPackage;
import com.metamatrix.metamodels.history.Label;
import com.metamatrix.metamodels.history.LabelLog;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Label Log</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * <ul>
 *   <li>{@link com.metamatrix.metamodels.history.impl.LabelLogImpl#getLabels <em>Labels</em>}</li>
 * </ul>
 * </p>
 *
 * @generated
 */
public class LabelLogImpl extends HistoryLogImpl implements LabelLog {
    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public static final String copyright = "See the LEGAL.txt file distributed with this work for information regarding copyright ownership and licensing."; //$NON-NLS-1$

    /**
     * The cached value of the '{@link #getLabels() <em>Labels</em>}' containment reference list.
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @see #getLabels()
     * @generated
     * @ordered
     */
    protected EList labels = null;

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    protected LabelLogImpl() {
        super();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    protected EClass eStaticClass() {
        return HistoryPackage.eINSTANCE.getLabelLog();
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    public EList getLabels() {
        if (labels == null) {
            labels = new EObjectContainmentWithInverseEList(Label.class, this, HistoryPackage.LABEL_LOG__LABELS, HistoryPackage.LABEL__HISTORY_LOG);
        }
        return labels;
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseAdd(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case HistoryPackage.LABEL_LOG__HISTORY_CRITERIA:
                    if (historyCriteria != null)
                        msgs = ((InternalEObject)historyCriteria).eInverseRemove(this, EOPPOSITE_FEATURE_BASE - HistoryPackage.LABEL_LOG__HISTORY_CRITERIA, null, msgs);
                    return basicSetHistoryCriteria((HistoryCriteria)otherEnd, msgs);
                case HistoryPackage.LABEL_LOG__LABELS:
                    return ((InternalEList)getLabels()).basicAdd(otherEnd, msgs);
                default:
                    return eDynamicInverseAdd(otherEnd, featureID, baseClass, msgs);
            }
        }
        if (eContainer != null)
            msgs = eBasicRemoveFromContainer(msgs);
        return eBasicSetContainer(otherEnd, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public NotificationChain eInverseRemove(InternalEObject otherEnd, int featureID, Class baseClass, NotificationChain msgs) {
        if (featureID >= 0) {
            switch (eDerivedStructuralFeatureID(featureID, baseClass)) {
                case HistoryPackage.LABEL_LOG__HISTORY_CRITERIA:
                    return basicSetHistoryCriteria(null, msgs);
                case HistoryPackage.LABEL_LOG__LABELS:
                    return ((InternalEList)getLabels()).basicRemove(otherEnd, msgs);
                default:
                    return eDynamicInverseRemove(otherEnd, featureID, baseClass, msgs);
            }
        }
        return eBasicSetContainer(null, featureID, msgs);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public Object eGet(EStructuralFeature eFeature, boolean resolve) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case HistoryPackage.LABEL_LOG__HISTORY_CRITERIA:
                return getHistoryCriteria();
            case HistoryPackage.LABEL_LOG__NAME:
                return getName();
            case HistoryPackage.LABEL_LOG__URI:
                return getUri();
            case HistoryPackage.LABEL_LOG__LABELS:
                return getLabels();
        }
        return eDynamicGet(eFeature, resolve);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eSet(EStructuralFeature eFeature, Object newValue) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case HistoryPackage.LABEL_LOG__HISTORY_CRITERIA:
                setHistoryCriteria((HistoryCriteria)newValue);
                return;
            case HistoryPackage.LABEL_LOG__NAME:
                setName((String)newValue);
                return;
            case HistoryPackage.LABEL_LOG__URI:
                setUri((String)newValue);
                return;
            case HistoryPackage.LABEL_LOG__LABELS:
                getLabels().clear();
                getLabels().addAll((Collection)newValue);
                return;
        }
        eDynamicSet(eFeature, newValue);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public void eUnset(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case HistoryPackage.LABEL_LOG__HISTORY_CRITERIA:
                setHistoryCriteria((HistoryCriteria)null);
                return;
            case HistoryPackage.LABEL_LOG__NAME:
                setName(NAME_EDEFAULT);
                return;
            case HistoryPackage.LABEL_LOG__URI:
                setUri(URI_EDEFAULT);
                return;
            case HistoryPackage.LABEL_LOG__LABELS:
                getLabels().clear();
                return;
        }
        eDynamicUnset(eFeature);
    }

    /**
     * <!-- begin-user-doc -->
     * <!-- end-user-doc -->
     * @generated
     */
    @Override
    public boolean eIsSet(EStructuralFeature eFeature) {
        switch (eDerivedStructuralFeatureID(eFeature)) {
            case HistoryPackage.LABEL_LOG__HISTORY_CRITERIA:
                return historyCriteria != null;
            case HistoryPackage.LABEL_LOG__NAME:
                return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
            case HistoryPackage.LABEL_LOG__URI:
                return URI_EDEFAULT == null ? uri != null : !URI_EDEFAULT.equals(uri);
            case HistoryPackage.LABEL_LOG__LABELS:
                return labels != null && !labels.isEmpty();
        }
        return eDynamicIsSet(eFeature);
    }

} //LabelLogImpl
