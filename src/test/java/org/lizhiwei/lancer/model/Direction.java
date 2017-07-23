package org.lizhiwei.lancer.model;

import java.util.Arrays;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class Direction {

    private String detail;
    private String[] steps;
    private byte[] raw;

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String[] getSteps() {
        return steps;
    }

    public void setSteps(String[] steps) {
        this.steps = steps;
    }

    public byte[] getRaw() {
        return raw;
    }

    public void setRaw(byte[] raw) {
        this.raw = raw;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Direction)) return false;

        Direction direction = (Direction) o;

        if (detail != null ? !detail.equals(direction.detail) : direction.detail != null) return false;
        // Probably incorrect - comparing Object[] arrays with Arrays.equals
        if (!Arrays.equals(steps, direction.steps)) return false;
        return Arrays.equals(raw, direction.raw);

    }

    @Override
    public int hashCode() {
        int result = detail != null ? detail.hashCode() : 0;
        result = 31 * result + Arrays.hashCode(steps);
        result = 31 * result + Arrays.hashCode(raw);
        return result;
    }
}
