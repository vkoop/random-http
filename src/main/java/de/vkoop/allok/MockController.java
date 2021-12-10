package de.vkoop.allok;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

@Controller
public class MockController {

    private static final Random random = new Random();

    @Value("${expectation}")
    private long expectation = 500;

    @Value("${derivation}")
    private double derivation = 100;

    @ResponseBody()
    @RequestMapping(value = "/**", produces = "text/html")
    public Mono<String> answerAll() {
        final long delay = getDelay();
        return Mono.delay(Duration.ofMillis(delay))
                .map(x -> "<html><head></head><body>ok</body></html>");
    }

    @ResponseBody
    @RequestMapping(value="/**", produces = "application/json")
    public Mono<String> answerJson(){
        final long delay = getDelay();

        return Mono.delay(Duration.ofMillis(delay))
                .map(x -> "{}");
    }

    private long getDelay() {
        var gaussian = random.nextGaussian();

        var delay = (expectation + derivation * gaussian);
        delay = Math.max(delay, 0);
        return (long) delay;
    }

}
