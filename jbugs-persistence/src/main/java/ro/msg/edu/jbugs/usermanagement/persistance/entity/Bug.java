package ro.msg.edu.jbugs.usermanagement.persistance.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="bug")
public class Bug extends BaseEntity<Long> {

    public enum Status {
        OPEN,
        IN_PROGRESS,
        FIXED,
        CLOSED,
        REJECTED,
        INFO_NEEDED
    }
    public enum Severity {
        CRITICAL,
        HIGH,
        MEDIUM,
        LOW
    }

    @Transient
    private final static int MAX_STRING_LENGTH = 20;

    @Transient
    private final static int MAX_TITLE_LENGTH = 40;

    @Transient
    private final static int MAX_DESCRIPTION_LENGTH = 800;


    @Column(name = "title", length = MAX_TITLE_LENGTH, nullable = false)
    private String title;

    @Column(name = "description", length = MAX_DESCRIPTION_LENGTH, nullable = false)
    private String description;

    @Column(name = "version", length = MAX_STRING_LENGTH, nullable = false)
    private String version;

    @Column(name = "targetdate", length = MAX_STRING_LENGTH, nullable = false)
    private Date targetDate;

    @Column(name = "status", length = MAX_STRING_LENGTH, nullable = false)
    private Status status;

    @Column(name = "fixedVersion", length = MAX_STRING_LENGTH, nullable = false)
    private String fixedVersion;

    @ManyToOne(optional = false)
    @Column(name = "createdBy", nullable = false)
    private User createdBy;

    @ManyToOne(optional = false)
    @Column(name = "createdBy", nullable = false)
    private User assignedTos;




}
