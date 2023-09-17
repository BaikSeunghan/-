package aswemake.task.controller;

import aswemake.task.dto.FindPriceRequestDto;
import aswemake.task.dto.ProductRequestDto;
import aswemake.task.security.UserDetailsImpl;
import aswemake.task.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
public class ProductController {

  private final ProductService productService;

  @GetMapping
  public ResponseEntity<?> findPricesByProductIdAndTime(@RequestBody FindPriceRequestDto requestDto) {
    return productService.findPricesByProductIdAndTime(requestDto);
  }

  @PostMapping("/new")
//  @PreAuthorize("hasRole('MART')")
  public ResponseEntity<?> createProduct(@RequestBody @Valid ProductRequestDto requestDto) {
    return productService.createProduct(requestDto);
  }

  @PutMapping("/{productId}")
//  @PreAuthorize("hasRole('MART')")
  public ResponseEntity<?> updateProduct(@PathVariable Long productId,
                                         @RequestBody @Valid ProductRequestDto requestDto) {
    return productService.updateProduct(productId, requestDto);
  }

  @DeleteMapping("/{productId}")
//  @PreAuthorize("hasRole('MART')")
  public ResponseEntity<?> deleteProduct(@PathVariable Long productId) {
    return productService.deleteProduct(productId);
  }


}
