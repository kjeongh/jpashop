package jpabook.jpashop.api;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.service.MemberService;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController //@Responsebody + @Controller
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;
    
//    이렇게 엔티티를 가지고 바로 하면 안 된다. 실제 요청에 걸맞는 DTO를 만들어서 사용해라! 엔티티를 노출하지 말 것
//    @PostMapping("/api/v1/members")
//    public CreateMemberResponse saveMemberV1(@RequestBody @Valid Member member) { //@RequestBody: JSON으로 온 body를 Member로 매핑해서 넣어줌
//        Long id = memberService.join(member);
//        return new CreateMemberResponse(id);
//    }

    //별도의 DTO를 만들어서 사용하는 경우
    @PostMapping("/api/v2/members")
    public CreateMemberResponse saveMemberV2(@RequestBody @Valid CreateMemberRequest request) {
        Member member = new Member();
        member.setName(request.getName());

        Long id = memberService.join(member);
        return new CreateMemberResponse(id);
    }

    //회원 수정
    @PutMapping("/api/v2/members/{id}")
    public UpdateMemberResponse updateMemberV2(@PathVariable("id") Long id, @RequestBody @Valid UpdateMemberRequest request ) {
        memberService.update(id, request.getName());
        Member findMember = memberService.findOne(id);
        return new UpdateMemberResponse(findMember.getId(), findMember.getName());
    }

    @Data
    static class UpdateMemberRequest {
        private String name;
    }

    @Data
    @AllArgsConstructor
    static class UpdateMemberResponse {
        private Long id;
        private String name;
    }

    @Data
    static class CreateMemberRequest {
        private String name;
    }

    @Data //
    static class CreateMemberResponse {
        private Long id;
        CreateMemberResponse(Long id) {
            this.id = id;
        }
    }
}
