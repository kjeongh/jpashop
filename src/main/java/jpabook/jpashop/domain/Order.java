package jpabook.jpashop.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders") //
@Getter
@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name = "order_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id") //FK지정, 연관관계 주인 -> 연관관계 메소드 설정
    private Member member; //주문 회원

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL) //연관관계 주인 아님
    private List<OrderItem> orderItems = new ArrayList<>();

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "delivery_id") //FK지정, 연관관계 주인. 일대일 관계에서는 어느 쪽에든 FK를 둘 수 있으나, 보통 참조를 많이 하는 쪽에 두는게 좋다
    private Delivery delivery; //배송정보

    private LocalDateTime orderDate; //주문시간

    @Enumerated(EnumType.STRING)
    private OrderStatus status; //주문상태 [ORDER, CANCEL]

    //==연관관계 메서드==// 양방향에서는 이 메소드들이 있는게 좋다.
    public void setMember(Member member) { //쓰기 권한은 연관관계 주인인 order에 있다
        this.member = member;
        member.getOrders().add(this);
    }

    public void addOrderItem(OrderItem orderItem) { //왜 여기에 적히는거지...?FK가 있는 OrderItem쪽에 하는거 아닌가?
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
        delivery.setOrder(this); //oneToOne이니까 그냥 set하면 됨.
    }
}