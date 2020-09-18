package com.jeeconf.hibernate.performancetuning.sqltracker;

public class QueryCountInfoHandler implements QueryHandler {
    @Override
    public void handleSql(String sql) {
        QueryType queryType = getQueryType(sql);
        QueryCountInfo queryCountInfo = QueryCountInfoHolder.getQueryInfo();
        switch (queryType) {
            case SELECT:
                queryCountInfo.incrementSelectCount();
                break;
            case NEXTVAL:
                queryCountInfo.incrementNextvalCount();
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

    private QueryType getQueryType(String query) {
        query = query.toLowerCase();
        String trimmedQuery = removeRedundantSymbols(query);

        if (trimmedQuery.startsWith("select nextval"))
            return QueryType.NEXTVAL;

        char firstChar = trimmedQuery.charAt(0);
        switch (firstChar) {
            case 'w': // query can be started 'with'
            case 's':
                return QueryType.SELECT;
            case 'i':
                return QueryType.INSERT;
            case 'u':
                return QueryType.UPDATE;
            case 'd':
                return QueryType.DELETE;
            case 'c':
            case '?':
                return QueryType.CALL;
            default:
                throw new AssertionError("Unknown QueryType: " + trimmedQuery);
        }
    }

    private String removeRedundantSymbols(String query) {
        return query.replaceAll("--.*\n", "")
                .replaceAll("\n", "")
                .replaceAll("/\\*.*\\*/", "")
                .trim();
    }
}
