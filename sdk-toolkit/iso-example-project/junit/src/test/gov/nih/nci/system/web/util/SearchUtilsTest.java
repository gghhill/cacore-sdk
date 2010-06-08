package test.gov.nih.nci.system.web.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation.Credit;

import gov.nih.nci.cacoresdk.domain.other.datatype.CdDataType;
import gov.nih.nci.cacoresdk.domain.other.datatype.DsetCdDataType;
import gov.nih.nci.cacoresdk.domain.other.datatype.IvlTsDataType;
import gov.nih.nci.iso21090.Cd;
import gov.nih.nci.system.util.ClassCache;
import gov.nih.nci.system.web.util.SearchUtils;
import junit.framework.TestCase;

public class SearchUtilsTest extends TestCase {
	
	private SearchUtils searchUtils;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		ClassCache classCache = new ClassCache();
		searchUtils = new SearchUtils(classCache);
	}
	
	public void xtestExampleBasicQuery(){
		List<String> criteriaList=new ArrayList<String>();
		String query = "Credit[@id=[@extension=3]]";
		criteriaList.add(query);
		try {
			Credit credit=	(Credit)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation",criteriaList);
			assertNotNull(credit.getId());
			assertEquals("3", credit.getId().getExtension());	
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}

	public void xtestExampleBasicQueryWithRoleName(){
		List<String> criteriaList=new ArrayList<String>();
		String query = "Credit[@id=[@extension=3]]&roleName=issuingBank";
		criteriaList.add(query);
		try {
			Credit credit=	(Credit)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation",criteriaList);
			assertNotNull(credit.getId());
			assertEquals("3", credit.getId().getExtension());	
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}

	public void xtestISOExampleBasicQuery(){
		List<String> criteriaList=new ArrayList<String>();
		String query = "Credit[@id=[@extension=3]]";
		criteriaList.add(query);
		try {
			Credit credit=	(Credit)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.inheritance.childwithassociation",criteriaList);
			assertNotNull(credit.getId());
			assertEquals("3", credit.getId().getExtension());	
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}

	public void xtestISOExampleBasicQueryWithRoleName(){
		List<String> criteriaList=new ArrayList<String>();
		String query = "CdDataType[@value1=[@code=CODE1]]";
		criteriaList.add(query);
		try {
			CdDataType cdDataType=	(CdDataType)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.other.datatype",criteriaList);
			assertNotNull(cdDataType.getValue1());
			assertNotNull(cdDataType.getValue1().getCode());
			assertEquals("CODE1", cdDataType.getValue1().getCode());	
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}

	public void xtestISOExampleComplexCdQuery(){
		List<String> criteriaList=new ArrayList<String>();
		String queryText="CdDataType[@value1=[@originalText=[@value=value]]]";
		criteriaList.add(queryText);
		try {
			CdDataType cdDataType=(CdDataType)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.other.datatype",criteriaList);
			assertNotNull(cdDataType.getValue1());
			assertNotNull(cdDataType.getValue1().getOriginalText());
			assertNotNull(cdDataType.getValue1().getOriginalText().getValue());
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}

	public void xtestISOExampleComplexCdQuery2(){
		List<String> criteriaList=new ArrayList<String>();
		String queryText="CdDataType[@value1=[@originalText=[@value=value]]][@value2=[@code=value]]";
		criteriaList.add(queryText);
		try {
			CdDataType cdDataType=(CdDataType)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.other.datatype",criteriaList);
			assertNotNull(cdDataType.getValue1());
			assertNotNull(cdDataType.getValue1().getOriginalText());
			assertNotNull(cdDataType.getValue1().getOriginalText().getValue());
			assertNotNull(cdDataType.getValue2().getCode());
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}
	
	public void xtestISOExampleComplexCdQueryNullFlavor(){
		List<String> criteriaList=new ArrayList<String>();
		String queryText="CdDataType[@value2=[@nullFlavor=NI]]";
		criteriaList.add(queryText);
		try {
			CdDataType cdDataType=(CdDataType)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.other.datatype",criteriaList);
			assertNotNull(cdDataType.getValue2().getNullFlavor());
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}
	
	public void xtestISOExampleComplexDsetQuery(){
		List<String> criteriaList=new ArrayList<String>();
		String queryText = "DsetCdDataType[@value5=[@item=[@code=CODE1][@codeSystem=CODE_SYSTEM1]][@item=[@codeSystem=CODE_SYSTEM2]]]";
		criteriaList.add(queryText);
		try {
			DsetCdDataType dsetCdDataType=(DsetCdDataType)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.other.datatype",criteriaList);
			assertNotNull(dsetCdDataType.getValue5());
			Iterator<Cd> iterator = dsetCdDataType.getValue5().getItem().iterator();
			Cd next = iterator.next();
			Cd next2 = iterator.next();
			if(next2.getCode()==null){
				assertEquals("CODE_SYSTEM2",next2.getCodeSystem());
				assertEquals("CODE1",next.getCode());
				assertEquals("CODE_SYSTEM1",next.getCodeSystem());
			}else{
				assertEquals("CODE_SYSTEM2",next.getCodeSystem());
				assertEquals("CODE1",next2.getCode());
				assertEquals("CODE_SYSTEM1",next2.getCodeSystem());
			}
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}
	
	public void xtestISOExampleComplexDsetQuery2(){
		List<String> criteriaList=new ArrayList<String>();
		String queryText = "DsetCdDataType[@value5=[@item=[@code=CODE1][@codeSystem=CODESYSTEM1]]]";
		criteriaList.add(queryText);
		try {
			DsetCdDataType dsetCdDataType=(DsetCdDataType)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.other.datatype",criteriaList);
			assertNotNull(dsetCdDataType.getValue5());
			Iterator<Cd> iterator = dsetCdDataType.getValue5().getItem().iterator();
			Cd next = iterator.next();
			assertNotNull(next.getCode());
			assertNotNull(next.getCodeSystem());
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}
	
	public void testISOComplexIVLPQDataWidthType(){
		List<String> criteriaList=new ArrayList<String>();
		String queryText = "IvlTsDataType[@value3=[@width=[@value=1]]]";
		criteriaList.add(queryText);
		try {
			IvlTsDataType ivlTsDataType=(IvlTsDataType)searchUtils.buildSearchCriteria("gov.nih.nci.cacoresdk.domain.other.datatype",criteriaList);
			assertNotNull(ivlTsDataType.getValue3().getWidth());
		} catch (Exception ex) {
			String message=getStackTrace(ex);
			fail(message);
		}
	}


	private String getStackTrace(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String stacktrace = sw.toString();
		return stacktrace;
	}
}
