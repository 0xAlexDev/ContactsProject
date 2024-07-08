import { LoginRequestDTO } from "./DTOs/LoginRequestDTO";

export class LoginRequest {
    username : String;
    password : String;

    constructor(username : String, password : String){
        this.username = username;
        this.password = password;
    }

    static factoryFromDTO(dto : LoginRequestDTO) : LoginRequest {
        return new LoginRequest(
            dto.username,
            dto.password,
        )
    }

}