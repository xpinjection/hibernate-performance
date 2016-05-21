package com.jeeconf.hibernate.performancetuning.sqltracker;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 12/13/15
 */
public class SqlCountMismatchException extends RuntimeException {
    public SqlCountMismatchException(String statement, int expectedCount, int actualCount) {
        super("Expected " + statement + " query count: " + expectedCount + ", actual: " + actualCount);
    }
}
