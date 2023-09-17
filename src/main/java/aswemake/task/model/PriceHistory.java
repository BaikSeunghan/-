package aswemake.task.model;

import aswemake.task.base.Timestamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter @Setter
@Table(name = "awm_price_history")
public class PriceHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "history_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id")
  private Product product; // 상품
  private int price; // 가격

  private String createdAt;

  public PriceHistory(Product product, int price, String createdAt) {
    this.product = product;
    product.getPriceHistoryList().add(this);
    this.price = price;
    this.createdAt = createdAt;
  }
}
