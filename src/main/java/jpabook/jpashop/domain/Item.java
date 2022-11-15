package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) //상속관계 매핑에서, 부모 클래스에 전략을 지정해줘야 한다. 한 테이블에 다 때려박기
@DiscriminatorColumn(name="dtype")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name="item_id")
    private Long id;

    private String name;

    private int price;

    private int stockQuantity;

    //item에서는 orderItem을 정의하지 않음 -> 단방향
    
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<>();
}
