import { Contact } from "./Contact";
import { AddressBookDTO } from "./DTOs/AddressBookDTO";

export class AddressBook {
    id : string;

    contacts: Contact[];

    constructor(id : Number, contacts : Contact[]){
        this.id = id.toString();
        this.contacts = contacts;
    }

    static factoryFromDTO(dto : AddressBookDTO) : AddressBook{
        return new AddressBook(
            dto.id,
            dto.contacts.map(contactDTO => Contact.factoryFromDTO(contactDTO))
        )
    }

}