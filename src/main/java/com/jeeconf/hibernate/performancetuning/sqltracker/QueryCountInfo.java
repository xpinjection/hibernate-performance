package com.jeeconf.hibernate.performancetuning.sqltracker;

import lombok.Getter;

@Getter
public class QueryCountInfo {
    private int selectCount;
    private int nextvalCount;
    private int insertCount;
    private int updateCount;
    private int deleteCount;
    private int callCount;

    public void incrementSelectCount() {
        selectCount++;
    }

    public void incrementInsertCount() {
        insertCount++;
    }

    public void incrementNextvalCount() {
        nextvalCount++;
    }

    public void incrementUpdateCount() {
        updateCount++;
    }

    public void incrementDeleteCount() {
        deleteCount++;
    }

    public void incrementCallCount() {
        callCount++;
    }

    public void clear() {
        selectCount = 0;
        nextvalCount = 0;
        insertCount = 0;
        updateCount = 0;
        deleteCount = 0;
        callCount = 0;
    }

    public int countAll() {
        return selectCount + nextvalCount + insertCount + updateCount + deleteCount + callCount;
    }
}
