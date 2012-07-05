package gov.nih.nci.system.query.example;

import gov.nih.nci.system.query.SDKQuery;

public abstract class ExampleQuery implements SDKQuery {
	private Object example;
	private boolean commit;

	public ExampleQuery(Object example) {
		this.example = example;
	}

	public ExampleQuery(Object example, boolean commit) {
		this.example = example;
		this.commit = commit;
	}
	
	public Object getExample() {
		return example;
	}

	public void setExample(Object example) {
		this.example = example;
	}
	
	public boolean getCommit()
	{
		return commit;
	}
	
	public void setCommit(boolean commit)
	{
		this.commit = commit;
	}
}