package aswemake.task.base;

import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@Getter
@MappedSuperclass // 상속했을 때, 멤버 변수가 컬럼이 되도록
@EntityListeners(AuditingEntityListener.class) // 생성/수정 시간을 자동으로 반영
public abstract class Timestamped {

    @Column(updatable = false)  // update 쿼리 시 반영 X
    @CreatedDate // 생성 일자
    private LocalDateTime createdAt;

    @LastModifiedDate // 수정 일자
    private LocalDateTime updatedAt;
}
