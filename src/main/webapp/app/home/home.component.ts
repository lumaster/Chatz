import { Component, ElementRef, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { JhiEventManager } from 'ng-jhipster';
import { LoginService } from 'app/core/login/login.service';
import { StateStorageService } from 'app/core/auth/state-storage.service';
import { JhiMainComponent } from 'app/layouts/main/main.component.ts';
import { HttpClient } from '@angular/common/http';
import { SERVER_API_URL } from 'app/app.constants';
import { FileUploader } from 'ng2-file-upload';
import { JhiMessageService } from 'app/core/message/message.service';

const URL_ALL_CUSTOMER = SERVER_API_URL + 'chat/customer';
const URL_MESSAGE_HISTORY = SERVER_API_URL + 'chat/messageHistory';

@Component({
    selector: 'jhi-home',
    templateUrl: './home.component.html',
    styleUrls: ['home.css']
})
export class HomeComponent implements OnInit, AfterViewInit {
    uploader: FileUploader = new FileUploader({ url: '' });
    displayLoading = false;

    authenticationError: boolean;
    password: string;
    rememberMe: boolean;
    username: string;
    credentials: any;
    customers$: any;
    messageHistory$: any;

    activeCustomer: any;
    messageText: string;

    @ViewChild('uploadImageFile') uploadImageFile: ElementRef;
    @ViewChild('appMessageChat') appMessageChat: ElementRef;

    // @ViewChild('ngxAutoScroll') ngxAutoScroll: NgxAutoScroll;

    constructor(
        private eventManager: JhiEventManager,
        private loginService: LoginService,
        private stateStorageService: StateStorageService,
        private elementRef: ElementRef,
        private router: Router,
        private mainComponent: JhiMainComponent,
        private http: HttpClient,
        private messageService: JhiMessageService
    ) {
        this.credentials = {};
        this.mainComponent.setShowHeaderAndFooter(true);
        this.messageText = '';
    }

    ngForMessageCallback() {
        this.appMessageChat.nativeElement.scrollTop = this.appMessageChat.nativeElement.scrollHeight;
    }

    ngAfterViewInit(): void {
        // this.appMessageChat.nativeElement.scrollTop = this.appMessageChat.nativeElement.scrollHeight;
    }

    ngOnInit() {
        // this.activeCustomer = {
        //     social: 'MOCK'
        // };
        this.messageHistory$ = {};
        this.messageHistory$.messageList = {};
        this.messageService.subscribe();
        this.messageService.receive().subscribe(chatMessage => {
            if (
                chatMessage.customer != null &&
                this.activeCustomer != null &&
                chatMessage.customer.socialId === this.activeCustomer.socialId
            ) {
                this.messageHistory$['messageList'].push(chatMessage);
            } else {
                this.fetchCustomerHistory();
            }
        });

        this.displayLoading = true;
        this.fetchCustomerHistory();
    }

    cancel() {
        this.credentials = {
            username: null,
            password: null,
            rememberMe: true
        };
        this.authenticationError = false;
    }

    login() {
        this.loginService
            .login({
                username: this.username,
                password: this.password,
                rememberMe: this.rememberMe
            })
            .then(() => {
                this.authenticationError = false;
                if (this.router.url === '/register' || /^\/activate\//.test(this.router.url) || /^\/reset\//.test(this.router.url)) {
                    this.router.navigate(['']);
                }

                this.eventManager.broadcast({
                    name: 'authenticationSuccess',
                    content: 'Sending Authentication Success'
                });
                this.router.navigate(['/admin/logs']);
            })
            .catch(() => {
                this.authenticationError = true;
            });
    }

    showMessageDetails(customer) {
        this.displayLoading = true;
        this.http.get(URL_MESSAGE_HISTORY + '/' + customer.customerId, {}).subscribe(
            result => {
                // console.log('GET URL[' + URL_MESSAGE_HISTORY + '/' + customer.customerId + '] Received Body = ' + JSON.stringify(result));
                this.messageHistory$ = result;
                this.displayLoading = false;
                this.activeCustomer = customer;
            },
            error => console.log('There was an error while calling GET URL[' + URL + ']: ', error)
        );
    }

    sendMessage() {
        // alert('Message = ' + this.messageText + ', upload image = ' + this.uploader.queue[ 0 ].file.name);
        const chatMessage = {
            customer: this.activeCustomer,
            message: this.messageText,
            type: 'TEXT',
            isGroupMessage: false,
            groupId: 0,
            status: 'OUT'
        };
        this.messageService.sendMessage(chatMessage);
        this.messageHistory$['messageList'].push(chatMessage);
        // Clear
        this.messageText = '';
        this.uploader.clearQueue();
        // this.appMessageChat.nativeElement.scrollTop = this.appMessageChat.nativeElement.scrollHeight;
        // this.forceScrollDown();
    }

    onUploadImageClick() {
        this.uploader.clearQueue();
        this.uploadImageFile.nativeElement.click();
    }

    forceScrollDown() {
        // this.ngxAutoScroll.forceScrollDown();
    }

    private fetchCustomerHistory() {
        this.http.get(URL_ALL_CUSTOMER, {}).subscribe(
            result => {
                // console.log('DATA >>>> ' + JSON.stringify(result));
                this.customers$ = result;
                this.displayLoading = false;
            },
            error => console.log('There was an error while calling GET URL[' + URL + ']: ', error)
        );
    }
}
