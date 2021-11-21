package com.tino.selflearning.entity;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Table(name = "role")
@Getter
@Setter
public class Role extends AuditingEntity implements GrantedAuthority {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column
  private String name;

  @Column
  private String description;

  @Column
  private Boolean isSystem;

  @ManyToMany(mappedBy = "roles")
  private Set<User> users;

  @Override
  public String getAuthority() {
    return this.name;
  }
}
