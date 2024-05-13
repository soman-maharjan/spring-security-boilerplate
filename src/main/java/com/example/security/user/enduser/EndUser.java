package com.example.security.user.enduser;

import com.example.security.user.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Getter
@Setter
@ToString
public class EndUser extends User {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Integer id;

    private UUID referenceId;

    private String firstName;

    private String middleName;

    private String lastName;

    @Transient
    private String fullName;

    @Column(length = 1000)
    private String imageUrl;

    public String getFullName() {
        return Stream
                .of(this.firstName, this.middleName, this.lastName)
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.joining(" "));
    }
}
