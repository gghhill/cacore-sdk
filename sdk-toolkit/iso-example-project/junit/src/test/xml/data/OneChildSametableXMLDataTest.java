package test.xml.data;

import gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Currency;
import gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Note;
import gov.nih.nci.iso21090.St;
import gov.nih.nci.system.query.hibernate.HQLCriteria;

import java.util.Collection;
import java.util.Iterator;

import test.xml.mapping.SDKXMLDataTestBase;


public class OneChildSametableXMLDataTest extends SDKXMLDataTestBase
{
	public static String getTestCaseName()
	{
		return "One Child Same Table XML Data Test Case";
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
		Currency searchObject = new Currency();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Currency",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Currency result = (Currency)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateIso90210Element(result, "id", "extension", result.getId().getExtension());
			validateIso90210Element(result, "country", "value", result.getCountry().getValue());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Currency result2 = (Currency)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getCountry());
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
		Note searchObject = new Note();
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Note",searchObject );

		assertNotNull(results);
		assertEquals(3,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Note result = (Note)i.next();
			toXML(result);
			
			validateClassElements(result);
			validateAttribute(result,"id",result.getId().getExtension());
			validateAttribute(result,"country",result.getCountry());
			validateAttribute(result,"value",result.getValue());
			
			assertTrue(validateXMLData(result, searchObject.getClass()));

			Currency result2 = (Currency)fromXML(result);
			
			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getCountry());
			assertNotNull(result.getValue());
		}
	}

	/**
	 * Uses Nested Search Criteria for inheritance as association in search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testAssociationNestedSearch1() throws Exception
	{
		Currency searchObject = new Currency();
		St  st = new St();
		st.setValue("USA");
		searchObject.setCountry(st);
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Note",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Note result = (Note)i.next();
			toXML(result);
			Note result2 = (Note)fromXML(result);

			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getValue());
		}
	}
	
	public void testAssociationNestedSearchHQL1() throws Exception {
		HQLCriteria hqlCriteria = new HQLCriteria(
				"from gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Note note "
						+ " where note.country='USA'");
		Collection results = search(hqlCriteria,
				"gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Note");

		assertNotNull(results);
		assertEquals(1, results.size());

		for (Iterator i = results.iterator(); i.hasNext();) {
			Note result = (Note) i.next();
			toXML(result);
			Note result2 = (Note) fromXML(result);

			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getValue());
		}
	}

	/**
	 * Uses Nested Search Criteria for inheritance as association in search
	 * Verifies that the results are returned 
	 * Verifies size of the result set
	 * Verifies that none of the attribute is null
	 * 
	 * @throws Exception
	 */
	public void testAssociationNestedSearch2() throws Exception
	{
		Note searchObject = new Note();
		St  st = new St();
		st.setValue("Germany");
		searchObject.setCountry(st);
		Collection results = getApplicationService().search("gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Currency",searchObject );

		assertNotNull(results);
		assertEquals(1,results.size());
		
		for(Iterator i = results.iterator();i.hasNext();)
		{
			Currency result = (Currency)i.next();
			toXML(result);
			Currency result2 = (Currency)fromXML(result);

			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getCountry());
		}
	}
	
	public void testAssociationNestedSearchHQL2() throws Exception {
		HQLCriteria hqlCriteria = new HQLCriteria(
				"from gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Currency currency "
						+ " where currency.country='USA'");
		Collection results = search(hqlCriteria,
				"gov.nih.nci.cacoresdk.domain.inheritance.onechild.sametable.Currency");

		assertNotNull(results);
		assertEquals(1, results.size());

		for (Iterator i = results.iterator(); i.hasNext();) {
			Currency result = (Currency) i.next();
			toXML(result);
			Currency result2 = (Currency) fromXML(result);

			assertNotNull(result2);
			assertNotNull(result2.getId());
			assertNotNull(result2.getCountry());
		}
	}
}
