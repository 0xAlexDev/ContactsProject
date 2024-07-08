import { Injectable } from '@angular/core';
import { ContactDTO } from '../../models/DTOs/ContactDTO';
import { Contact } from '../../models/Contact';
import { Observable, map } from 'rxjs';
import { UserDTO } from '../../models/DTOs/UserDTO';
import { User } from '../../models/User';
import { CrudService } from '../crud-service/crud.service';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ContactService extends CrudService<Contact,ContactDTO> {

  constructor(private http : HttpClient) {
    super("contacts");
  }

  override get(id: string): Observable<Boolean> {
    return this.http.get<Boolean>(this.baseApi + this.entityName + '/' + id,{headers: this.headers});
  }
  override create(entity: Contact): Observable<Contact> {
    let contactDTO : ContactDTO = this.entityToDTO(entity);
    return this.http.post<ContactDTO>(this.baseApi + this.entityName, contactDTO, {headers:this.headers}).pipe(map( data => {
      return Contact.factoryFromDTO(data)
    }))
  }
  override update(entity: Contact): Observable<Boolean> {
    let contactDTO : ContactDTO = this.entityToDTO(entity);
    return this.http.put<Boolean>(this.baseApi + this.entityName + '/' + entity.id, contactDTO, {headers:this.headers});
  }
  override delete(id: string): Observable<Boolean> {
    return this.http.delete<Boolean>(this.baseApi + this.entityName + '/' + id,{headers: this.headers});
  }

  deleteAll(contacts : Contact[]): Observable<Boolean> {
    let contactDTOs : ContactDTO[] = contacts.map(contact => this.entityToDTO(contact));
    return this.http.post<Boolean>(this.baseApi + this.entityName + '/bulk/delete',contactDTOs,{headers: this.headers});
  }
  
  override entityToDTO(entity: Contact): ContactDTO {
    return {id: parseInt(entity.id ?? ""), name: entity.name, surname: entity.surname, address: entity.address, phoneNumber: entity.phoneNumber,  age: entity.age, addressBookId: entity.addressBookId};
  }
}
