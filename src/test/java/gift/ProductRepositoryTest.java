package gift;


import gift.entity.Product;
import gift.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
class ProductRepositoryTest {
    @Autowired
    private ProductRepository productRepository;

    @Test
    void 저장() {
        Product product = new Product("이름", 3000, "None");

        var actual = productRepository.save(product);

        assertThat(actual.getId()).isNotNull();
    }
}