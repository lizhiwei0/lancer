package org.lizhiwei.lancer.model;

import java.util.List;

/**
 * Created by lizhiwe on 7/16/2017.
 */
public class Command {

    private String from;
    private String to;

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getLength() {
        return length;
    }

    public void setLength(long length) {
        this.length = length;
    }

    public short getHeavy() {
        return heavy;
    }

    public void setHeavy(short heavy) {
        this.heavy = heavy;
    }

    public List<Direction> getDetails() {
        return details;
    }

    public void setDetails(List<Direction> details) {
        this.details = details;
    }

    private int height;
    private long length;
    private short heavy;

    private List<Direction> details;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Command)) return false;

        Command command = (Command) o;

        if (height != command.height) return false;
        if (length != command.length) return false;
        if (heavy != command.heavy) return false;
        if (!from.equals(command.from)) return false;
        if (!to.equals(command.to)) return false;
        return details.equals(command.details);

    }

    @Override
    public int hashCode() {
        int result = from.hashCode();
        result = 31 * result + to.hashCode();
        result = 31 * result + height;
        result = 31 * result + (int) (length ^ (length >>> 32));
        result = 31 * result + (int) heavy;
        result = 31 * result + details.hashCode();
        return result;
    }
}
