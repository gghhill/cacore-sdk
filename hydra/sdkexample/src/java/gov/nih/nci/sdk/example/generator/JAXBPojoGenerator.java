package gov.nih.nci.sdk.example.generator;

import gov.nih.nci.sdk.core.ScriptContext;
import gov.nih.nci.sdk.example.generator.util.GeneratorUtil;

public class JAXBPojoGenerator extends PojoGenerator {
	
	public JAXBPojoGenerator(ScriptContext _scriptContext) {
		super(_scriptContext);
	}

	protected void init()
	{
		System.out.println("Generating JAXB pojo...");
		// TODO Auto-generated method stub
	}

	public void runProcess()
	{
		//TODO Talk to Prasad about what he wanted to do here.
		//runProcess("JAXBPojo", "jaxb", GeneratorUtil.getJaxbPojoPath(getScriptContext()));
	}
}
