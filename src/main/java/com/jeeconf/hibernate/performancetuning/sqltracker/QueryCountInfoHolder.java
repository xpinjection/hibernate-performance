package com.jeeconf.hibernate.performancetuning.sqltracker;

public class QueryCountInfoHolder {
    private static final ThreadLocal<QueryCountInfo> QUERY_INFO_HOLDER = ThreadLocal.withInitial(QueryCountInfo::new);

    public static QueryCountInfo getQueryInfo() {
        return QUERY_INFO_HOLDER.get();
    }

    public static void clear() {
        QUERY_INFO_HOLDER.get().clear();
    }
}
