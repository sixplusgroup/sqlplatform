package com.qiao.ga.db;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.select.Select;

public class SeedExtractor {

	private final String sql;

	public SeedExtractor(String sql) {
		this.sql = sql;
	}
	
	public Seeds extract() {
		Select stmt;
		try {
			stmt = (Select) CCJSqlParserUtil.parse(sql);
		} catch (JSQLParserException e) {
			throw new RuntimeException(e);
		}
		
		Seeds seeds = new Seeds();
		stmt.getSelectBody().accept(new SeedVisitor(seeds));

		return seeds;
	}
}
