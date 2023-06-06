package compensation;

import annotation.Compensation;

@Compensation
public enum Status {

    REPORTING("reporting"),
    REVIEWING("reviewing"),
    ACCEPTED("accepted"),
    REJECTED("rejected"),
    PAID("paid");

    private String str;

    Status(String str) {
        this.str = str;
    }

    @Override
    public String toString() {
        return str;
    }

}
