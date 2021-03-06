/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC, SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cacore-sdk/LICENSE.txt for details.
 */

package gov.nih.nci.cacore.workbench.portal.viewer;

import gov.nih.nci.cacore.workbench.common.CacoreWorkbenchConstants;
import gov.nih.nci.cacore.workbench.common.LookAndFeel;
import gov.nih.nci.cacore.workbench.common.ResourceManager;
import gov.nih.nci.cacore.workbench.common.WorkbenchPropertiesManager;
import gov.nih.nci.cacore.workbench.portal.panel.AdvancedSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.AppServerSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.CaGridAuthSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.ClmSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.CodegenSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.CsmDbConnectionSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.DbConnectionSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.LogViewerPanel;
import gov.nih.nci.cacore.workbench.portal.panel.ModelSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.ProjectDirSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.ProjectSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.RemoteSshSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.SecuritySettingsPanel;
import gov.nih.nci.cacore.workbench.portal.panel.WritableApiSettingsPanel;
import gov.nih.nci.cacore.workbench.portal.validation.CodegenPropertiesValidator;
import gov.nih.nci.cacore.workbench.portal.validation.PanelValidator;
import gov.nih.nci.cagrid.common.portal.PortalLookAndFeel;
import gov.nih.nci.cagrid.common.portal.validation.IconFeedbackPanel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;

import org.apache.log4j.Logger;

import com.jgoodies.validation.ValidationMessage;
import com.jgoodies.validation.ValidationResult;
import com.jgoodies.validation.ValidationResultModel;
import com.jgoodies.validation.util.DefaultValidationResultModel;
import com.jgoodies.validation.view.ValidationComponentUtils;

/**
 * SDK Code Generation Properties Manager Viewer
 * 
 * @author <A HREF="MAILTO:dumitrud@mail.nih.gov">Dan Dumitru</A>
 * @created November 6, 2008
 */
public class CodegenPropertiesViewer extends WorkbenchViewerBaseComponent {
	
	private static final Logger log = Logger.getLogger(CodegenPropertiesViewer.class);

	private static final long serialVersionUID = 1L;
	
	private static final String MODELS_DIR = "models";
	
    // Tab panel indexes - used to enable/disable entire tab
	private int PROJECT_TAB_INDEX = 1;
	private int MODEL_TAB_INDEX = 2;	
	private int CODEGEN_TAB_INDEX = 3;
	private int WRITABLE_API_TAB_INDEX = 4;
	private int SECURITY_TAB_INDEX = 5;
	private int GENERATE_TAB_INDEX = 6;
	private int LOG_VIEWER_TAB_INDEX = 7;
	
	private boolean isPropsLoaded=false;
	private boolean isDirty=false;
	
	// Validation 
	private CodegenPropertiesValidator propsValidator = null;
	private List<PanelValidator> panelValidators = null;
    private ValidationResultModel validationModel = new DefaultValidationResultModel();

    // Buttons
    private JButton previousButton = null;
    private JButton nextButton = null;
    private JButton saveButton = null;
    private JButton generateButton = null;
    private JButton closeButton = null;

	/*
	 * Primary Panel definitions
	 */
	private JTabbedPane mainTabbedPane = null;
    private JPanel mainPanel = null;
    private JPanel buttonPanel = null;

	// Tab panel definitions
	private ProjectDirSettingsPanel projectDirSettingsPanel = null;
	private ProjectSettingsPanel projectSettingsPanel = null;	
	private ModelSettingsPanel modelSettingsPanel = null;
	private CodegenSettingsPanel codegenSettingsPanel = null;
	private WritableApiSettingsPanel writableApiSettingsPanel = null;
	private SecuritySettingsPanel securitySettingsPanel = null;
	private LogViewerPanel logViewerPanel = null;
	
	private JPanel summarySettingsPanel = null;
    
	// Constructor
    public CodegenPropertiesViewer() {
        super();
        initialize();
    }

    /**
     * This method initializes this Viewer
     */
    private void initialize() {
    	
		// Initialize to an empty properties manager. User will
		// later choose which set of deployment properties to load
		// i.e., local or remote (e.g., dev, training, qa, prod)
		propsMgr = new WorkbenchPropertiesManager(new String[]{});    	
        
        //Initialize main tabbed panel validator
        propsValidator = new CodegenPropertiesValidator(this);
        
    	projectDirSettingsPanel=new ProjectDirSettingsPanel(this, propsValidator);

        //Initialize Panels       
        initPanels();
        
        this.setContentPane(getMainPanel());
        this.setFrameIcon(LookAndFeel.getGenerateApplicationIcon());
        this.setTitle("Manage Code Generation Properties");

        initValidation();
        
//        projectSettingsPanel.setProjectDirValue(projectDirPath);
        
        setPropsLoaded(false);
        setDirty(false);
        validateInput();
    }


