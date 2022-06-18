package controller.services;

import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import persistance.IRepositoryORMUser;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class RESTController {

    private static final String greetingsMessage = "Hellow world :)";

    @Autowired
    private IRepositoryORMUser repoUser = null;

    @RequestMapping(value="/hello")
    public String greet(){
        return greetingsMessage;
    }

//    @RequestMapping(value="/users", method = RequestMethod.GET)
//    public User[] getUsers(){
//        return repoUser.getAll().toArray(new User[0]);
//    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String flightError(RuntimeException exception){
        return exception.getMessage();
    }
}
