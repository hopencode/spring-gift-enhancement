package gift.service;

import gift.dto.ProductResponseDto;
import gift.dto.WishListProductRequestDto;
import gift.entity.Product;
import gift.entity.WishList;
import gift.exception.MemberExceptions;
import gift.exception.ProductExceptions;
import gift.repository.MemberRepository;
import gift.repository.ProductRepository;
import gift.repository.WishListRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListService {
    private final WishListRepository wishListRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public WishListService(WishListRepository wishListRepository,
                           MemberRepository memberRepository,
                           ProductRepository productRepository) {
        this.wishListRepository = wishListRepository;
        this.memberRepository = memberRepository;
        this.productRepository = productRepository;
    }

    public List<ProductResponseDto> findAllProductsFromWishList(String email) {
        validateMemberExists(email);
        List<WishList> wishLists = wishListRepository.findWishListByEmail(email);
        List<ProductResponseDto> productResponseDtoList = new ArrayList<>();
        for (WishList wishList : wishLists) {
            Product product = productRepository.findById(wishList.getProductId())
                    .orElseThrow(() -> new ProductExceptions.ProductNotFoundException(wishList.getProductId()));

            productResponseDtoList.add(new ProductResponseDto(
                    product.getId(),
                    product.getName(),
                    product.getPrice(),
                    product.getImageUrl()));
        }
        return productResponseDtoList;
    }

    public List<ProductResponseDto> addProductToWishListByEmail(String email, WishListProductRequestDto requestDto) {
        validateMemberExists(email);
        Long productId = requestDto.getproductId();
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ProductExceptions.ProductNotFoundException(productId));
        WishList wish = new WishList(email, productId);
        wishListRepository.save(wish);

        return findAllProductsFromWishList(email);
    }

    public void deleteProductFromWishList(String email, Long productId) {
        validateMemberExists(email);
        WishList wishList = wishListRepository.findByEmailAndProductId(email, productId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "찜 목록에 해당 상품이 없습니다."));

        wishListRepository.deleteById(wishList.getId());
    }

    private void validateMemberExists(String email) {
        if (memberRepository.findByEmail(email).isEmpty()) {
            throw new MemberExceptions.MemberNotFoundException(email);
        }
    }

}
