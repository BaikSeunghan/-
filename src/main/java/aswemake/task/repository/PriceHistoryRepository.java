package aswemake.task.repository;

import aswemake.task.model.PriceHistory;
import aswemake.task.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PriceHistoryRepository extends JpaRepository<PriceHistory, Long> {


  long deleteByProduct(Product product);

  @Query("SELECT ph.price FROM PriceHistory ph WHERE ph.product.id = :productId " +
      "AND ph.createdAt >= :startTime AND ph.createdAt < :endTime")
  String findPriceByTime(
      @Param("productId") Long productId,
      @Param("startTime") String startTime,
      @Param("endTime") String endTime
  );

  PriceHistory findByProduct(Product createdProduct);
}
