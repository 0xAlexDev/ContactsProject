import { RegistrationRequestDTO } from "./DTOs/RegistrationRequestDTO";

export class RegistrationRequest  {
    username : String;
    password : String;

    constructor(username : String, password : String){
        this.username = username;
        this.password = password;
    }

    static factoryFromDTO(dto : RegistrationRequestDTO) : RegistrationRequest {
        return new RegistrationRequest(
            dto.username,
            dto.password,
        )
    }

}