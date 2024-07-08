import { AddressBookDTO } from "./AddressBookDTO";


export interface UserDTO  {

    id : Number;
    username : string;
    role : string;
    addressBook : AddressBookDTO;
       
}