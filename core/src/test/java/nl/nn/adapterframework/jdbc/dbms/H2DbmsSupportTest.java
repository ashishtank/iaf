package nl.nn.adapterframework.jdbc.dbms;

import static org.junit.Assert.assertEquals;

import java.sql.SQLException;

import org.junit.Test;

import nl.nn.adapterframework.jdbc.JdbcException;

public class H2DbmsSupportTest {

	@Test
	public void testConvertQueryOracleToH2() throws JdbcException, SQLException {
		String query = "DROP SEQUENCE SEQ_IBISSTORE";
		String expected = "DROP SEQUENCE IF EXISTS SEQ_IBISSTORE";
		String translatedQuery = (new H2DbmsSupport()).convertQuery(query, "Oracle");
		assertEquals(expected, translatedQuery);
	}

	@Test
	public void testConvertQueryH2ToH2() throws JdbcException, SQLException {
		String query = "SELECT COUNT(*) FROM IBISSTORE";
		String expected = query;
		String translatedQuery = (new H2DbmsSupport()).convertQuery(query, "H2");
		assertEquals(expected, translatedQuery);
	}

	@Test
	public void testConvertMultipleQueriesOracleToH2() throws JdbcException, SQLException {
		String query = "--------\n  --drop--\r\n--------\nDROP SEQUENCE SEQ_IBISSTORE;\nselect count(*) from ibisstore;";
		String expected = "--------\n  --drop--\r\n--------\nDROP SEQUENCE IF EXISTS SEQ_IBISSTORE;\nselect count(*) from ibisstore;";
		String translatedQuery = (new H2DbmsSupport()).convertQuery(query, "Oracle");
		assertEquals(expected, translatedQuery);
	}
}
