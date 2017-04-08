package com.jeeconf.hibernate.performancetuning.sqltracker;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 12/6/15
 */
public class QueryCountInfoHolder {
    private static final ThreadLocal<QueryCountInfo> QUERY_INFO_HOLDER = new ThreadLocal<QueryCountInfo>() {
        @Override
        protected QueryCountInfo initialValue() {
            return new QueryCountInfo();
        }
    };

    public static QueryCountInfo getQueryInfo() {
        return QUERY_INFO_HOLDER.get();
    }

    public static void clear() {
        QUERY_INFO_HOLDER.get().clear();
    }
}
