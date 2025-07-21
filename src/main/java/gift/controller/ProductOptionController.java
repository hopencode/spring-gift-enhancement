package gift.controller;

import gift.dto.ProductOptionRequestDto;
import gift.dto.ProductOptionResponseDto;
import gift.entity.ProductOption;
import gift.service.ProductOptionService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RestController
@RequestMapping("/api/products/{productId}/options")
public class ProductOptionController {
    private ProductOptionService productOptionService;

    public ProductOptionController(ProductOptionService productOptionService) {
        this.productOptionService = productOptionService;
    }

    @GetMapping
    public ResponseEntity<List<ProductOptionResponseDto>> getAllOptions(
            @PathVariable Long productId,
            @PageableDefault(size = 5, sort = "id", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        List<ProductOptionResponseDto> productOptions = productOptionService
                .getOptionList(productId, pageable)
                .getContent();

        return ResponseEntity.status(HttpStatus.OK).body(productOptions);
    }

    @PostMapping
    public ResponseEntity<ProductOptionResponseDto> createOption(
            @PathVariable Long productId,
            @RequestBody @Valid ProductOptionRequestDto productOptionRequestDto
    ) {
        ProductOption saved = productOptionService.addOption(productId, productOptionRequestDto);
        ProductOptionResponseDto responseDto = new ProductOptionResponseDto(
                saved.getId(),
                saved.getOptionName(),
                saved.getOptionQuantity(),
                saved.getProduct().getId()
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PutMapping("/{productOptionId}")
    public ResponseEntity<ProductOptionResponseDto> updateOption(
            @PathVariable Long productId,
            @PathVariable Long productOptionId,
            @RequestBody @Valid ProductOptionRequestDto productOptionRequestDto
    ) {
        ProductOption updated = productOptionService.updateOption(productId, productOptionId, productOptionRequestDto);
        ProductOptionResponseDto responseDto = new ProductOptionResponseDto(
                updated.getOptionName(),
                updated.getOptionQuantity(),
                updated.getProduct().getId()
        );

        return ResponseEntity.status(HttpStatus.OK).body(responseDto);
    }

    @PatchMapping("/{productOptionId}/subtract")
    public ResponseEntity<Void> subtractOptionQuantity(
            @PathVariable("productOptionId") Long productOptionId,
            @RequestBody @Valid ProductOptionRequestDto productOptionRequestDto
    ) {
        productOptionService.subtractQuantity(productOptionId, productOptionRequestDto.getOptionQuantity());
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @DeleteMapping("/{productOptionId}")
    public ResponseEntity<Void> deleteProductOption(
            @PathVariable("productOptionId") Long productOptionId
    ) {
        productOptionService.deleteOption(productOptionId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
