package hello.hellospring.service;

import hello.hellospring.domain.Member;
import hello.hellospring.repository.MemberRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /*
    회원 가입
    - 주어진 회원 정보를 가지고 회원을 가입시키는 메서드입니다.
    - 이미 존재하는 회원인 경우 예외를 던집니다.
    - 존재하지 않는 회원인 경우 회원을 저장하고, 회원의 고유 식별자를 반환합니다.
    */

    public Long join(Member member) {
        // 같은 이름이 있는 중복 회원을 검증합니다.
        validateDuplicateMember(member);

        // 회원을 저장하고, 고유 식별자를 반환합니다.
        memberRepository.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByName(member.getName()).ifPresent(m -> {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        });
    }

    /*
    전체 회원 조회
     */
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }
}