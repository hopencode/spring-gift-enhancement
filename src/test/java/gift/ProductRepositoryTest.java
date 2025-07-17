package gift;


import gift.entity.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void 상품_저장_정상_테스트() {
        Product product = new Product(null, "저장테스트", 1000, "https://테스트.png");

        var result = productRepository.save(product);

        assertAll(
                () -> assertThat(result.getId()).isNotNull(),
                () -> assertThat(result.getName()).isEqualTo(product.getName())
        );
    }

    @Test
    void 저장된_상품_정상_id_검색_테스트() {
        Product product = new Product(null, "검색테스트", 1000, "https://테스트.png");

        var saved = productRepository.save(product);

        var found = productRepository.findById(saved.getId()).get().getName();

        assertThat(found).isNotNull();
        assertThat(found).isEqualTo(product.getName());
    }
}