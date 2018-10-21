package com.example.hibernateimmutable.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.Immutable;

import javax.persistence.*;
import java.util.List;

/**
 * Created by mtumilowicz on 2018-10-21.
 */
@Entity
@Immutable
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder
@ToString
public class Customer {
    @Id
    Integer id;

    String name;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn
    @Immutable
    List<Issue> issues;
}
