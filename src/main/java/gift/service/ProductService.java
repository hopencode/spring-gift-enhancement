package gift.service;

import gift.dto.PageResponseDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements ProductServiceInterface {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductResponseDto addProduct(ProductRequestDto requestDto) {
        Product product = new Product(requestDto.getName(), requestDto.getPrice(), requestDto.getImageUrl());
        Product savedProduct = productRepository.save(product);
        return new ProductResponseDto(savedProduct.getId(), savedProduct.getName(), savedProduct.getPrice(), savedProduct.getImageUrl());
    }

    @Override
    public PageResponseDto getPageProducts(int page, int pageSize) {
        return null;
    }

    @Override
    public List<ProductResponseDto> findAllProducts() {
        return List.of();
    }

    @Override
    public Optional<ProductResponseDto> findProductById(Long id) {
        return Optional.empty();
    }

    @Override
    public Optional<ProductResponseDto> updateProduct(Long id, ProductRequestDto requestDto) {
        return Optional.empty();
    }

    @Override
    public void deleteProduct(Long id) {

    }

    @Override
    public int countAllProducts() {
        return 0;
    }
}
