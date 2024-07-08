import { Observable } from 'rxjs';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { AppConfiguration } from '../../config/AppConfiguration';

export abstract class CrudService <Entity,EntityDTO> {

  baseApi : string = AppConfiguration.baseApiPath;

  headers = new HttpHeaders().set('Content-Type', 'application/json; charset=utf-8');

  entityName : string;

  abstract get(id : string) : Observable<Boolean>;

  abstract create(entity : Entity) : Observable<Entity>;

  abstract update(entity : Entity) : Observable<Boolean>;

  abstract delete(id : string) : Observable<Boolean>;

  abstract entityToDTO(entity : Entity) : EntityDTO;


  /*
  get(entityName : string, id : string) : Observable<Entity> {
    return this.http.get<EntityDTO>(this.baseApi + entityName + '/' + id,{headers: this.headers});
  }

  post<EntityDTO>(entityName : string, jsonEntity : string) : Observable<any> {
    return this.http.post<EntityDTO>(this.baseApi + entityName, jsonEntity, {headers:this.headers})
  }

  put<EntityDTO>(entityName : string, id : string, jsonEntity : string) : Observable<any> {
    return this.http.put<Boolean>(this.baseApi + entityName + '/' + id, jsonEntity, {headers:this.headers})
  }

  delete<EntityDTO>(entityName : string, id : string) : Observable<any> {
    return this.http.delete<Boolean>(this.baseApi + entityName + '/' + id, {headers:this.headers})
  }


  constructor(private http : HttpClient) { }
  */

  constructor(entityName : string) {
    this.entityName = entityName;
  }
}
