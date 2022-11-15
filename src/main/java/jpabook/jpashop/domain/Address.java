package jpabook.jpashop.domain;

import lombok.Getter;

import javax.persistence.Embeddable;

@Embeddable
@Getter //setter는 함부로 달지 말자
public class Address {
    private String city;
    private String street;
    private String zipcode;

    protected Address() { //기본생성자로 생성하면 안됨

    }

    public Address (String city, String street, String zipcode) {
        this.city = city;
        this.street = street;
        this.zipcode = zipcode;
    }
}
