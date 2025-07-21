package gift.service;

import gift.dto.ProductOptionRequestDto;
import gift.dto.ProductOptionResponseDto;
import gift.entity.Product;
import gift.entity.ProductOption;
import gift.exception.ProductOptionExceptions;
import gift.repository.ProductOptionRepository;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;

@Service
public class ProductOptionService {
    private final ProductOptionRepository productOptionRepository;
    private final ProductService productService;

    public ProductOptionService(ProductOptionRepository productOptionRepository, ProductService productService) {
        this.productOptionRepository = productOptionRepository;
        this.productService = productService;
    }

    public Page<ProductOptionResponseDto> getOptionList(Long productId, Pageable pageable) {
        return productOptionRepository
                .findAllByProductId(productId, pageable)
                .map(productOption -> new ProductOptionResponseDto(
                        productOption.getId(),
                        productOption.getOptionName(),
                        productOption.getOptionQuantity(),
                        productOption.getProduct().getId()));
    }

    public ProductOption addOption(Long productId, ProductOptionRequestDto productOptionRequestDto) {
        Product product = productService.findById(productId);

        productOptionRepository.findByProductIdAndOptionName(productId, productOptionRequestDto.getOptionName())
                .ifPresent(productOption -> {
                    throw new ProductOptionExceptions.DuplicateOptionException(productId);
                });

        return productOptionRepository.save(new ProductOption(
                product,
                productOptionRequestDto.getOptionName(),
                productOptionRequestDto.getOptionQuantity()
        ));
    }

    public ProductOption updateOption(Long productId, Long optionId, ProductOptionRequestDto productOptionRequestDto) {
        Product product = productService.findById(productId);

        ProductOption productOption = productOptionRepository.findById(optionId)
                .orElseThrow(() -> new NoSuchElementException("옵션을 찾을 수 없습니다."));

        if (productOption.getProduct().getId() != product.getId()) {
            throw new ProductOptionExceptions.OptionAndProductMismatchException(product.getName(), productOption.getOptionName());
        }

        productOptionRepository.findByProductIdAndOptionName(productOption.getProduct().getId(), productOptionRequestDto.getOptionName())
                .ifPresent(o -> {
                    throw new ProductOptionExceptions.DuplicateOptionException(productOption.getProduct().getId());
                });

        ProductOption updatedOption = new ProductOption(
                optionId,
                productOption.getProduct(),
                productOptionRequestDto.getOptionName(),
                productOptionRequestDto.getOptionQuantity()
        );

        return productOptionRepository.save(updatedOption);
    }

    @Transactional
    public void subtractQuantity(Long optionId, int num) {
        ProductOption productOption = productOptionRepository.findById(optionId)
                .orElseThrow(() -> new NoSuchElementException("옵션을 찾을 수 없습니다."));

        productOption.subOptionQuantity(num);
    }

    public void deleteOption(Long optionId) {
        productOptionRepository.findById(optionId)
                .orElseThrow(() -> new NoSuchElementException("옵션을 찾을 수 없습니다."));

        productOptionRepository.deleteById(optionId);
    }
}
