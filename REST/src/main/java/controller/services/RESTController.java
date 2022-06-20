package controller.services;

import model.GameConfiguration;
import model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import persistance.IRepositoryORMGameConfiguration;
import persistance.IRepositoryORMTop;
import persistance.IRepositoryORMUser;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/rest")
public class RESTController {

    private static final String greetingsMessage = "Hellow world :)";

    @Autowired
    private IRepositoryORMUser repoUser = null;

    @Autowired
    private IRepositoryORMGameConfiguration repoGame = null;

    @Autowired
    private IRepositoryORMTop repoTop = null;

    @RequestMapping(value="/hello")
    public String greet(){
        return greetingsMessage;
    }

//    @RequestMapping(value="/users", method = RequestMethod.GET)
//    public User[] getUsers(){
//        return repoUser.getAll().toArray(new User[0]);
//    }

    @RequestMapping(value="/user")
    public User getUser(@RequestParam("username")String username){
        return repoUser.findByUsername(username);
    }

    @RequestMapping(value="/add_configuration", method = RequestMethod.GET)
    public ResponseEntity<?> addConfig(@RequestParam("config")String list){
        List<Integer> config = new ArrayList<>();
        for(var rawNumber: list.split(",")){
            int number = Integer.parseInt(rawNumber);
            if(number == 3 ||number == 12 ||number == 15 ||number == 20 ||number == 30)
                config.add(number);
        }
        if(config.size() != 5){
            return new ResponseEntity<String>("The configuration is not valid", HttpStatus.BAD_REQUEST);
        }
        GameConfiguration game_config = new GameConfiguration();
        game_config.setValues(config);
        if (repoGame.add(game_config) != null) {
            return new ResponseEntity<String>("The configuration was saved!", HttpStatus.OK);
        }
        return new ResponseEntity<String>("The configuration is not valid", HttpStatus.BAD_REQUEST);

    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String flightError(RuntimeException exception){
        return exception.getMessage();
    }
}
