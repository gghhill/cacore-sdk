/*
 * David Bismut, davidou@mageos.com
 * Intern, SETLabs, Infosys Technologies Ltd. May 2004 - Jul 2004
 * Ecole des Mines de Nantes, France
 */


package gov.nih.nci.restgen.ui.main;

import java.awt.event.MouseEvent;
import java.util.EventListener;


public interface CloseListener extends EventListener {
	public void closeOperation(MouseEvent e);
}
