package myusick.api.services;

import com.google.gson.Gson;
import myusick.model.dto.AuthTokenDTO;
import myusick.model.dto.LoginDTO;
import myusick.model.services.MyusickService;
import myusick.util.security.AuthTokenGenerator;

public class LoginService {

    /**
     * Función de autenticación de usuarios
     * REST endpoint
     * Method: POST /auth
     * Content-Type: application/x-www-form-urlencoded
     * Response-Type: application/json
     *
     * Autentifica a un usuario si existe
     * @param user usuario que trata de loggearse
     * @param password password del usuario
     * @return token de autenticación
     */
    public static String authenticate(String user, String password){
        Gson gson = new Gson();
        LoginDTO userDatabase, userForm;
        userForm = new LoginDTO(user,password);
        userDatabase = new MyusickService().getLoginData(user,password);
        AuthTokenDTO authTokenDTO = new AuthTokenDTO();
        authTokenDTO.setToken("no"); //default not allowed
        if(userDatabase != null && userForm.getPassword().equals(userDatabase.getPassword())){
            //allow auth, send token
            authTokenDTO = AuthTokenGenerator.generateToken(userDatabase);
        }
        return gson.toJson(authTokenDTO);
    }
}
