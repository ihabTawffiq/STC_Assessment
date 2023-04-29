package com.example.stc.domain;

import com.example.stc.model.PermissionLevels;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "permissions", catalog = "stc-db", schema = "public", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"user_email"})})

public class Permissions implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "user_email", nullable = false, length = 2147483647)
    private String userEmail;

    @Basic(optional = false)
    @Column(name = "permission_level", nullable = false, length = 2147483647)
    @Enumerated(EnumType.STRING)
    private PermissionLevels permissionLevel;

    @JoinColumn(name = "group_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionGroups groupId;

}
