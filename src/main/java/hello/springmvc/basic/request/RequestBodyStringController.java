package hello.springmvc.basic.request;

import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream(); // HTTP 메시지 바디에 대한 데이터를 InputStream으로 직접 읽을 수 있다.
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8); // stream은 바이트라서 인코딩을 지정해줘야한다. (안 해주면 default 설정을 사용함.)

        log.info("messageBody={}", messageBody);
        response.getWriter().write("ok");
    }

    // InputStream과 Writer를 바로 받을 수 있다.
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        log.info("messageBody={}", messageBody);
        responseWriter.write("ok");
    }

    /**
     * HttpEntity 사용. -> Http Header와 message body 정보를 편리하게 조회할 수 있다.
     * @param httpEntity
     * @return
     * @throws IOException
     */
    @PostMapping("/request-body-string-v3-")
    public HttpEntity<String> requestBodyStringV3_(HttpEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new HttpEntity<>("ok");
    }

    /**
     * HttpEntity를 상속밭은 RequestEntity, ResponseEntity를 사용할 수 있다.
     * RequestEntity : HttpMethod, url 정보 추가. (요청에서 사용된다.)
     * ResponseEntity : Http 상태 코드 설정 가능 (응답에서 사용된다.)
     * @param httpEntity
     * @return
     * @throws IOException
     */
    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(RequestEntity<String> httpEntity) {
        String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    /**
     * @RequestBody를 사용하여 바디 정보를 편리하게 조회 가능.
     * 헤더 정보가 필요하면 @RequestHeader를 사용하면 된다.
     * @param messageBody
     * @return
     */
    @PostMapping("/request-body-string-v4")
    public HttpEntity<String> requestBodyStringV3(@RequestBody String messageBody) {
        log.info("messageBody={}", messageBody);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
}
