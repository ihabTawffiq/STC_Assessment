package com.example.stc.domain;

import java.io.Serializable;
import java.util.List;

import lombok.*;

import javax.persistence.*;


@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "permission_groups", catalog = "stc-db", schema = "public")
public class PermissionGroups implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "group_name", nullable = false, length = 2147483647)
    private String groupName;

    @OneToMany(mappedBy = "groupId", fetch = FetchType.LAZY)
    private List<Permissions> permissionsCollection;

    @OneToMany(mappedBy = "permissionGroupId", fetch = FetchType.LAZY)
    private List<Items> itemsCollection;

}
