package com.smart.controller;

import com.smart.dao.UserRepository;
import com.smart.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;

@Controller
public class HomeController {

    @Autowired
    private UserRepository userRepository ;

   @RequestMapping("/")
    public String home(Model m)
    {
        m.addAttribute("title", "Home-Smart Contact Manager");
        return "home";

    }

    @RequestMapping("/about")
    public String about(Model model)
    {
        model.addAttribute("title", "about -Smart Contact Manager");
        return "about";

    }
    @RequestMapping("/signup")
    public String signup(Model model)
    {
        model.addAttribute("title", "Register -Smart Contact Manager");
        model.addAttribute("user",new User());
        return "signup";

    }
    // this handler for registering user
    @RequestMapping(value = "/do_register" , method = RequestMethod.POST)
    public String registerUser(@Valid @ModelAttribute("user") User user ,BindingResult result,  @RequestParam(value = "agreement", defaultValue = "false") boolean agreement , Model model )
    {


        if(result.hasErrors())
        {
            System.out.println("ERROR"+ result.toString());
            model.addAttribute("user", user);
            return "signup";
        }
        user.setRole("ROLE_user");
        user.setEnabled(true);

        if(!agreement)
        {
            System.out.println("you have not agreed the terms and conditions");
        }

        System.out.println("Agreement" +agreement);
        System.out.println("USER"+user);
        User res= this.userRepository.save(user);
        model.addAttribute("user", res);
        return "signup";
    }


}
