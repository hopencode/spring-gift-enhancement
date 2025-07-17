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

        var actual = productRepository.save(product);

        assertAll(
                () -> assertThat(actual.getId()).isNotNull(),
                () -> assertThat(actual.getName()).isEqualTo(product.getName())
        );
    }

    @Test
    void 저장된_상품_정상_검색_테스트() {
        Product product = new Product(null, "검색테스트", 1000, "https://테스트.png");

        var temp = productRepository.save(product);

        var actual = productRepository.findById(temp.getId()).get().getName();

        assertThat(actual).isEqualTo(product.getName());
    }
}