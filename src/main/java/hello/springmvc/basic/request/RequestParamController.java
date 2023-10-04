package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
public class RequestParamController {

    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    @ResponseBody // RestController처럼 view를 찾지 않고 리턴 값을 HTTP message body에 바로 넣어준다.
    @RequestMapping("/request-param-v2")
    public String requestParamV2(@RequestParam("username") String memberName,
                                 @RequestParam("age") int memberAge) {
        log.info("username={}, age={}", memberName, memberAge);
        return "ok";
    }

    /**
     * @RequestParam 사용
     * HTTP 파라미터 이름이 변수 이름과 같으면 @RequestParam(name="xx") 생략 가능 */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(@RequestParam String username, @RequestParam int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * @RequestParam도 생략 가능 (너무 과하다고 하심)
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 파라미터 필수 여부
     * null과 ""는 다르다. ?username= 이렇게 빈값으로 넘기면 null이 아니기 때문에 성공한다.
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-required")
    public String requestParamRequired(@RequestParam(required = true) String username, // 기본이 필수 파라미터
                                       @RequestParam(required = false) int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    /**
     * 파라미터 기본 값 적용
     * 빈 문자열이 들어와도 기본 값이 적용된다.
     * @param username
     * @param age
     * @return
     */
    @ResponseBody
    @RequestMapping("/request-param-default")
    public String requestParamDefault(@RequestParam(required = true, defaultValue = "guest") String username, // 기본이 필수 파라미터
                                       @RequestParam(required = false, defaultValue = "-1") int age) {
        log.info("username={}, age={}", username, age);
        return "ok";
    }

    @ResponseBody
    @RequestMapping("/request-param-map")
    public String requestParamMap(@RequestParam Map<String, Object> paramMap) { // 파라미터를 Map, MultiValueMap으로 조회할 수 있다.
        log.info("username={}, age={}", paramMap.get("username"), paramMap.get("age"));
        return "ok";
    }

    // 1번 (기존)
    @ResponseBody
    @RequestMapping("/model-attribute-v1-")
    public String modelAttributeV1_(@RequestParam String username, @RequestParam int age) {
//        HelloData helloData = new HelloData();
//        helloData.setUsername(username);
//        helloData.setAge(age);
//
//        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
//        log.info("helloData={}", helloData);
        return "ok";
    }

    /**
     * 2번 (@ModelAttribute 사용) -> 1번과 같은 과정을 자동으로 실행해준다.
     * HelloData 객체를 생성하고,
     * 요청 파라미터 이름으로 HelloData 객체의 프로퍼티를 찾고,
     * 해당 프로퍼티의 세터를 호출하여 파라미터 값을 입력(바인딩)한다.
     * -> 세터가 없으면? 값이 바인딩되지 않는다. 출력 결과 : username=null, age=0
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }

    /**
     * @ModelAttribute는 @RequestParam과 마찬가지로 생략할 수 있다.
     * 스프링은 단순 타입(String, int 등)이면 @RequestParam으로 구분하고,
     * 나머지 객체는 @ModelAttribute로 구분한다.
     * @param helloData
     * @return
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v2")
    public String modelAttributeV2(HelloData helloData) {
        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());
        return "ok";
    }
}
