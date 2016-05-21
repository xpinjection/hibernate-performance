package com.jeeconf.hibernate.performancetuning.sqltracker;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 12/6/15
 */
public class QueryCountInfoHandler implements QueryHandler {

    @Override
    public void handleSql(String sql) {
        QueryType queryType = getQueryType(sql);
        QueryCountInfo queryCountInfo = QueryCountInfoHolder.getQueryInfo();
        switch (queryType) {
            case SELECT:
                queryCountInfo.incrementSelectCount();
                break;
            case INSERT:
                queryCountInfo.incrementInsertCount();
                break;
            case UPDATE:
                queryCountInfo.incrementUpdateCount();
                break;
            case DELETE:
                queryCountInfo.incrementDeleteCount();
                break;
            case CALL:
                queryCountInfo.incrementCallCount();
                break;
            default:
                throw new IllegalArgumentException("There is no QueryType hander:" + queryType);

        }
    }

    protected QueryType getQueryType(String query) {
        query = query.toLowerCase();
        final String trimmedQuery = removeRedundantSymbols(query);
        final char firstChar = trimmedQuery.charAt(0);

        final QueryType type;
        switch (firstChar) {
            case 'w': // query can be started 'with'
            case 's':
                type = QueryType.SELECT;
                break;
            case 'i':
                type = QueryType.INSERT;
                break;
            case 'u':
                type = QueryType.UPDATE;
                break;
            case 'd':
                type = QueryType.DELETE;
                break;
            case 'c':
            case '?':
                type = QueryType.CALL;
                break;
            default:
                throw new AssertionError("Unknown QueryType");
        }
        return type;
    }

    private String removeRedundantSymbols(String query) {
        return query.replaceAll("--.*\n", "").replaceAll("\n", "").replaceAll("/\\*.*\\*/", "").trim();
    }

}
