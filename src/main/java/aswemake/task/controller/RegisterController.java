package aswemake.task.controller;

import aswemake.task.dto.SignUpRequestDto;
import aswemake.task.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class RegisterController {

    private final UserService userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("signUpRequestDto", new SignUpRequestDto());
        return "login/registerform";
    }
    @PostMapping("/registerProcess")
    public String register(@Valid SignUpRequestDto dto, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "login/registerform";
        }
        try {
            userService.createUser(dto);
        } catch (IllegalStateException e) {
            e.getStackTrace();
            model.addAttribute("joinError", e.getMessage());
            return "login/registerform";
        }
        return "login/loginForm";
    }
}
