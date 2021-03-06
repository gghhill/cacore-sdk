	@DELETE
	$PathParamPath$
	@Consumes("application/xml")
	public $DeleteReturnType$ remove$ResourceName$($PathParam$)
	{
		try
		{
			gov.nih.nci.restgen.generated.client.$ServiceName$ service = new gov.nih.nci.restgen.generated.client.$ServiceName$();
			gov.nih.nci.restgen.generated.client.$PortType$ port = service.get$PortName$();
   	        	$if(ReturnTypeNotVoid)$ return $endif$ port.$OperationName$($OperationParameters$);
			$if(ReturnTypeResponse)$				
			ResponseBuilder builder = Response.status(Status.OK);
			builder.type("application/xml");
			StringBuffer buffer = new StringBuffer();
			buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			buffer.append("<response>");
			buffer.append("<type>MESSAGE</type>");
			buffer.append("<code>DELETE</code>");
			buffer.append("<resource>$ResourceName$</resource>");
			buffer.append("<path>$PathParamPathShort$</path>");
			buffer.append("<message>Successfully deleted $ResourceName$</message>");
			buffer.append("</response>");
			builder.entity(buffer.toString());
			return builder.build();
			 $endif$
   	        	
		}
		$ResourceException; separator="\n"$
		catch(WebApplicationException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	