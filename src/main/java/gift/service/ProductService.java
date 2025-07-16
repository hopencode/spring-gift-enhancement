package gift.service;

import gift.dto.PageResponseDto;
import gift.dto.ProductRequestDto;
import gift.dto.ProductResponseDto;
import gift.entity.Product;
import gift.exception.ProductExceptions;
import gift.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public ProductResponseDto addProduct(ProductRequestDto requestDto) {

        String name = requestDto.getName();
        // validateUsingKakaoName(name); 추후 수정

        Product product = new Product(
                requestDto.getName(),
                requestDto.getPrice(),
                requestDto.getImageUrl()
        );

        Product addedProduct = productRepository.save(product);

        return new ProductResponseDto(addedProduct.getId(), addedProduct.getName(), addedProduct.getPrice(), addedProduct.getImageUrl());
    }

    public List<ProductResponseDto> findAllProducts() {
        List<Product> productList = productRepository.findAll();
        List<ProductResponseDto> products = new ArrayList<>();
        for (Product product : productList) {
            products.add(new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl()
            ));
        }
        return products;
    }

    public Optional<ProductResponseDto> findProductById(Long id) {
        return productRepository.findById(id)
                .map(product -> new ProductResponseDto(
                        product.getId(),
                        product.getName(),
                        product.getPrice(),
                        product.getImageUrl()
                ));
    }

    public Optional<ProductResponseDto> updateProduct(Long id, ProductRequestDto requestDto) {

        String name = requestDto.getName();
        // validateUsingKakaoName(name);

        checkProductExist(id);

        Product product = new Product(
                id,
                requestDto.getName(),
                requestDto.getPrice(),
                requestDto.getImageUrl()
        );

        Product updatedProduct = productRepository.save(product);
        return Optional.of(new ProductResponseDto(updatedProduct.getId(), updatedProduct.getName(),
                                        updatedProduct.getPrice(), updatedProduct.getImageUrl()));
    }

    public void deleteProduct(Long id) {
        checkProductExist(id);

        productRepository.deleteById(id);
    }

    public int countAllProducts() {
        return (int) productRepository.count();
    }



    private void checkProductExist(Long id) {
        if (!productRepository.existsById(id)) {
            throw new ProductExceptions.ProductNotFoundException(id);
        }
    }
}
