import { Injectable } from '@angular/core';
import { User } from '../../models/User';
import { CrudService } from '../crud-service/crud.service';
import { map, Observable } from 'rxjs';
import { AddressBook } from '../../models/AddressBook';
import { UserDTO } from '../../models/DTOs/UserDTO';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class UserService extends CrudService<User,UserDTO>  {

  constructor(private http : HttpClient) {
    super("users");
  }

  override get(id: string): Observable<Boolean> {
    return this.http.get<Boolean>(this.baseApi + this.entityName + '/' + id,{headers: this.headers});
  }
  override create(entity: User): Observable<User> {
    let userDTO : UserDTO = this.entityToDTO(entity);
    return this.http.post<UserDTO>(this.baseApi + this.entityName, userDTO, {headers:this.headers}).pipe(map( data => {
      return User.factoryFromDTO(data)
    }))
  }
  override update(entity: User): Observable<Boolean> {
    let userDTO : UserDTO = this.entityToDTO(entity);
    return this.http.put<Boolean>(this.baseApi + this.entityName + '/' + entity.id, userDTO, {headers:this.headers});
  }
  override delete(id: string): Observable<Boolean> {
    return this.http.delete<Boolean>(this.baseApi + this.entityName + '/' + id,{headers: this.headers});
  }
  
  override entityToDTO(entity: User): UserDTO {
    return {id: Number.parseInt(entity.id), username: entity.username, role: entity.role, 
      addressBook: {
        id: Number.parseInt(entity.addressBook.id),
        contacts: entity.addressBook.contacts.map(contact => {
          return {id: Number.parseInt(contact.id ?? ""), name: contact.name, surname: contact.surname, address: contact.address, phoneNumber: contact.phoneNumber, age: contact.age, addressBookId: contact.addressBookId};
        })
      }
    };
  }

}
