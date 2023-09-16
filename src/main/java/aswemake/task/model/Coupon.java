package aswemake.task.model;

import aswemake.task.base.Timestamped;
import aswemake.task.enums.CouponType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "awm_coupon")
public class Coupon extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "coupon_id")
  private Long id;

  private String name;

  @Enumerated(EnumType.STRING)
  private CouponType type;

}
