package gift;

import gift.entity.Product;
import gift.entity.WishList;
import gift.repository.WishListRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
class WishListRepositoryTest {

    @Autowired
    private WishListRepository wishListRepository;

    @Test
    void 이메일로_위시리스트_상품_모두_조회() {
        String email = "abc@pusan.ac.kr";

        List<WishList> wishLists = wishListRepository.findWishListByEmail(email);

        assertThat(wishLists).hasSize(0);
        // assertThat(wishLists).extracting(WishList::getEmail).containsExactlyInAnyOrder("초코송이");
    }

    @Test
    void 위시리스트에_상품_추가() {
        String email = "def@pusan.ac.kr";
        Long productId = 2L;

        WishList wishList = new WishList(email, productId);
        wishListRepository.save(wishList);

        List<WishList> wishLists = wishListRepository.findWishListByEmail(email);
        assertThat(wishLists).hasSize(1);
        assertThat(wishLists.get(0).getEmail()).isEqualTo("def@pusan.ac.kr");
    }

}