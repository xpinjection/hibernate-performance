package com.jeeconf.hibernate.performancetuning.sqltracker;

import org.hibernate.resource.jdbc.spi.StatementInspector;

public class StatementInspectorImpl implements StatementInspector {
    private static final QueryHandler QUERY_HANDLER = new QueryCountInfoHandler();

    @Override
    public String inspect(String sql) {
        QUERY_HANDLER.handleSql(sql);
        return sql;
    }
}
