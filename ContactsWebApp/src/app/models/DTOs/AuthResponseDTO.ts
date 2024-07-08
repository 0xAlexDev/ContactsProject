import { UserDTO } from "./UserDTO";

export interface AuthResponseDTO  {

    token : string;
    expiresIn : Number;
    user : UserDTO;
    
}