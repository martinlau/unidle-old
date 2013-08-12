package org.un_idle.domain;

public enum TimeUnit {

    SECOND("common.period.second"),

    MINUTE("common.period.minute"),

    HOUR("common.period.hour"),

    DAY("common.period.day"),

    WEEK("common.period.week"),

    MONTH("common.period.month"),

    YEAR("common.period.year"),

    UNKNOWN("common.period.unknown");

    private final String messageKey;

    TimeUnit(final String messageKey) {
        this.messageKey = messageKey;
    }

    public String getMessageKey() {
        return messageKey;
    }

}
