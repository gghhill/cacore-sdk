/*L
 *  Copyright Ekagra Software Technologies Ltd.
 *  Copyright SAIC, SAIC-Frederick
 *
 *  Distributed under the OSI-approved BSD 3-Clause License.
 *  See http://ncip.github.com/cacore-sdk/LICENSE.txt for details.
 */

package test.xml.data;

import gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.Flight;
import gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.Passanger;
import gov.nih.nci.iso21090.Ii;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.Collection;
import java.util.Iterator;


public class O2MBidirectionalWJoinXMLDataTest extends SDKXMLDataTestBase
{
	public static String getTestCaseName()
	{
		return "One to Many Bidirectional XML Data Test Case";
	}
	
	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testEntireObjectNestedSearch1() throws Exception
	{
		Flight searchObject = new Flight();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.Flight",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Flight result = (Flight)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateIso90210Element(result, "id", "extension", result.getId().getExtension());
			validateIso90210Element(result, "destination", "value", result.getDestination().getValue());			
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Flight result2 = (Flight)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId().getExtension());   
			assertEquals(II_ROOT_GLOBAL_CONSTANT_VALUE,result2.getId().getRoot());
			assertNotNull(result2.getDestination());
		}
	}

	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testEntireObjectNestedSearch2() throws Exception
	{
		Passanger searchObject = new Passanger();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.Passanger",searchObject );

		assertNotNull(results);
		assertEquals(2,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Passanger result = (Passanger)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateIso90210Element(result, "id", "extension", result.getId().getExtension());
			validateIso90210Element(result, "name", "value", result.getName().getValue());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Passanger result2 = (Passanger)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId().getExtension()); 
			assertEquals(II_ROOT_GLOBAL_CONSTANT_VALUE,result2.getId().getRoot());
			assertNotNull(result2.getName());
		}
	}

	/**
	 * Uses Nested Search Criteria for search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * erifies that the associated object is null
	 * 
	 * @throws Exception
	 */
	public void testZeroAssociatedObjectsNestedSearch1() throws Exception
	{
		Flight searchObject = new Flight();
		Ii ii = new Ii();
		ii.setExtension("1");
		searchObject.setId(ii);
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.Flight",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Flight result = (Flight)i.next();
		toXML(result);
		Flight result2 = (Flight)fromXML(result);
		
		assertNotNull(result2);
		assertNotNull(result2.getId().getExtension());   
		assertEquals(II_ROOT_GLOBAL_CONSTANT_VALUE,result2.getId().getRoot());
		assertNotNull(result2.getDestination());
		
		Collection passangerCollection = result2.getPassangerCollection();
		assertNotNull(passangerCollection);
	}
	
	
	public void testAssociationNestedSearchHQL1() throws Exception {
		HQLCriteria hqlCriteria = new HQLCriteria(
				"from gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.Flight where id='1'");
		Collection results = search(hqlCriteria,
				"gov.nih.nci.cacoresdk.domain.onetomany.bidirectional.withjoin.Flight");

		assertNotNull(results);
		assertEquals(1,results.size());
		
		Iterator i = results.iterator();
		Flight result = (Flight)i.next();
		toXML(result);
		Flight result2 = (Flight)fromXML(result);
		
		assertNotNull(result2);
		assertNotNull(result2.getId().getExtension());
		assertEquals(II_ROOT_GLOBAL_CONSTANT_VALUE,result2.getId().getRoot());
		assertNotNull(result2.getDestination());
		
		Collection passangerCollection = result2.getPassangerCollection();
		assertNotNull(passangerCollection);
	}
}
