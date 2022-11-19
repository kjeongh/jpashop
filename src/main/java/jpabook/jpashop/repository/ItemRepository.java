package jpabook.jpashop.repository;

import jpabook.jpashop.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class ItemRepository {

    private final EntityManager em;

    public void save(Item item) {
        if(item.getId() == null) {
            em.persist(item); //jpa에 저장하기 전까지 id값이 없다. 여기서 id값이 있으면 이미 db에 등록된걸 가져온것
        } else {
            em.merge(item);
        }
    }

    public Item findOne(Long id) {
        return em.find(Item.class, id);
    }

    public List<Item> findAll() { //여러 개 찾는건 jpql로 작성해야 한다
        return em.createQuery("select i from Item i", Item.class)
                .getResultList();

    }
}
