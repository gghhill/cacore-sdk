
package gov.nih.nci.restgen.generated.client;

import javax.xml.ws.WebFault;


/**
 * This class was generated by Apache CXF 2.7.2
 * 2013-02-21T12:51:05.030-05:00
 * Generated source version: 2.7.2
 */

@WebFault(name = "NoSuchCustomer", targetNamespace = "http://customerservice.example.com/")
public class NoSuchCustomer_Exception extends Exception {
    
    private gov.nih.nci.restgen.generated.client.NoSuchCustomer noSuchCustomer;

    public NoSuchCustomer_Exception() {
        super();
    }
    
    public NoSuchCustomer_Exception(String message) {
        super(message);
    }
    
    public NoSuchCustomer_Exception(String message, Throwable cause) {
        super(message, cause);
    }

    public NoSuchCustomer_Exception(String message, gov.nih.nci.restgen.generated.client.NoSuchCustomer noSuchCustomer) {
        super(message);
        this.noSuchCustomer = noSuchCustomer;
    }

    public NoSuchCustomer_Exception(String message, gov.nih.nci.restgen.generated.client.NoSuchCustomer noSuchCustomer, Throwable cause) {
        super(message, cause);
        this.noSuchCustomer = noSuchCustomer;
    }

    public gov.nih.nci.restgen.generated.client.NoSuchCustomer getFaultInfo() {
        return this.noSuchCustomer;
    }
}
