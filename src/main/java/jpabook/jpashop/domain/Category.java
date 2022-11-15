package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter @Setter
@Entity
public class Category {

    @Id
    @GeneratedValue
    @Column(name="category_id")
    private Long id;

    private String name;


    @ManyToMany
    @JoinTable(name = "category_item", joinColumns = @JoinColumn(name="category_id")
                ,inverseJoinColumns = @JoinColumn(name = "item_id")) //중간 조인테이블 매핑 - 실무에서 사용하지 않는 방법
    private List<Item> items = new ArrayList<>();


    //상속관계 - 셀프로 양방향 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "parent_id")
    private Category parent; //계층구조의 부모 표현

    @OneToMany(mappedBy = "parent")
    private List<Category> child = new ArrayList<>(); //계층구조의 자식 표현


    //child를 집어넣으면 부모에도 들어가고 자식에도 들어가야 하므로 양방향
    //연관관계 편입 메소드
    public void addChildCategory(Category child) {
        this.child.add(this);
        child.setParent(this);
    }
}
