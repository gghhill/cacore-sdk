/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC, SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cacore-sdk/LICENSE.txt for details.
 */

package gov.nih.nci.sdk.ide.generator;

import java.util.Map;

import gov.nih.nci.sdk.core.GeneratorContext;
import gov.nih.nci.sdk.core.MessageManager;
import gov.nih.nci.sdk.ide.core.GroupPanel;
import gov.nih.nci.sdk.ide.core.SDKScreen;
import gov.nih.nci.sdk.ide.modelexplorer.Constants;
import gov.nih.nci.sdk.ide.modelexplorer.SDKUIManager;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Shell;

public class SDKGenerator extends SDKScreen {
	private DomainSelectionEvent dsEvent;
	private GeneratorSelectionEvent gsEvent;
	private Button generateButton;
	
	public SDKGenerator(Shell parent, String title) {
		super(parent, title);
		SDKUIManager.getInstance().registerAsListener(Constants.DOMAIN_SELECTION_EVENT, this);
		SDKUIManager.getInstance().registerAsListener(Constants.GENERATOR_SELECTION_EVENT, this);
	}
	
	@Override
	public void createScreen(Composite composite) {
		composite.setSize(Constants.MODEL_EXPLORER_SCREEN_WIDTH, Constants.MODEL_EXPLORER_SCREEN_HEIGHT);
		
		GridLayout layout = new GridLayout();
		layout.horizontalSpacing = 10;
		layout.marginHeight = 10;
		layout.marginWidth = 10;
		layout.numColumns = 3;
		composite.setLayout(layout);
		
		EPackage rootEPackage = SDKUIManager.getInstance().getRootEPackage();
		GroupPanel dlPanel = new DomainListGroupPanel(composite, SWT.NONE, rootEPackage, "Domain List");
		dlPanel.paint();
		
		GroupPanel gdPanel = new GeneratorDetailsGroupPanel(composite, SWT.NONE, rootEPackage, "Generator Details");
		gdPanel.paint();
		
		GroupPanel glPanel = new GeneratorListGroupPanel(composite, SWT.NONE, rootEPackage, "Generators");
		glPanel.paint();
	}
	
	@Override
	public void okPressed()
	{
		java.util.logging.Logger logger = java.util.logging.Logger.getLogger(gsEvent.getGeneratorInfoVO().getName());
		
		try
		{
			logger.info("LOGGER: dsEvent: " + dsEvent);
			logger.info("LOGGER: gsEvent: " + gsEvent);

			String targetDirectoryName = gsEvent.getGeneratorInfoVO().getProperties().getProperty("PROJECT_SRC");

			if (targetDirectoryName == null || "".equals(targetDirectoryName) == true)
			{
				throw new RuntimeException("Please set target dir property to valid directory name instead of: " + targetDirectoryName);
			}

			java.io.File targetDirectory = new java.io.File(targetDirectoryName);

			//Create Generator Context
			GeneratorContext generatorContext = new GeneratorContext(
				(new java.io.File("generators/sdkexample")).toURI(),
				targetDirectory.toURI(),
				gsEvent.getGeneratorInfoVO().getProperties(),
				SDKUIManager.getInstance().getRootEPackage(),
				new java.util.HashSet<String>(dsEvent.getModelNames()),
				logger
				);
			
			gov.nih.nci.sdk.core.Generator generator = new gov.nih.nci.sdk.core.Generator();

			logger.info("Generation context created successfully.");
			logger.info("Launching generator.");
			generator.compile(generatorContext);
			
			if (generatorContext.hasErrors() == true) 
			{
				logErrorsToLogger(generatorContext);
			}
			else
			{
				logger.info("Generation completed with no errors.");
			}
		}
		catch(Throwable t)
		{
			logger.severe("Encountered exception: " + t.toString());
			t.printStackTrace();
		}
		finally
		{
			super.okPressed();
		}
	}
	
	private void logErrorsToLogger(GeneratorContext _generatorContext)
	{
		for (gov.nih.nci.sdk.core.Message message: _generatorContext.getErrorManager().getMessageList())
		{
			_generatorContext.getLogger().severe(message.toString());
		}
	}
	
	@Override
	public Button createButton(Composite parent, int id, String label, boolean defaultButton) {
		Button button = super.createButton(parent, id, label, defaultButton);
		if (id == IDialogConstants.OK_ID) {
			button.setText("Generate");
			generateButton = button;
			generateButton.setEnabled(false);
		}
		return button;
	}
	
	@Override
	public boolean isResizable() {
		return true;
	}
	
	@Override
	public int open() {
		dsEvent = null;
		gsEvent = null;
		return super.open();
	}
	
	@Override
	public void handleEvent(Event event) {
		System.out.println("SDKGenerator receiving " + event);
		if (event == null) return;
		
		if (event instanceof GeneratorSelectionEvent) {
			gsEvent = (GeneratorSelectionEvent)event;
		}
		
		if (event instanceof DomainSelectionEvent) {
			dsEvent = (DomainSelectionEvent)event;
		}
		
		if (dsEvent != null && gsEvent != null) {
			generateButton.setEnabled(true);
			generateButton.setFocus();
		}
	}
}
