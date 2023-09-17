package aswemake.task;

import aswemake.task.dto.FindPriceRequestDto;
import aswemake.task.dto.ProductRequestDto;
import aswemake.task.enums.UserRole;
import aswemake.task.model.PriceHistory;
import aswemake.task.model.Product;
import aswemake.task.model.User;
import aswemake.task.repository.PriceHistoryRepository;
import aswemake.task.repository.ProductRepository;
import aswemake.task.service.ProductService;
import org.json.JSONException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ProductServiceTest {

  @Autowired
  private ProductService productService;

  @Autowired
  private ProductRepository productRepository;

  @Autowired
  private PriceHistoryRepository priceHistoryRepository;

  private User martUSer;
  private User testUser;
  private Product testProduct;
  private String time;

  @BeforeEach
  public void setUp() {
    // 테스트를 위한 User와 Product 생성 및 저장
    martUSer = new User("테스트 유저", "1128", UserRole.MART);
    testProduct = new Product(new ProductRequestDto("Test Product", 10000, "Y"));
    productRepository.save(testProduct);
    time = "2023-09-17 14:15:33";

  }

  @Test
  @DisplayName("특정 시점의 상품 가격 조회")
  public void testFindPricesByProductIdAndTime() throws JSONException {
    FindPriceRequestDto requestDto = new FindPriceRequestDto(15L, time);
    ResponseEntity<?> responseEntity = productService.findPricesByProductIdAndTime(requestDto);
    assertEquals(200, responseEntity.getStatusCodeValue());
  }

  @Test
  @DisplayName("상품 생성")
  public void testCreateProduct() {
    // 상품 생성 응답 테스트
    ProductRequestDto requestDto = new ProductRequestDto("테스트 상품", 10000, "Y");
    ResponseEntity<?> responseEntity = productService.createProduct(requestDto);
    assertEquals(200, responseEntity.getStatusCodeValue());

    // 상품과 가격 이력이 생성되었는지 확인
    Product createdProduct = productRepository.findByName("테스트 상품");
    assertNotNull(createdProduct);
    PriceHistory priceHistory = priceHistoryRepository.findByProduct(createdProduct);
    assertNotNull(priceHistory);
    assertEquals(10000, priceHistory.getPrice());

  }

  @Test
  @DisplayName("상품 수정")
  public void testUpdateProduct() {
    // 상품 수정 테스트
    ProductRequestDto requestDto = new ProductRequestDto("상품 업데이트", 20000, "Y");
    ResponseEntity<?> responseEntity = productService.updateProduct(testProduct.getId(), requestDto);
    assertEquals(200, responseEntity.getStatusCodeValue());

    // 상품과 가격 이력이 수정되었는지 확인
    Product updatedProduct = productRepository.findById(testProduct.getId()).orElse(null);
    assertNotNull(updatedProduct);
    assertEquals("상품 업데이트", updatedProduct.getName());
    PriceHistory priceHistory = priceHistoryRepository.findByProduct(updatedProduct);
    assertNotNull(priceHistory);
    assertEquals(20000, priceHistory.getPrice());

  }

  @Test
  @DisplayName("상품 삭제")
  public void testDeleteProduct() {
    // 상품 삭제 테스트
    ResponseEntity<?> responseEntity = productService.deleteProduct(testProduct.getId());
    assertEquals(200, responseEntity.getStatusCodeValue());

    // 상품과 가격 이력이 삭제되었는지 확인
    Product deletedProduct = productRepository.findById(testProduct.getId()).orElse(null);
    assertNull(deletedProduct);
    PriceHistory priceHistory = priceHistoryRepository.findByProduct(testProduct);
    assertNull(priceHistory);

  }
}
