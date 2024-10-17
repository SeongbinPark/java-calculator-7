package calculator;

import camp.nextstep.edu.missionutils.test.NsTest;
import org.junit.jupiter.api.Test;

import static camp.nextstep.edu.missionutils.test.Assertions.assertSimpleTest;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ApplicationTest extends NsTest {
    @Test
    void 커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;\\n1");
            assertThat(output()).contains("결과 : 1");
        });
    }

    @Test
    void 예외_테스트() {
        assertSimpleTest(() ->
            assertThatThrownBy(() -> runException("-1,2,3"))
                .isInstanceOf(IllegalArgumentException.class)
        );
    }

    @Test
    void 여러개의_커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//;=\\n1;2=3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 빈_문자열이_입력된_경우() {
        assertSimpleTest(() -> {
            run("\n");
            assertThat(output()).contains("결과 : 0");
        });
    }

    @Test
    void 숫자가_아닌_값이_포함된_경우_예외_발생() {
        assertSimpleTest(() -> {
            assertThatThrownBy(() -> runException("1,a,3"))
                    .isInstanceOf(IllegalArgumentException.class);
        });
    }

    @Test
    void 커스텀_구분자_선언_후_개행_문자_없을_경우_예외_발생() {
        assertSimpleTest(() -> {
            assertThatThrownBy(() -> runException("//;1;2;3"))
                    .isInstanceOf(IllegalArgumentException.class)
                    .hasMessageContaining("커스텀 구분자 선언 후 '\\n'이 필요합니다");
        });
    }

    @Test
    void 여러문자로_이루어진_커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//[***]\\n1***2***3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Test
    void 여러개의_여러문자_커스텀_구분자_사용() {
        assertSimpleTest(() -> {
            run("//[**][%%]\\n1**,,,,[,::::,,,*,,,,,],,,,],,,,,,,,,,,,,,,,,,,,,,,,,,,,,:2%%3");
            assertThat(output()).contains("결과 : 6");
        });
    }

    @Override
    public void runMain() {
        Application.main(new String[]{});
    }
}