    private void initValidation() {
    	propsValidator.initValidation();
    }
    
    private void validateInput() {
    	propsValidator.validateInput();
    }

    public void updateComponentTreeSeverity() {
        ValidationComponentUtils.updateComponentTreeMandatoryAndBlankBackground(this);
        ValidationComponentUtils.updateComponentTreeSeverityBackground(this, this.getValidationModel().getResult());
    }
    
    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getMainPanel() {
        if (mainPanel == null) {
            GridBagConstraints gridBagConstraints111 = new GridBagConstraints();
            gridBagConstraints111.fill = GridBagConstraints.BOTH;
            gridBagConstraints111.weighty = 1.0;
            gridBagConstraints111.weightx = 1.0;
            
            GridBagConstraints gridBagConstraints1 = new GridBagConstraints();
            gridBagConstraints1.insets = new java.awt.Insets(5, 5, 5, 5);
            gridBagConstraints1.gridx = 0;
            gridBagConstraints1.gridy = 2;
            gridBagConstraints1.anchor = java.awt.GridBagConstraints.CENTER;
            gridBagConstraints1.gridheight = 1;
            
            mainPanel = new JPanel();
            mainPanel.setLayout(new GridBagLayout());
            mainPanel.add(getButtonPanel(), gridBagConstraints1);
            mainPanel.add(getMainTabbedPane(), gridBagConstraints111);
        }
        return mainPanel;
    }


    /**
     * This method initializes jPanel
     * 
     * @return javax.swing.JPanel
     */
    private JPanel getButtonPanel() {
        if (buttonPanel == null) {
            buttonPanel = new JPanel();
            buttonPanel.add(getPreviousButton(), null);
            buttonPanel.add(getNextButton(), null);
            buttonPanel.add(getSaveButton(), null);
            buttonPanel.add(getGenerateButton(), null);
            buttonPanel.add(getCloseButton(), null);
        }
        return buttonPanel;
    }

