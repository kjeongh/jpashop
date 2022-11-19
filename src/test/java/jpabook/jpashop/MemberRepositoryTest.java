//package jpabook.jpashop;
//
//import jpabook.jpashop.repository.MemberRepository;
//import org.assertj.core.api.Assertions;
//import org.junit.jupiter.api.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.transaction.annotation.Transactional;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//@Transactional
//public class MemberRepositoryTest {
//
//    @Autowired
//    MemberRepository memberRepository;
//
//    @Test
//    //@Rollback(value = false)
//    public void testMember() throws Exception {
//        Member member = new Member();
//        member.setUsername("memberA");
//
//        Long saveId = memberRepository.save(member);
//        Member findMember = memberRepository.find(saveId);
//
//        Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
//        Assertions.assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
//
//        //같은 트랜잭션 안에서 저장하고 조회하므로 같은 영속성 컨텍스트.
//        //같은 영속성 컨텍스트에서 id값이 같으므로 같은 엔티티로 식별한다.
//        Assertions.assertThat(findMember).isEqualTo(member); //TRUE
//
//    }
//}