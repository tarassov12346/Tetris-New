package com.teris1.game.tetris.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {

	@RequestMapping(value="/")
	public ModelAndView test(HttpServletResponse response) throws IOException{
		return new ModelAndView("home");
	}
	
	@RequestMapping(value="/login")
	public ModelAndView loginView(){
		return new ModelAndView("login");
	}
	@RequestMapping(value="/signup")
	public ModelAndView signUpView(){
		return new ModelAndView("signup");
	}
	@RequestMapping(value="/gameScreen")
	public ModelAndView gameView(){
		return new ModelAndView("gameScreen");
	}
}
