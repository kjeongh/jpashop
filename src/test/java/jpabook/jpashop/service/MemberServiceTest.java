package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;
@RunWith(SpringRunner.class) //junit실행할때 spring이랑 같이 실행할래!
@SpringBootTest //springboot띄운상태로 test할때 필요. 스프링 컨테이너 안에서 테스트 돌리기. autowired도 이거 없으면 다 실패
@Transactional //트랜잭션 내에서 실행
public class MemberServiceTest {

        @Autowired MemberService memberService;
        @Autowired MemberRepository memberRepository;
        @Autowired EntityManager em;

    @Test
    //@Transactional(readOnly = true)
    //@Rollback(false)
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("kim");

        //when
        Long savedId = memberService.join(member);
        //em.flush(); //db에 반영. 쿼리가 나감

        //then
        Assert.assertEquals(member, memberRepository.findOne(savedId));
        //@Transactional 붙어있잖음. 같은 영속성 컨텍스트 안에서 id값이 같은건 같은 엔티티임을 이용해서
        //잘 저장되었는지 확인
    } //테스트가 끝날때 트랜잭션이 롤백을 함

    @Test(expected = IllegalStateException.class)
    public void 중복_회원_예외() throws Exception { //validation메소드 잘 동작하나 검사
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        memberService.join(member2);

//        expected를 통해 try-catch생략
//        try {
//            memberService.join(member2); //여기서 예외 발생
//        } catch (IllegalStateException e) {
//            return;
//        }

        //then
        fail("예외가 발생해야 한다");
    }

}