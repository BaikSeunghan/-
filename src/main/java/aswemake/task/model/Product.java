package aswemake.task.model;

import aswemake.task.base.Timestamped;
import aswemake.task.dto.ProductRequestDto;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "awm_product")
public class Product extends Timestamped {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "product_id")
  private Long id;
  private String name; // 상품 명
  private int price; // 가격
  private String specialCoupon; // Y / N
  @JsonIgnore
  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL)
  private List<PriceHistory> priceHistoryList = new ArrayList<>();

  public Product(ProductRequestDto requestDto) {
    this.name = requestDto.getName();
    this.price = requestDto.getPrice();
    this.specialCoupon = requestDto.getSpecialCoupon();
  }

  public void update(ProductRequestDto requestDto) {
    this.name = requestDto.getName();
    this.price = requestDto.getPrice();
    this.specialCoupon = requestDto.getSpecialCoupon();
  }

//  public void addOrderItem(PriceHistory priceHistory) {
//    priceHistoryList.add(priceHistory);
//    priceHistory.setProduct(this);
//  }
}
