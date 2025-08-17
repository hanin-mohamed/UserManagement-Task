package com.task.employeemanagement.auth.entity;

import com.task.employeemanagement.users.entity.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;


import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class JWTToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String token;

    @CreationTimestamp
    private Date createdAt;
    private Date expiredAt;
    boolean isExpired= false;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}
