package compensation;

import annotation.Compensation;

@Compensation
public enum Status {

    REPORTING,
    REVIEWING,
    ACCEPTED,
    REJECTED,
    PAID
}
