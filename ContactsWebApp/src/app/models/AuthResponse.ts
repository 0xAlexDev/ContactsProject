import { AuthResponseDTO } from "./DTOs/AuthResponseDTO";
import { User } from "./User";

export class AuthResponse {

    token : string;
    expiresIn : Number;
    user : User;

    constructor(token : string, expiresIn : Number, user : User){
        this.token = token;
        this.expiresIn = expiresIn;
        this.user = user;
    }

    static factoryFromDTO(dto : AuthResponseDTO) : AuthResponse {
        return new AuthResponse(
            dto.token,
            dto.expiresIn,
            User.factoryFromDTO(dto.user)
        )
    }
    
}