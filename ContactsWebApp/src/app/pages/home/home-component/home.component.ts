import { Component, OnInit } from '@angular/core';
import { LottieComponent } from 'ngx-lottie';
import { Button } from 'primeng/button';
import { TableModule, TableRowSelectEvent } from 'primeng/table';
import { FormBuilder, FormControl, FormGroup, ReactiveFormsModule } from '@angular/forms'
import { Card, CardModule } from 'primeng/card';
import { Panel, PanelModule } from 'primeng/panel'
import { DialogModule } from 'primeng/dialog'
import { User } from '../../../models/User';
import { AuthService } from '../../../services/auth-service/auth.service';
import { Contact } from '../../../models/Contact';
import { InputTextModule } from 'primeng/inputtext';
import { ContactService } from '../../../services/contact-service/contact.service';
import { ConfirmationService, MessageService } from 'primeng/api';
import { ConfirmDialogModule } from 'primeng/confirmdialog';
import { ValidationException } from '../../../config/CustomException/ValidationException';
import { ToastModule } from 'primeng/toast';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [
    Button,
    LottieComponent,
    TableModule,
    CardModule,
    PanelModule,
    DialogModule,
    ReactiveFormsModule,
    InputTextModule,
    ToastModule,
    ConfirmDialogModule
  ],
  providers: [MessageService,ConfirmationService],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss'
})
export class HomeComponent implements OnInit{

  formToUpdate : FormGroup;

  formToCreate : FormGroup;

  user : User;

  selectedContacts : Contact[] = [];

  showDialogToUpdate : boolean = false;
  showDialogToCreate : boolean = false;

  updateLoading : boolean = false;
  createLoading : boolean = false;


  openDialogToRemoveSelectedContacts(){
    this.confirmationService.confirm({
      message: 'Do you want to delete all selected records?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass:"p-button-danger p-button-text",
      rejectButtonStyleClass:"p-button-text p-button-text",
      acceptIcon:"none",
      rejectIcon:"none",

      accept: () => {
        this.removeAllSelectedContacts();
      },
      reject: () => {
          this.showErrorToast('You have rejected');
      }
    });
  }

  removeAllSelectedContacts(){
    this.contactService.deleteAll(this.selectedContacts).subscribe({
      next: (data) => {
        for(let contact of this.selectedContacts){
          let itemIndex = this.user.addressBook.contacts.findIndex(item => item.id == contact.id);
          this.user.addressBook.contacts.splice(itemIndex,1)
        }
        this.selectedContacts = []
        this.showSuccessToast('Records deleted');
      },
      error: (err) => this.showErrorToast(err)
    })
  }



  ngOnInit() {
    this.user = this.authService.authUser!;

    this.user.addressBook.contacts.forEach(e => console.log(e))
    
    this.formToUpdate = new FormGroup({
      id: new FormControl(''),
      name: new FormControl(''),
      surname: new FormControl(''),
      address: new FormControl(''),
      phoneNumber: new FormControl(''),
      age: new FormControl(''),
    });

    this.formToCreate = new FormGroup({
      name: new FormControl(''),
      surname: new FormControl(''),
      address: new FormControl(''),
      phoneNumber: new FormControl(''),
      age: new FormControl(''),
    });
  }

  logout(){
    this.authService.logout();
  }

  openPanelToUpdate(contact : Contact){
    this.formToUpdate.controls["id"].setValue(contact.id);
    this.formToUpdate.controls["name"].setValue(contact.name);
    this.formToUpdate.controls["surname"].setValue(contact.surname);
    this.formToUpdate.controls["address"].setValue(contact.address);
    this.formToUpdate.controls["phoneNumber"].setValue(contact.phoneNumber);
    this.formToUpdate.controls["age"].setValue(contact.age);

    this.showDialogToUpdate = true;
  }

  updateContact(){
    this.updateLoading = true;
    let updateContact : Contact = new Contact(
      this.formToUpdate.controls["id"].value,
      this.formToUpdate.controls["name"].value,
      this.formToUpdate.controls["surname"].value,
      this.formToUpdate.controls["address"].value,
      this.formToUpdate.controls["phoneNumber"].value,
      this.formToUpdate.controls["age"].value,
      parseInt(this.user.addressBook.id)
    );

    console.log(updateContact);
    
    this.contactService.update(updateContact).subscribe({
      next: (data) => {
        let itemIndex = this.user.addressBook.contacts.findIndex(item => item.id == updateContact.id);
        this.user.addressBook.contacts[itemIndex] = updateContact;

        this.updateLoading = false;
        this.showDialogToUpdate = false;
        this.showSuccessToast("Contact updated successfull");
      },
      error: (error) => {
        if(error instanceof ValidationException){
          error.validationErrors.forEach(validationError => this.showErrorToast(validationError))
        }
        this.updateLoading = false;
      }
    });
  }

  createContact(){
    console.log("CREATE");
    this.createLoading = true;
    let createContact : Contact = new Contact(
      null,
      this.formToCreate.controls["name"].value,
      this.formToCreate.controls["surname"].value,
      this.formToCreate.controls["address"].value,
      this.formToCreate.controls["phoneNumber"].value,
      this.formToCreate.controls["age"].value,
      parseInt(this.user.addressBook.id)
    );
    
    this.contactService.create(createContact).subscribe({
      next: (data) => {
        console.log(data);
        this.user.addressBook.contacts.push(data);

        this.createLoading = false;
        this.showDialogToCreate = false;
        this.resetCreateForm();
        this.showSuccessToast("Contact created successfull");
      },
      error: (error) => {
        if(error instanceof ValidationException){
          error.validationErrors.forEach(validationError => this.showErrorToast(validationError))
        }
        this.createLoading = false;
      }
    }) 
  }

  private showSuccessToast(message : string) {
    this.toastMessageService.add({ severity: 'success', summary: 'Success', detail: message });
  }

  private showErrorToast(message : string) {
    this.toastMessageService.add({ severity: 'error', summary: 'Error', detail: message });
  }

  private resetCreateForm(){
    this.formToCreate.controls["name"].setValue("");
    this.formToCreate.controls["surname"].setValue("");
    this.formToCreate.controls["address"].setValue("");
    this.formToCreate.controls["phoneNumber"].setValue("");
    this.formToCreate.controls["age"].setValue("");
  }

  openDeleteDialog(contact : Contact){
    this.confirmationService.confirm({
      message: 'Do you want to delete this record?',
      header: 'Delete Confirmation',
      icon: 'pi pi-info-circle',
      acceptButtonStyleClass:"p-button-danger p-button-text",
      rejectButtonStyleClass:"p-button-text p-button-text",
      acceptIcon:"none",
      rejectIcon:"none",

      accept: () => {
          this.contactService.delete(contact.id!).subscribe(data => {
            if(data){
              let itemIndex = this.user.addressBook.contacts.findIndex(item => item.id == contact.id);
              this.user.addressBook.contacts.splice(itemIndex,1)
              this.showSuccessToast('Record deleted');
            }
          })
      },
      reject: () => {
          this.showErrorToast('You have rejected');
      }
    });
  }

  constructor(private authService : AuthService, private contactService : ContactService, private toastMessageService: MessageService,private confirmationService: ConfirmationService){}

}
