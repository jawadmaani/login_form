package org.example.loginform.Controller;

import org.example.loginform.model.UserModel;
import org.example.loginform.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UsersController {

    private final UsersService usersService;


    public UsersController(UsersService usersService) {
        this.usersService = usersService;
    }


    @GetMapping("/register")
    public String getregisterpage(Model model) {
        model.addAttribute("registerRequest", new UserModel());
        return "register_page";
    }
    @GetMapping("/login")
    public String getloginpage(Model model) {
        model.addAttribute("loginRequest", new UserModel());

        return "login_page";
    }
    @PostMapping("/register")
    public String register(@ModelAttribute UserModel userModel) {
        System.out.println("regester request "+userModel);
        UserModel registeredUser=usersService.registerUser(userModel.getLogin(),userModel.getPassword(),userModel.getEmail());
        return  registeredUser==null ?"error_page" : "redirect:/login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute UserModel userModel,Model model) {
        System.out.println("login request "+userModel);
        UserModel authenticated=usersService.authenticated(userModel.getLogin(),userModel.getPassword());
        if(authenticated !=null){
            model.addAttribute("userLogin",authenticated.getLogin());
            return "personal_page";

        }else {
            return "error_page";
        }
    }

}
