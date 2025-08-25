package uz.pdp.spring_boot_demo.config;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.spring_boot_demo.dto.TokenRequest;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtProvider provider;

    public AuthController(JwtProvider provider) {
        this.provider = provider;
    }

    @PostMapping("/token")
    public String login(@RequestBody TokenRequest tokenRequest) {
        return provider.genereteToken(tokenRequest.username());
    }
}