    /**
     * This method initializes the Previous jButton
     * 
     * @return javax.swing.JButton
     */
    public JButton getPreviousButton() {
        if (previousButton == null) {
        	previousButton = new JButton();
        	previousButton.setText("Previous");
        	previousButton.setIcon(LookAndFeel.getPreviousIcon());
        	previousButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    mainTabbedPane.setSelectedIndex(getPreviousIndex());
                    validateInput();
                }
            });
        }

        return previousButton;
    }
    
    /**
     * This method initializes the Previous jButton
     * 
     * @return javax.swing.JButton
     */
    public JButton getNextButton() {
        if (nextButton == null) {
        	nextButton = new JButton();
        	nextButton.setText("Next");
        	nextButton.setIcon(LookAndFeel.getNextIcon());
        	nextButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                	 mainTabbedPane.setSelectedIndex(getNextIndex());
                	 validateInput();
                }
            });
        }

        return nextButton;
    }
    
    private int getPreviousIndex(){
    	int index = mainTabbedPane.getSelectedIndex();

    	while (index >= 0){
    		--index;
    		
    		if (mainTabbedPane.isEnabledAt(index))
    			return index;
    	}
    	
    	return 0;
    }
    
    private int getNextIndex(){
    	int index = mainTabbedPane.getSelectedIndex();

    	while (index <= mainTabbedPane.getTabCount()-1){
    		++index;
    		
    		if (mainTabbedPane.isEnabledAt(index))
    			return index;
    	}
    	
    	return mainTabbedPane.getTabCount() - 1;
    }
    
    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getGenerateButton() {
        if (generateButton == null) {
            generateButton = new JButton();
            generateButton.setText("Generate");
            generateButton.setIcon(LookAndFeel.getGenerateApplicationIcon());
            generateButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    generateApplication(
                    		projectSettingsPanel.getSdkInstallDirValue(),
                    		projectSettingsPanel.getProjectTemplateDirValue(),
                    		projectSettingsPanel.getProjectDir());
                }
            });
        }

        return generateButton;
    }

    /**
     * This method initializes jButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton();
            saveButton.setText("Save");
            saveButton.setIcon(LookAndFeel.getGenerateApplicationIcon());
            saveButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                	
                   boolean saveSuccessful = saveCodegenProperties(
                    		projectSettingsPanel.getSdkInstallDirValue(), 
                    		projectSettingsPanel.getProjectTemplateDirValue(),
							projectSettingsPanel.getProjectDir(), 
							modelSettingsPanel.getModelFilePath(),
							getPropsMap());
                    
                   if (saveSuccessful){
	                   setDirty(false);
	                   validateInput();
                   }
                }
            });
        }

        return saveButton;
    }
    
    public void updateGenerateApplicationTabContents() {
    	//update the 'Generate Application' tab contents
    	if (mainTabbedPane.getSelectedIndex() == GENERATE_TAB_INDEX){
    		mainTabbedPane.setComponentAt(GENERATE_TAB_INDEX, getSummarySettingsPanel());
    	}
    }
    
    public void toggleGenerateButton() {
    	//if (this.validationModel.hasErrors() || (this.isDirty) || (mainTabbedPane.getSelectedIndex() != GENERATE_TAB_INDEX) ) {
    	if (this.isDirty || (mainTabbedPane.getSelectedIndex() != GENERATE_TAB_INDEX)|| (!this.isPropsLoaded) ) {
    		log.debug("Generate Button is disabled; required conditions have not been met");
    		generateButton.setEnabled(false);
    	} else {
    		log.debug("* * * Validation model has errors? "+this.validationModel.hasErrors());
    		if (this.validationModel.hasErrors()){

    			ValidationResult results = validationModel.getResult();
    			List<com.jgoodies.validation.ValidationMessage> errors = results.getErrors();
    			
    			//debug error results
    			for (ValidationMessage errorMessage:results.getErrors()){
    				log.debug("* * * * Validation Error Message key: "+errorMessage.key()+"; Validation Error Message: "  + errorMessage.formattedText());
    			}
    			
    			if (errors !=null && errors.size() == 1){
        			for (ValidationMessage errorMessage:results.getErrors()){
        				if ( ((String)(errorMessage.key())).equalsIgnoreCase(CacoreWorkbenchConstants.LOG_FILE_VALIDATION_KEY)){
        					log.debug("Generate Button is enabled; required conditions have been met");
        					generateButton.setEnabled(true);
        	    			return;
        				}
        			}

    			}
    			log.debug("Generate Button is disabled; Validation errors have been found");
    			generateButton.setEnabled(false);
    			return;
    		}

    		log.debug("Generate Button is enabled; required conditions have been met");
    		generateButton.setEnabled(true);
    		
    	}
    }
    
    public void toggleSaveButton() {
    	//if (this.validationModel.hasErrors() || !this.isDirty || (!this.isPropsLoaded)) {
    	if ( (!this.isDirty) || (!this.isPropsLoaded)) {
    		saveButton.setEnabled(false);
    	} else {
    		saveButton.setEnabled(true);
    	}
    }

    public void togglePreviousButton() {
    	if (mainTabbedPane.getSelectedIndex() <= 0 || (!this.isPropsLoaded) ){
    		previousButton.setEnabled(false);
    	} else {
    		previousButton.setEnabled(true);
    	}
    }

    public void toggleNextButton() {
    	if (mainTabbedPane.getSelectedIndex() >= mainTabbedPane.getTabCount()-1 || (!this.isPropsLoaded)){
    		nextButton.setEnabled(false);
    	} else {
    		nextButton.setEnabled(true);
    	}
    }
    
    public void confirmCsmTablesPresent(){
		if (securitySettingsPanel.isInstanceLevelSecurityEnabled()){
			JOptionPane.showMessageDialog(
					this,
					"Instance Level Security requires that the CSM tables be present in\n"
					+"the same schema as the model tables. Make sure this is the case.");
		}
    }
    
    
    public void notifyOfDisabledInterfaces(){
		if (codegenSettingsPanel.isIso21090DatatypesEnabled()){
			JOptionPane.showMessageDialog(
					this,
					 "Enabling ISO 21090 Data Type support causes the following functionality\n"
					+"to be disabled:\n"
					+"  *  Web Service API\n"
					+"  *  WSDD Generation\n"
					+"  *  Castor Mapping Generation\n"
					+"  *  Permissible Values Validator Activation\n");
		}
    }

    /**
     * This method initializes closeButton
     * 
     * @return javax.swing.JButton
     */
    private JButton getCloseButton() {
        if (closeButton == null) {
            closeButton = new JButton();
            closeButton.setIcon(PortalLookAndFeel.getCloseIcon());
            closeButton.setText("Cancel");
            closeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent e) {
                    dispose();
                }
            });
        }
        return closeButton;
    }

	/**
	 * This method initializes mainTabbedPane	
	 * 	
	 * @return javax.swing.JTabbedPane	
	 */
    public JTabbedPane getMainTabbedPane() {
		if (mainTabbedPane == null) {
			mainTabbedPane = new JTabbedPane();
			mainTabbedPane.addTab("Project Dir", null, new IconFeedbackPanel(this.validationModel, projectDirSettingsPanel.getSettingsPanel()), null);			
			mainTabbedPane.addTab("Project", null, new IconFeedbackPanel(this.validationModel, projectSettingsPanel.getSettingsPanel()), null);
			mainTabbedPane.addTab("Model", null, new IconFeedbackPanel(this.validationModel, modelSettingsPanel.getSettingsPanel()), null);
			mainTabbedPane.addTab("Code Generation", null, new IconFeedbackPanel(this.validationModel, codegenSettingsPanel.getSettingsPanel()), null);
			
			mainTabbedPane.addTab("Writable API", null, new IconFeedbackPanel(this.validationModel, writableApiSettingsPanel.getSettingsPanel()), null);
			
			mainTabbedPane.addTab("Security", null, new IconFeedbackPanel(this.validationModel, securitySettingsPanel.getSettingsPanel()), null);
			
			mainTabbedPane.addTab("Generate Application", null, getSummarySettingsPanel(), null);
			
			mainTabbedPane.addTab("View Log", null, new IconFeedbackPanel(this.validationModel, logViewerPanel.getSettingsPanel()), null);
			
			mainTabbedPane.addMouseListener(new java.awt.event.MouseListener() {
                public void mouseEntered(java.awt.event.MouseEvent e) {
                	;//do nothing
                }
                public void mouseReleased(java.awt.event.MouseEvent e) {
                	;//do nothing
                }
                public void mouseExited(java.awt.event.MouseEvent e) {
                	;//do nothing
                }
                public void mousePressed(java.awt.event.MouseEvent e) {
                	validateInput();
                }
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    try {
                        validateInput();
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            });
		}
		return mainTabbedPane;
	}
    
	/**
	 * This method initializes the Code Generation settings panel	
	 * 	
	 * @return javax.swing.JPanel	
	 */
	private JPanel getSummarySettingsPanel() {
		//if (summarySettingsPanel == null) {
			
			GridBagConstraints gridBagConstraints10 = new GridBagConstraints();
			gridBagConstraints10.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints10.gridy = 1;
			gridBagConstraints10.weightx = 1.0;
			gridBagConstraints10.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints10.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints10.gridwidth = 1;
			gridBagConstraints10.weighty = 1.0D;
			gridBagConstraints10.gridx = 0;
			
			GridBagConstraints gridBagConstraints11 = new GridBagConstraints();
			gridBagConstraints11.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints11.gridy = 1;
			gridBagConstraints11.weightx = 1.0;
			gridBagConstraints11.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints11.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints11.gridwidth = 1;
			gridBagConstraints11.weighty = 1.0D;
			gridBagConstraints11.gridx = 1;
			
			GridBagConstraints gridBagConstraints20 = new GridBagConstraints();
			gridBagConstraints20.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints20.gridy = 2;
			gridBagConstraints20.weightx = 1.0;
			gridBagConstraints20.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints20.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints20.gridwidth = 1;
			gridBagConstraints20.weighty = 1.0D;
			gridBagConstraints20.gridx = 0;
			
			GridBagConstraints gridBagConstraints21 = new GridBagConstraints();
			gridBagConstraints21.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints21.gridy = 2;
			gridBagConstraints21.weightx = 1.0;
			gridBagConstraints21.anchor = java.awt.GridBagConstraints.NORTHWEST;
			gridBagConstraints21.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints21.gridwidth = 1;
			gridBagConstraints21.weighty = 1.0D;
			gridBagConstraints21.gridx = 1;
			
			GridBagConstraints gridBagConstraints30 = new GridBagConstraints();
			gridBagConstraints30.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints30.gridy = 3;
			gridBagConstraints30.weightx = 1.0;
			gridBagConstraints30.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints30.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints30.gridwidth = 2;
			gridBagConstraints30.weighty = 1.0D;
			gridBagConstraints30.gridx = 0;
			
			GridBagConstraints gridBagConstraints40 = new GridBagConstraints();
			gridBagConstraints40.fill = java.awt.GridBagConstraints.HORIZONTAL;
			gridBagConstraints40.gridy = 4;
			gridBagConstraints40.weightx = 1.0;
			gridBagConstraints40.anchor = java.awt.GridBagConstraints.WEST;
			gridBagConstraints40.insets = new java.awt.Insets(2, 2, 2, 2);
			gridBagConstraints40.gridwidth = 2;
			gridBagConstraints40.weighty = 1.0D;
			gridBagConstraints40.gridx = 0;

		    summarySettingsPanel = new JPanel();
		    summarySettingsPanel.setLayout(new GridBagLayout());
		    summarySettingsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Review Code Generation Properties",
					javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
					javax.swing.border.TitledBorder.DEFAULT_POSITION, null, PortalLookAndFeel.getPanelLabelColor()));

			summarySettingsPanel.add(projectSettingsPanel.getSummaryPanel(), gridBagConstraints10);
			summarySettingsPanel.add(writableApiSettingsPanel.getSummaryPanel(), gridBagConstraints11);
			summarySettingsPanel.add(modelSettingsPanel.getSummaryPanel(), gridBagConstraints20);
			summarySettingsPanel.add(securitySettingsPanel.getSummaryPanel(), gridBagConstraints21);
			summarySettingsPanel.add(codegenSettingsPanel.getSummaryPanel(), gridBagConstraints30);
			
		    summarySettingsPanel.validate();
		//}
		return summarySettingsPanel;
	}
	
	public List<PanelValidator> getPanelValidators() {
		return panelValidators;
	}

	public ValidationResultModel getValidationModel() {
		return validationModel;
	}

	private Map<String,String> getPropsMap(){
		Map<String,String> propsMap=new TreeMap<String,String>();

		// Project properties
		propsMap.putAll(projectSettingsPanel.getPropsMap());
		
		// Model properties
		propsMap.putAll(modelSettingsPanel.getPropsMap());
		
		// Override the MODEL_FILE_PATH property to point to the project generation 
		// models sub-directory, as we now copying the model file to this sub-directory
		propsMap.put("MODEL_FILE_PATH", (
				(projectSettingsPanel.getProjectDir()
				+ File.separator 
				+ MODELS_DIR)
				+ File.separator 
				+ modelSettingsPanel.getModelFileName()).replace('\\', '/'));
		
		// Code Generation properties
		propsMap.putAll(codegenSettingsPanel.getPropsMap());
		
		// Writable API properties
		propsMap.putAll(writableApiSettingsPanel.getPropsMap());
		
		// Writable API properties
		propsMap.putAll(securitySettingsPanel.getPropsMap());
		
		// Retrieve and add 'Additional' codegen properties not managed by the Workbench but 
		// still required by the SDK Codegen process
		Properties addlProps = ResourceManager.getAdditionalCodegenPropertiesManager().getDeployProperties();

		Iterator<Object> keys = addlProps.keySet().iterator();
		while (keys.hasNext())
		{
			String key = (String) keys.next();
			String value = (String) addlProps.get(key);
			propsMap.put(key, value);
		}

		return propsMap;
	}
	
	public void setDirty(boolean isDirty){
		this.isDirty = isDirty;
	}
	
	private void setPropsLoaded(boolean isPropsLoaded){
		this.isPropsLoaded = isPropsLoaded;
	}	
	
    public void setProjectDir(String projectDirPath){
    	projectSettingsPanel.setProjectDir(projectDirPath);
    }
	
    public void disableWritableAPI(){
    	writableApiSettingsPanel.disableWritableAPI();
    }
	
    public void enableWritableAPI(){
    	writableApiSettingsPanel.enableWritableAPI();
    }
    
    public void disableInstanceAndAttributeLevelSecurity(){
    	securitySettingsPanel.disableInstanceAndAttributeLevelSecurity();
    }
	
    public void enableInstanceAndAttributeLevelSecurity(){
    	securitySettingsPanel.enableInstanceAndAttributeLevelSecurity();
    }
    
    public boolean isIso21090DatatypesEnabled(){
    	return codegenSettingsPanel.isIso21090DatatypesEnabled();
    }
    
    public void loadCodegenProperties(){
    	
    	String projectDir = projectSettingsPanel.getProjectDir();
    	
		// Initialize WorkbenchPropertiesManager
		propsMgr = ResourceManager.getCodegenPropertiesManager(projectDir); 
        
		initPanels();
		resetMainTabbedPaneComponents();
		
		//Restore projectDir - necessary in case an empty (initial) project dir was selected
		projectSettingsPanel.setProjectDir(projectDir);
    	
		initValidation();
		
        setDirty(false);
        setPropsLoaded(true);
        
		validateInput();
		
		// Provide confirmation to user that the properties have been successfully loaded
		JOptionPane.showMessageDialog(this,"The codegen properties have been successfully loaded");
    }    
    
    private void initPanels(){
    	
        //Initialize Panels
        projectSettingsPanel=new ProjectSettingsPanel(propsMgr, propsValidator);
        modelSettingsPanel=new ModelSettingsPanel(propsMgr, propsValidator);
        codegenSettingsPanel=new CodegenSettingsPanel(propsMgr, propsValidator);
        writableApiSettingsPanel=new WritableApiSettingsPanel(propsMgr, propsValidator);
        securitySettingsPanel=new SecuritySettingsPanel(propsMgr, propsValidator);
        logViewerPanel=new LogViewerPanel(propsValidator,projectSettingsPanel.getProjectDir());
        
        panelValidators = new ArrayList<PanelValidator>();
        panelValidators.add((PanelValidator)projectDirSettingsPanel);
        panelValidators.add((PanelValidator)projectSettingsPanel);
        panelValidators.add((PanelValidator)modelSettingsPanel);
        panelValidators.add((PanelValidator)codegenSettingsPanel);
        panelValidators.add((PanelValidator)writableApiSettingsPanel);
        panelValidators.add((PanelValidator)securitySettingsPanel);
        panelValidators.add((PanelValidator)logViewerPanel);
        
        codegenSettingsPanel.setParentContainer(this);
        securitySettingsPanel.setParentContainer(this);
    }
    
    private void resetMainTabbedPaneComponents(){
    	
    	mainTabbedPane.setComponentAt(PROJECT_TAB_INDEX, new IconFeedbackPanel(this.validationModel, projectSettingsPanel.getSettingsPanel()));
    	mainTabbedPane.setComponentAt(MODEL_TAB_INDEX, new IconFeedbackPanel(this.validationModel, modelSettingsPanel.getSettingsPanel()));
    	mainTabbedPane.setComponentAt(CODEGEN_TAB_INDEX, new IconFeedbackPanel(this.validationModel, codegenSettingsPanel.getSettingsPanel()));
    	mainTabbedPane.setComponentAt(WRITABLE_API_TAB_INDEX, new IconFeedbackPanel(this.validationModel, writableApiSettingsPanel.getSettingsPanel()));
    	mainTabbedPane.setComponentAt(SECURITY_TAB_INDEX, new IconFeedbackPanel(this.validationModel, securitySettingsPanel.getSettingsPanel()));
    	mainTabbedPane.setComponentAt(LOG_VIEWER_TAB_INDEX, new IconFeedbackPanel(this.validationModel, logViewerPanel.getSettingsPanel()));    	

    }  
    
    public void togglePanels(){
	    if (!isPropsLoaded){
	    	mainTabbedPane.setEnabledAt(PROJECT_TAB_INDEX, false);
	    	mainTabbedPane.setEnabledAt(MODEL_TAB_INDEX, false);
	    	mainTabbedPane.setEnabledAt(CODEGEN_TAB_INDEX, false);
	    	mainTabbedPane.setEnabledAt(WRITABLE_API_TAB_INDEX, false);
	    	mainTabbedPane.setEnabledAt(SECURITY_TAB_INDEX, false);
	    	mainTabbedPane.setEnabledAt(GENERATE_TAB_INDEX, false);
	    	mainTabbedPane.setEnabledAt(LOG_VIEWER_TAB_INDEX, false);
	    } else {
	    	mainTabbedPane.setEnabledAt(PROJECT_TAB_INDEX, true);
	    	mainTabbedPane.setEnabledAt(MODEL_TAB_INDEX, true);
	    	mainTabbedPane.setEnabledAt(CODEGEN_TAB_INDEX, true);
	    	mainTabbedPane.setEnabledAt(WRITABLE_API_TAB_INDEX, true);
	    	mainTabbedPane.setEnabledAt(SECURITY_TAB_INDEX, true);
	    	mainTabbedPane.setEnabledAt(GENERATE_TAB_INDEX, true);
	    	mainTabbedPane.setEnabledAt(LOG_VIEWER_TAB_INDEX, true);
	    }
    }    
} 
