package hello.springmvc.basic;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

//@Slf4j
@RestController
public class LogTestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @GetMapping("/log-test")
    public String logTest() {
        String name = "Spring";

        System.out.println("name = " + name);

        // logging level
        log.trace("trace log={}", name);
        log.debug("debug log={}", name); // 디버그 시 보는 로그 (개발 서버)
        log.info("info log={}", name); // 운영 시스템에서도 봐야되는 비즈니스 정보
        log.warn("warn log={}", name); // 경고 (위험함)
        log.error("error log={}", name); // 에러 (빨리 확인해야 함)
        return "ok";
    }
}
