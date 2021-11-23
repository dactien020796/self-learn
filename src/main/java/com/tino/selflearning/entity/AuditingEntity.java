package com.tino.selflearning.entity;

import java.time.Instant;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Getter
@Setter
@MappedSuperclass
public abstract class AuditingEntity {

  @Column(name = "created_date", updatable = false)
  @CreationTimestamp
  private Instant createdDate;

  @Column(name = "modified_date")
  @UpdateTimestamp
  private Instant modifiedDate;
}
