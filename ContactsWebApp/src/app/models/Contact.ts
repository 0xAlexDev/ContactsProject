import { ContactDTO } from "./DTOs/ContactDTO";

export class Contact {

    id : string | null;
    name : string;
    surname : string;
    address : string;
    phoneNumber : string;
    age : string;
    addressBookId : Number;

    constructor(id : Number | null, name : string, surname : string, address : string, phoneNumber : string, age : string, addressBookId : Number){
        this.id = id?.toString() ?? null;
        this.name = name;
        this.surname = surname;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.addressBookId = addressBookId;
    }    

    static factoryFromDTO(dto : ContactDTO) : Contact{
        return new Contact(
            dto.id,
            dto.name,
            dto.surname,
            dto.address,
            dto.phoneNumber,
            dto.age,
            dto.addressBookId
        )
    }

}