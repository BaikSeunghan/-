package aswemake.task.service;

import aswemake.task.dto.FindPriceRequestDto;
import aswemake.task.dto.ProductRequestDto;
import aswemake.task.dto.ResponseDto;
import aswemake.task.model.PriceHistory;
import aswemake.task.model.Product;
import aswemake.task.model.User;
import aswemake.task.repository.PriceHistoryRepository;
import aswemake.task.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;
  private final PriceHistoryRepository priceHistoryRepository;

  /**
   * 특정 시점의 상품 가격 조회 제공 API.
   * @return 특정 시점 상품 가격.
   */
  public ResponseEntity<?> findPricesByProductIdAndTime(FindPriceRequestDto requestDto) {

    Long productId = requestDto.getProductId();
    String startTime = requestDto.getTime();

    Product product = checkProduct(productId);

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    LocalDateTime parse = LocalDateTime.parse(requestDto.getTime(), formatter);
    LocalDateTime endTime = parse.plusSeconds(3);

    String priceByTime = priceHistoryRepository.findPriceByTime(productId, startTime, endTime.toString());
    return ResponseEntity.ok(new ResponseDto(true, product.getName(), priceByTime));
  }

  /**
   * 상품 생성 API.
   * @param requestDto 상품 생성 Dto.
   * @return 요청 결과 값.
   */
  @Transactional
  public ResponseEntity<?> createProduct(ProductRequestDto requestDto) {

    // 상품 생성
    Product product = new Product(requestDto);
    productRepository.save(product);

    // 생성 시점 상품 가격 이력저장
    PriceHistory priceHistory = new PriceHistory(product, requestDto.getPrice(), formatDateTime());
    priceHistoryRepository.save(priceHistory);

    return ResponseEntity.ok(new ResponseDto(true, "상품이 등록 되었습니다."));

  }

  /**
   * 상품 수정 API.
   * @param productId 상품 id, 
   * @param requestDto requestDto 상품 수정 Dto.
   * @return 요청 결과 값.
   */
  @Transactional
  public ResponseEntity<?> updateProduct(Long productId, ProductRequestDto requestDto) {

    Product product = checkProduct(productId);
    // 상품 수정
    product.update(requestDto);

    // 수정 시점 상품 가격 이력저장
    PriceHistory priceHistory = new PriceHistory(product, requestDto.getPrice(), formatDateTime());
    priceHistoryRepository.save(priceHistory);

    return ResponseEntity.ok(new ResponseDto(true, "상품이 수정 되었습니다."));

  }

  /**
   * 상품 삭제 API.
   * @param productId
   * @return 요청 결과 값.
   */
  @Transactional
  public ResponseEntity<?> deleteProduct(Long productId) {

    Product product = checkProduct(productId);

    // 상품 삭제
    productRepository.deleteById(productId);
    // 상품 가격 이력 삭제
    priceHistoryRepository.deleteByProduct(product);
    return ResponseEntity.ok(new ResponseDto(true, "상품이 삭제 되었습니다."));

  }

  public void checkRole(User user) {
    //    if (!(user.getRole.equal("MART"))) {
//      throw new IllegalStateException("상품 등록은 마트 관리자만 할 수 있습니다.");
//    }
  }

  private String formatDateTime() {

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    return LocalDateTime.now().format(formatter);
  }

  /**
   * 상품 확인
   * @param productId
   * @return
   */
  private Product checkProduct(Long productId) {
    return productRepository.findById(productId).orElseThrow(
        () -> new IllegalArgumentException("존재하지 않는 상품입니다."));
  }
}
