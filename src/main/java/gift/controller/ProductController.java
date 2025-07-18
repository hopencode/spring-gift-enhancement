package gift.controller;

import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }


    @PostMapping
    public ResponseEntity<ProductResponseDto> addProduct(@Valid @RequestBody ProductRequestDto dto) {
        ProductResponseDto added = productService.addProduct(dto);

        return ResponseEntity.status(HttpStatus.CREATED).body(added);
    }

    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        List<ProductResponseDto> products = productService.findAllProducts();

        return ResponseEntity.status( HttpStatus.OK).body(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {

        ProductResponseDto responseDto = productService.findProductById(id);

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductRequestDto dto) {
        return productService.updateProduct(id, dto)
                .map(responseDto -> ResponseEntity.status(HttpStatus.OK).body(responseDto))
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}