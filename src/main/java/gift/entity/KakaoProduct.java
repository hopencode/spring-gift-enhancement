package gift.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "kakao_product")
public class KakaoProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false, unique = true)
    private String name;

    public KakaoProduct() {}
}
