import { AddressBook } from "./AddressBook";
import { UserDTO } from "./DTOs/UserDTO";

export class User {

    id : string;

    username : string;

    role : string;

    addressBook : AddressBook;
    
   
    constructor(id : Number, username : string, role : string, addressBook : AddressBook){
        this.id = id.toString();
        this.username = username;
        this.role = role;
        this.addressBook = addressBook;
    }

    static factoryFromDTO(dto : UserDTO) : User{
        return new User(
            dto.id,
            dto.username,
            dto.role,
            AddressBook.factoryFromDTO(dto.addressBook)
        )
    }

}