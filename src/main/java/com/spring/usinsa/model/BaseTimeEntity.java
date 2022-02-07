package com.spring.usinsa.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@Getter
@Setter
@MappedSuperclass   // BaseTimeEntity 클래스를 상속하면 아래 필드들을 컬럼으로 인식
@EntityListeners(AuditingEntityListener.class) // Auditing (자동으로 값 매핑) 기능 추가
public abstract class BaseTimeEntity {

    private long createdAt;
    private long updatedAt;

    @PrePersist
    public void prePersist(){
        createdAt = System.currentTimeMillis();
        updatedAt = System.currentTimeMillis();
    }

    @PreUpdate
    public void preUpdate() {
        updatedAt = System.currentTimeMillis();
    }
}
