package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor //final이 붙은 필수적인 필드들을 가지고 생성자 만들어줌
public class MemberService {

//  @Autowired 필드주입 - 지양해야 하는 방법. 생성자 주입 권장.
//  주입받은 객체가 변하지 않거나 반드시 주입이 필요할 경우에 강제할 때 생성자 주입
    private final MemberRepository memberRepository; //final로 하는 이유 -

    //Setter주입 - 이후 변경 가능. 스프링이 바로 주입하지 않고 setter를 통해 주입
    //단점 - 실행시점에 누가 함부로 바꿔버릴 수 있음. 사실 변경할 일이 거의 없으므로 권장X
    //@Autowired
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }


    //생성자 인젝션 - 무조건 한 번 호출. 주입받은 객체가 변하지 않거나 반드시 주입이 필요할 경우 강제할 때 사용
    //권장되는 방법! 실행시간에 변경되지 않음
//    @Autowired
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원가입
    public Long join(Member member) {
        validateDuplicateMember(member); //중복회원 검사
        memberRepository.save(member);
        return member.getId();
    }

    //동시에 같은 이름의 두 명이 가입하는 등의 상황 발생할 수 있음 -> db에서 unique제약을 걸거나 하는 2차방어 필요
    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository.findByName(member.getName());
        if(!findMembers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다");
        }
    }

    //회원 전체조회
    
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    //회원 단일조회
    @Transactional(readOnly = true)
    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional //트랜잭션이 있는 상태에서 조회하면 영속성 컨텍스트에서 가져온다
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id); //컨텍스트에서 처음에 찾으면 없으니까 db에 끌고 와서 컨텍스트에 올림
        member.setName(name); //영속상태 객체가 변경됨을 jpa가 감지 -> update쿼리 날림
    } //commit되면서 jpa가 flush
}

/*
@Transactional(readOnly = true) //이렇게하면 조회할때 성능을 최적화한다. 단순한 읽기전용임을 명시
조회에는 가급적 달아주는것이 좋다. 데이터 변경하는 메소드에는 달면 변경안됨!

아니면 서비스 클래스 전체에 @Transactional(readOnly = true)를 달고,
단순조회가 아닌 메소드에다가 @Transactional을 달아주는 방법도 있다
어느 방법을 선택하는지는 상황에 따라
기본적으로는 readOnly가 false임 


JPA의 모든 데이터변경이나 로직들은 가급적 트랜잭션 안에서 실행되어야 한다. 그래야 lazyLoading이런것도 됨
클래스 레벨에서 @Transactional을 붙이면 안의 메소드들은 다 트랜잭셔널에 걸려들어감!
transactional은 스프링이 제공하는것과 javax가 있는데, 현재 코드가 스프링을 쓰고있기에 스프링이 제공하는걸 쓰는게 좋다

 */