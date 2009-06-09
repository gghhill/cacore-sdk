package gov.nih.nci.cacoresdk.workbench.portal.validation;

import gov.nih.nci.cacoresdk.workbench.portal.viewer.DeployPropertiesViewer;

import com.jgoodies.validation.ValidationResult;

public class DeploymentPropertiesValidator implements TabbedPanePropertiesValidator {
	
	DeployPropertiesViewer parentContainer = null;
	
	public DeploymentPropertiesValidator(DeployPropertiesViewer parentContainer){
			this.parentContainer=parentContainer;
	}

    public void validateInput() {

    	ValidationResult result = new ValidationResult();
    	
    	for(PanelValidator panelValidator:parentContainer.getPanelValidators()){
    		result.addAllFrom(panelValidator.validateInput());
    	}

    	parentContainer.getValidationModel().setResult(result);
    	
    	parentContainer.toggleWritableApiFields();
    	parentContainer.toggleSecurityFields();
    	parentContainer.toggleRemoteSshFields();
    	
    	parentContainer.toggleSaveButton();
    	parentContainer.togglePreviousButton();
    	parentContainer.toggleNextButton();
    	parentContainer.toggleDeployButton();
    	
    	parentContainer.togglePanels();
    	
    	parentContainer.updateDeployApplicationTabContents();
    	
    	parentContainer.updateComponentTreeSeverity();

    }
    
    public void initValidation(){
    	
    	for(PanelValidator panelValidator:parentContainer.getPanelValidators()){
    		panelValidator.initValidation();
    	}
        
    	this.validateInput();
    	
    	parentContainer.updateComponentTreeSeverity();
    }
    
	public void setDirty(boolean isDirty){
		parentContainer.setDirty(isDirty);
	}
}
