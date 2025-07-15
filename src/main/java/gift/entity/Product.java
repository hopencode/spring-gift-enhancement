package gift.entity;

import gift.dto.ProductRequestDto;
import jakarta.persistence.*;

@Entity
@Table(name = "product")
@Access(AccessType.FIELD)
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 15, nullable = false)
    private String name;

    @Column(nullable = false)
    private Integer price;

    @Column(length = 512, nullable = false)
    private String imageUrl;

    public Product() {}

    public Product(Long id, String name, Integer price, String imageUrl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.imageUrl = imageUrl;
    }

    public Product(String name, Integer price, String imageUrl) {
        this(null, name, price, imageUrl);
    }

    public Long getId() { return id; }

    public String getName() { return name; }

    public Integer getPrice() { return price; }

    public String getImageUrl() { return imageUrl; }

    public void update(ProductRequestDto requestDto) {
        this.name = requestDto.getName();
        this.price = requestDto.getPrice();
        this.imageUrl = requestDto.getImageUrl();
    }
}