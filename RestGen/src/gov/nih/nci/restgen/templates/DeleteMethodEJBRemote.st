	@DELETE
	$PathParamPath$
	@Consumes("application/xml")
	public $DeleteReturnType$ remove$ResourceName$($PathParam$)
	{
		try
		{
	        $HomeInterface$ ejbHome; 
	        $RemoteInterface$ ejbRemote;
	 
	        java.util.Properties props = new java.util.Properties();
	
	         javax.naming.InitialContext initContext = null; 
	
	        try { 
	        	String jndiPropsFile = "$JNDIProperties$";
				java.io.InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream(jndiPropsFile);
				if (inputStream == null) {
					throw new java.io.FileNotFoundException("JNDI property file '" + jndiPropsFile + "' not found in the classpath");
				}
				props.load(inputStream);	        
	            initContext = new javax.naming.InitialContext(props); 
	            Object objref = initContext.lookup("$JNDIName$"); 
	            ejbHome = ($HomeInterface$)javax.rmi.PortableRemoteObject.narrow(objref,
	                                             $HomeInterface$.class);
	            ejbRemote = ejbHome.create();
	            		$if(ReturnTypeNotVoid)$ return $endif$ ejbRemote.$OperationName$($OperationParamNames$);
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
	        catch(javax.ejb.CreateException e) {
	        	e.printStackTrace();
				ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
				builder.type("application/xml");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				buffer.append("<response>");
				buffer.append("<type>ERROR</type>");
				buffer.append("<code>GET_EJB_LOCAL_1</code>");
				buffer.append("<resource>$ResourceName$</resource>");
				buffer.append("<path>$PathParamPathShort$</path>");
				buffer.append("<message>Failed to create local client for EJB: "+ e.getMessage() + "</message>");
				buffer.append("</response>");
				builder.entity(buffer.toString());
				throw new WebApplicationException(builder.build());
	        }  
	        catch(java.rmi.RemoteException e) {
	        	e.printStackTrace();
				ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
				builder.type("application/xml");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				buffer.append("<response>");
				buffer.append("<type>ERROR</type>");
				buffer.append("<code>GET_EJB_LOCAL_1</code>");
				buffer.append("<resource>$ResourceName$</resource>");
				buffer.append("<path>$PathParamPathShort$</path>");
				buffer.append("<message>Failed to create local client for EJB: "+ e.getMessage() + "</message>");
				buffer.append("</response>");
				builder.entity(buffer.toString());
				throw new WebApplicationException(builder.build());
	        }  
	        catch (Exception e) { 
	        	e.printStackTrace();
				ResponseBuilder builder = Response.status(Status.BAD_REQUEST);
				builder.type("application/xml");
				StringBuffer buffer = new StringBuffer();
				buffer.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
				buffer.append("<response>");
				buffer.append("<type>ERROR</type>");
				buffer.append("<code>GET_EJB_LOCAL_1</code>");
				buffer.append("<resource>$ResourceName$</resource>");
				buffer.append("<path>$PathParamPathShort$</path>");
				buffer.append("<message>Failed to create local client for EJB: "+ e.getMessage() + "</message>");
				buffer.append("</response>");
				builder.entity(buffer.toString());
				throw new WebApplicationException(builder.build());
	        } 
			
		}
		catch(WebApplicationException e)
		{
			e.printStackTrace();
			throw e;
		}
	}
	