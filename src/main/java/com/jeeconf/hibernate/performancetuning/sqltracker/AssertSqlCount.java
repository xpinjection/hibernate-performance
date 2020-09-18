package com.jeeconf.hibernate.performancetuning.sqltracker;

import static com.jeeconf.hibernate.performancetuning.sqltracker.QueryCountInfoHolder.getQueryInfo;

public class AssertSqlCount {
    public static void reset() {
        getQueryInfo().clear();
    }

    public static void assertSelectCount(int expectedSelectCount) {
        assertSqlCount("select", expectedSelectCount, getQueryInfo().getSelectCount());
    }

    public static void assertNextvalCount(int expectedNextvalCount) {
        assertSqlCount("select nextval", expectedNextvalCount, getQueryInfo().getNextvalCount());
    }

    public static void assertUpdateCount(int expectedUpdateCount) {
        assertSqlCount("update", expectedUpdateCount, getQueryInfo().getUpdateCount());
    }

    public static void assertInsertCount(int expectedInsertCount) {
        assertSqlCount("insert", expectedInsertCount, getQueryInfo().getInsertCount());
    }

    public static void assertDeleteCount(int expectedDeleteCount) {
        assertSqlCount("delete", expectedDeleteCount, getQueryInfo().getDeleteCount());

    }

    public static void assertCallCount(int expectedCallCount) {
        assertSqlCount("call", expectedCallCount, getQueryInfo().getCallCount());
    }

    private static void assertSqlCount(String statement, int expectedCount, int actualCount) {
        if (expectedCount != actualCount) {
            throw new SqlCountMismatchException(statement, expectedCount, actualCount);
        }
    }
}
