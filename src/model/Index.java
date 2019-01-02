package model;

public class Index {

    private int start;
    private int end;

    public Index() {

    }

    /**
     * Constructor of a new Index instance
     *
     * @param start the starting index of the matched word
     * @param length the end index of the matched word
     */
    public Index(int start, int end) {
       this.start = start;
       this.end = end;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void incrementStartAndEnd(int incrementer) {
        this.start = this.start + incrementer;
        this.end = this.end + incrementer;

    }
}
