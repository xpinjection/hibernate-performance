package com.jeeconf.hibernate.performancetuning.sqlformatter;

import com.p6spy.engine.spy.appender.MessageFormattingStrategy;
import org.hibernate.engine.jdbc.internal.BasicFormatterImpl;
import org.hibernate.engine.jdbc.internal.Formatter;

/**
 * Created by Igor Dmitriev / Mikalai Alimenkou on 1/3/16
 */
public class SimpleFormatter implements MessageFormattingStrategy {

    private static final Formatter HIBERNATE_SQL_FORMATTER = new BasicFormatterImpl();

    @Override
    public String formatMessage(int connectionId, String now, long elapsed, String category, String prepared, String sql) {
        if (sql.isEmpty()) {
            return "";
        }
        String batch = "batch".equals(category) ? " add to batch " : "";
        return String.format("Hibernate: %s %s {elapsed: %dms}", batch, HIBERNATE_SQL_FORMATTER.format(sql), elapsed);
    }
}

