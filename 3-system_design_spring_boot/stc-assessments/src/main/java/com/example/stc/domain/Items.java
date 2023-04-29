package com.example.stc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.example.stc.model.ItemType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Table(name = "items", catalog = "stc-db", schema = "public")
public class Items implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;

    @Basic(optional = false)
    @Column(name = "type", nullable = false, length = 2147483647)
    @Enumerated(EnumType.STRING)
    private ItemType type;

    @Basic(optional = false)
    @Column(name = "name", nullable = false, length = 2147483647)
    private String name;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "itemId", fetch = FetchType.EAGER)
    private List<Files> filesCollection;


    @JoinColumn(name = "permission_group_id", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionGroups permissionGroupId;
}
