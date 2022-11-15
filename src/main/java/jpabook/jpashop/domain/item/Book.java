package jpabook.jpashop.domain.item;

import jpabook.jpashop.domain.Item;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@DiscriminatorValue("B") //싱글테이블이므로 구분하기 위한 값
public class Book extends Item {

    private String author;
    private String isbn;


}
