package gift;

import gift.entity.Member;
import gift.repository.MemberRepository;
import org.h2.engine.Role;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class MemberRepositoryJpaTest {
    @Autowired
    private MemberRepository memberRepository;

    @Test
    void 회원_계정_정상_저장() {
        Member member = new Member(null, "test@email.com", "12345678");

        var result = memberRepository.save(member);

        assertAll(
                () -> assertThat(result.getId()).isNotNull(),
                () -> assertThat(result.getEmail()).isEqualTo(member.getEmail())
        );
    }

    @Test
    void 회원_계정_정상_검색() {
        Member member = new Member(null, "test@email.com", "12345678");
        memberRepository.save(member);

        Optional<Member> found = memberRepository.findByEmail("test@email.com");

        assertThat(found).isPresent();
        assertThat(found.get().getEmail()).isEqualTo("test@email.com");
    }

    @Test
    public void 없는_회원_계정_검색() {
        Optional<Member> found = memberRepository.findByEmail("not_exist@email.com");

        assertThat(found).isNotPresent();
    }
}
