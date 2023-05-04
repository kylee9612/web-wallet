package com.axia.common.util;

public enum DataFormats {
    HEX(0, "text/plain"),
    JSON(1, "application/json"),
    PLAIN_TEXT(2, "text/plain");

    private final int code;
    private final String mediaType;

    private DataFormats(int code, String mediaType) {
        this.code = code;
        this.mediaType = mediaType;
    }

    public int getCode() {
        return this.code;
    }

    public String getMediaType() {
        return this.mediaType;
    }

    public String toString() {
        return "DataFormats(code=" + this.getCode() + ", mediaType=" + this.getMediaType() + ")";
    }
}
